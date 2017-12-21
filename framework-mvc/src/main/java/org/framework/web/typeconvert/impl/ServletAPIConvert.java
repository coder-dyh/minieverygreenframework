package org.framework.web.typeconvert.impl;

import org.framework.web.ActionContext;
import org.framework.web.TypeExecutor;
import org.framework.web.typeconvert.TypeConvert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;

public class ServletAPIConvert implements TypeConvert {
    @Override
    public Object convert(Parameter parameter, TypeExecutor executor) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        if(parameter.getType().equals(HttpServletRequest.class)){
            return ActionContext.getActionContext().getRequest();
        }else if(parameter.getType().equals(HttpServletResponse.class)){
            return ActionContext.getActionContext().getResponse();
        }else if(parameter.getType().equals(HttpSession.class)){
            return ActionContext.getActionContext().getRequest().getSession();
        }
        return executor.execute(parameter);
    }
}
