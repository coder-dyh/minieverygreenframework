package org.framework.web;


import org.framework.web.view.ViewResult;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
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
                ViewResult viewResult = (ViewResult) definition.getMethod().invoke(definition.getClazz().newInstance(),params);
                //对视图结果进行处理
                executeViewResult(viewResult);
            } catch (Exception e) {
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
        for(int i=0;i<params.length;i++){
            Parameter parameter=params[i];
            needParams[i]=new TypeExecutor().execute(parameter);
        }

        return needParams;
    }

    private static void executeViewResult(ViewResult viewResult){
        if(viewResult!=null){
            viewResult.dealViewResult();
        }
    }



}
