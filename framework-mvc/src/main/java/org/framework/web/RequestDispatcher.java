package org.framework.web;

import org.framework.web.factory.MVCFactory;
import org.framework.web.factory.impl.WebAppFactory;
import org.framework.web.filter.FilterAdaptor;
import org.framework.web.filter.FilterChain;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RequestDispatcher extends HttpServlet {


    /**
     * 用于存放过滤链的过滤顺序的order值
     */
    private static List orderList=new ArrayList();

    @Override
    public void init(ServletConfig config) throws ServletException {
        //将一些数据放到上下文对象共享
        setServletContext(config);
        //扫描项目
        new HandlerMapping().scan(config);
        DealDefaultServlet.initDefaultServlet(config);
        setFactory(config);
    }

    /**
     * 将一些需要共享的数据设置到上下文作用域中
     * @param config
     */
    private void setServletContext(ServletConfig config){
        config.getServletContext().setAttribute(ContextInfo.getContextMapping(),ContextInfo.getRequestMap());
        config.getServletContext().setAttribute(ContextInfo.getFilterListStr(),ContextInfo.getFilterList());
    }

    private void setFactory(ServletConfig config){
        if(config.getServletContext().getAttribute("PluginFactory")==null){
            config.getServletContext().setAttribute("PluginFactory",new WebAppFactory());
        }
    }


    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setActionContext(req,resp);
        //检查该请求是否有相关过滤器拦截
        List<FilterDefinition> filters=(List<FilterDefinition>) req.getServletContext().getAttribute(ContextInfo.FILTER_LIST);
        List<FilterDefinition> filterDefinitionList=checkFilterMapping(filters);

        if(filterDefinitionList!=null && filterDefinitionList.size()>0){
            //如果有过滤器先执行过滤器中的方法
            FilterChain chain=new FilterChain(new FilterAdaptor(filterDefinitionList).getFilterInstances());
            chain.execute(req,resp,chain);
        }else{
            HandlerInvoker.invoker();
        }
    }





    /**
     * 创建存储请求响应等信息的行为上下文
     * @param req
     * @param resp
     */
    private void setActionContext(HttpServletRequest req, HttpServletResponse resp){
        ActionContext.getActionContext().setRequest(req);
        ActionContext.getActionContext().setResponse(resp);
        ActionContext.getActionContext().setServletPath(req.getServletPath());
    }

    /**
     * 检查对应请求是否有对应的过滤器拦截
     * @return
     */
    private List<FilterDefinition> checkFilterMapping(List<FilterDefinition> filters){
        List<FilterDefinition> list=new ArrayList<>();
        if(filters.size()>0){
            String servletPath=ActionContext.getActionContext().getServletPath();
            for(FilterDefinition fd : filters){
                if(fd.getRequestMapName().equals(servletPath) || fd.getRequestMapName().equals("/")){
                    list.add(fd);
                }
            }
            //对集合中的过滤器按注解中的order值进行排序
            list=sortOrders(list);
        }
        return list;
    }

    /**
     * 对集合中的过滤器按注解中的order值进行排序
     * @param list
     * @return List<FilterDefinition> 返回一个已经排好序的集合
     */
    private List<FilterDefinition> sortOrders(List<FilterDefinition> list){
        for(int i=0;i<list.size();i++){
            int j=list.get(i).getOrder();
            list.set(j-1,list.get(i));
        }
        return list;
    }


    @Override
    public void destroy() {
        super.destroy();
        removeActionContext();
    }

    /**
     * 销毁ActionContext
     */
    public void removeActionContext(){
        ActionContext.remove();
    }


}
