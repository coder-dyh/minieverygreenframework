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
        Map<String,Object[]> paramsMap=new HashMap<>();
        paramsMap.put(method.getName(),params);
        for(int i=0;i<params.length;i++){
            //setProperty(req, resp, params[i], needParams, paramsMap, i);
            Parameter parameter=params[i];
            needParams[i]=new TypeExecutor().execute(parameter);
        }
        return needParams;
    }

    private static void setProperty(HttpServletRequest req, HttpServletResponse resp, Parameter param, Object[] needParams, Map<String, Object[]> paramsMap, int i) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        if(param.getType().equals(HttpServletRequest.class)){
            needParams[i]=req;
        }else if(param.getType().equals(HttpServletResponse.class)){
            needParams[i]=resp;
        }else{
            Object obj=req.getParameter(param.getName());
            if(param.getType().isPrimitive() || param.getType().isArray()){
                //BeanUtil 转换实体类型  ConvertUtils转换常用数据类型（基本数据类型）
                needParams[i]= ConvertUtils.convert(obj, param.getType());
            }else{
                BeanUtils.populate(param.getType().newInstance(),paramsMap);
            }

        }
    }


}
