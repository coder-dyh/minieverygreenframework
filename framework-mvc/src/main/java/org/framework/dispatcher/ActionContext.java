package org.framework.dispatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ActionContext {

    private static HttpServletRequest request;
    private static HttpServletResponse response;
    private static String servletPath;


    public static final ThreadLocal<ActionContext> actionContextThreadLocal=new ThreadLocal<ActionContext>();

    public static ActionContext getActionContext(){
        if(actionContextThreadLocal.get()==null){
            actionContextThreadLocal.set(new ActionContext());
        }
        return actionContextThreadLocal.get();
    }

    public static HttpServletRequest getRequest() {
        return request;
    }

    public static void setRequest(HttpServletRequest request) {
        ActionContext.request = request;
    }

    public static HttpServletResponse getResponse() {
        return response;
    }

    public static void setResponse(HttpServletResponse response) {
        ActionContext.response = response;
    }

    public static String getServletPath() {
        return servletPath;
    }

    public static void setServletPath(String servletPath) {
        ActionContext.servletPath = servletPath;
    }
}
