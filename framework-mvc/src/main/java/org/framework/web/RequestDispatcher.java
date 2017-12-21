package org.framework.web;

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
     * key为请求的URL地址，value为处理请求的类的描述定义
     */
    private static Map<String,HandlerDefinition> requestMap=new HashMap<>();
    /**
     * 用于存放所有的实现filter接口的所有实现类的class对象
     */
    private static List<FilterDefinition> filterList=new ArrayList<>();
    /**
     * 用于存放过滤链的过滤顺序的order值
     */
    private static List orderList=new ArrayList();

    protected final static String CONTEXTMAPING="contextMap";
    protected final static String FILTERLIST="filterList";

    @Override
    public void init(ServletConfig config) throws ServletException {
        config.getServletContext().setAttribute(CONTEXTMAPING,requestMap);
        config.getServletContext().setAttribute(FILTERLIST,filterList);
        HandlerMapping handlerMapping=new HandlerMapping();
        handlerMapping.scan(config);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setActionContext(req,resp);

        //检查该请求是否有相关过滤器拦截
        List<FilterDefinition> filters=(List<FilterDefinition>) req.getServletContext().getAttribute(FILTERLIST);
        List<FilterDefinition> filterDefinitionList=checkFilterMapping(filters);

        if(filterDefinitionList!=null && filterDefinitionList.size()>0){
            //如果有过滤器先执行过滤器中的方法
            FilterChain chain=new FilterChain(new FilterAdaptor(filterDefinitionList).getFilterInstances());
            chain.execute(req,resp,chain);
        }else{
            if(!req.getServletPath().equals("/favicon.ico")){
                HandlerInvoker.invoker(req,resp);
            }
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
    }


}
