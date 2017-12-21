package org.framework.web.view;

import org.framework.web.ActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class ViewResult {


    public abstract void dealViewResult();

    public HttpServletRequest getRequest(){
        return ActionContext.getActionContext().getRequest();
    }

    public HttpServletResponse getResponse(){
        return ActionContext.getActionContext().getResponse();
    }

}