package org.framework.web.typeconvert.impl;

import org.apache.commons.beanutils.BeanUtils;
import org.framework.web.ActionContext;
import org.framework.web.TypeExecutor;
import org.framework.web.typeconvert.TypeConvert;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;

public class BeanTypeConvert implements TypeConvert {

    @Override
    public Object convert(Parameter parameter, TypeExecutor executor)
            throws InvocationTargetException,InstantiationException,IllegalAccessException{
        try {
            Object obj=parameter.getType().newInstance();
            BeanUtils.populate(obj, ActionContext.getActionContext().getRequest().getParameterMap());
            if(obj==null || obj.equals("")){
                executor.execute(parameter);
            }
            return obj;
        } catch (Throwable e) {
            return executor.execute(parameter);
        }


    }
}
