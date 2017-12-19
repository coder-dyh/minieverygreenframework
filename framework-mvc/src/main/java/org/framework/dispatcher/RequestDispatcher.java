package org.framework.dispatcher;

import org.framework.filter.FilterAdaptor;
import org.framework.filter.FilterChain;
import org.framework.requestHandler.RequestHandler;
import org.framework.utils.FirstCharToLowerCase;
import org.framework.utils.Scan;
import sun.rmi.server.Dispatcher;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Handler;


public class RequestDispatcher extends HttpServlet {

    /**
     * key为请求的URL地址，value为处理请求的类的描述定义
     */
    private static Map<String,HandlerDefinition> requestMap=new HashMap<>();
    /**
     * 用于存放所有的实现filter接口的所有实现类的class对象
     */
    private static List<FilterDefinition> filterList=new ArrayList<>();

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
        List<FilterDefinition> filterDefinitionList=checkFilterMapping();
        if(filterDefinitionList!=null){
            FilterChain chain=new FilterChain(new FilterAdaptor(filterDefinitionList).getFilterInstances());
            chain.execute(req,resp,chain);
            HandlerInvoker.invoker(req,resp);
        }else{
            HandlerInvoker.invoker(req,resp);
        }
    }

    /**
     * 创建存储请求响应等信息的行为上下文
     * @param req
     * @param resp
     */
    private void setActionContext(HttpServletRequest req, HttpServletResponse resp){
        ActionContext.setServletPath(req.getServletPath());
        ActionContext.setRequest(req);
        ActionContext.setResponse(resp);
    }

    /**
     * 检查对应请求是否有对应的过滤器拦截
     * @return
     */
    private List<FilterDefinition> checkFilterMapping(){
        if(filterList.size()>0){
            List<FilterDefinition> list=new ArrayList<>();
            String servletPath=ActionContext.getServletPath();
            for(FilterDefinition fd : filterList){
                if(fd.getRequestMapName().equals(servletPath)){
                    list.add(fd);
                }
            }
            //对过滤器中的元素进行排序
            List<FilterDefinition> filterDefinitionList=sortFilter(list);
            return list;
        }
        return null;
    }

    private List<FilterDefinition> sortFilter(List<FilterDefinition> list){

        if(list.size()==1){
            return list;
        }else{
            int[] order=new int[list.size()];
            for(int i=0;i<list.size();i++){
                order[i]=list.get(i).getOrder();
            }
            int[] afterBubbling=bubbling(order);
            List<FilterDefinition> filterDefinitionList=new ArrayList<>();
            for(int i=0;i<afterBubbling.length;i++){
                filterDefinitionList.add(list.get(afterBubbling[i]));
            }
            return filterDefinitionList;
        }

    }

    private int[] bubbling(int[] a){
        //冒泡排序
        for (int k = 0; k < a.length - 1; k++) {
            for (int j = k + 1; j < a.length; j++) { // 升序把<改成>
                if (a[k] > a[j]) {
                    int temp = a[k];
                    a[k] = a[j];
                    a[j] = temp;
                }
            }
        }
        return a;
    }





    @Override
    public void destroy() {
        super.destroy();
    }


}
