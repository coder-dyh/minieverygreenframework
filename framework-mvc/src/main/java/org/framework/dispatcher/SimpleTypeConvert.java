package org.framework.dispatcher;

import org.apache.commons.beanutils.ConvertUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;

//BeanUtil 转换实体类型  ConvertUtils转换常用数据类型（基本数据类型）

public class SimpleTypeConvert implements TypeConvert{

    @Override
    public Object convert(Parameter parameter, TypeExecutor executor)
            throws IllegalAccessException,InvocationTargetException,InstantiationException{


        Object obj=parameter.getType().isArray() ? ActionContext.getActionContext()
                .getRequest().getParameterValues(parameter.getName()) : ActionContext.getActionContext()
                .getRequest().getParameter(parameter.getName());
        if(obj==null){
            executor.execute(parameter);
        }
        Object value=ConvertUtils.convert(obj,parameter.getType());

        if(parameter.getType().isPrimitive() && obj==null){
            throw new RuntimeException(value+"can't be convert "+parameter.getType().getName());
        }

        return obj;
    }
}
