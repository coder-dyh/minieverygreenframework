package org.framework.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ActionContext {

    private  HttpServletRequest request;
    private  HttpServletResponse response;
    private  String servletPath;

    public static final ThreadLocal<ActionContext> actionContextThreadLocal=new ThreadLocal<ActionContext>();

    public static ActionContext getActionContext(){
        if(actionContextThreadLocal.get()==null){
            actionContextThreadLocal.set(new ActionContext());
        }
        return actionContextThreadLocal.get();
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    public String getServletPath() {
        return servletPath;
    }

    public void setServletPath(String servletPath) {
        this.servletPath = servletPath;
    }

    /**
     * 移除线程副本中的对象
     */
    public static void remove(){
        actionContextThreadLocal.remove();
    }

}
