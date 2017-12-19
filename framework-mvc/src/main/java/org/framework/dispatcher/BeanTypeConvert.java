package org.framework.dispatcher;

import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;

public class BeanTypeConvert implements TypeConvert{


    @Override
    public Object convert(Parameter parameter, TypeExecutor executor)
            throws InvocationTargetException,InstantiationException,IllegalAccessException{
        Object obj=parameter.getType().newInstance();
        try {
            BeanUtils.populate(obj,ActionContext.getRequest().getParameterMap());
            return obj;
        } catch (Throwable e) {
            return executor.execute(parameter);
        }


    }
}
