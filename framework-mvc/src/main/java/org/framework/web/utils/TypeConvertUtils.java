package org.framework.web.utils;

import org.framework.web.TypeExecutor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class TypeConvertUtils {

    public static Object[] parameterConvert(Method method)
            throws InvocationTargetException,IllegalAccessException,InstantiationException{
        Parameter[] params=method.getParameters();
        Object[] needParams=new Object[params.length];
        for(int i=0;i<params.length;i++){
            Parameter parameter=params[i];
            needParams[i]=new TypeExecutor().execute(parameter);
        }

        return needParams;
    }
}
