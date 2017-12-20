package org.framework.dispatcher;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HandlerInvoker {

    public static void invoker(HttpServletRequest req, HttpServletResponse resp) throws IOException,ServletException{
        String servletPath=req.getServletPath();
        Map<String,HandlerDefinition> requestMap=
                (Map<String, HandlerDefinition>) req.getServletContext().getAttribute(RequestDispatcher.CONTEXTMAPING);
        HandlerDefinition definition=requestMap.get(servletPath);
        if(definition!=null){
            try {
                Object[] params=parameterConvert(req,resp,definition.getMethod());
                definition.getMethod().invoke(definition.getClazz().newInstance(),params);
            }catch (Exception e){
                throw new RuntimeException(e);
            }
        }

    }

    /**
     * 处理类型转换的工具类
     * @param req
     * @param resp
     * @param method
     * @return Object[]
     */
    private static Object[] parameterConvert(HttpServletRequest req,HttpServletResponse resp,Method method)
            throws InvocationTargetException,IllegalAccessException,InstantiationException{
        Parameter[] params=method.getParameters();
        Object[] needParams=new Object[params.length];
//        Map<String,Object[]> paramsMap=new HashMap<>();
//        paramsMap.put(method.getName(),params);
        for(int i=0;i<params.length;i++){
            Parameter parameter=params[i];
            needParams[i]=new TypeExecutor().execute(parameter);
        }
        return needParams;
    }



}
