package org.framework.web;


import org.framework.web.utils.TypeConvertUtils;
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

    public static void invoker() throws IOException,ServletException{
        String servletPath=ActionContext.getActionContext().getServletPath();
        Map<String,HandlerDefinition> requestMap=
                (Map<String, HandlerDefinition>) ActionContext
                        .getActionContext().getRequest()
                        .getServletContext()
                        .getAttribute(ContextInfo.CONTEXT_MAPPING);
        HandlerDefinition definition=requestMap.get(servletPath);
        if(definition!=null){
            try {
                Object[] params= TypeConvertUtils.parameterConvert(definition.getMethod());
                ViewResult viewResult = (ViewResult) definition.getMethod().invoke(definition.getClazz().newInstance(),params);
                //对视图结果进行处理
                executeViewResult(viewResult);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }else{
            DealDefaultServlet.forwardDefaultServlet();
        }
    }

    private static void executeViewResult(ViewResult viewResult){
        if(viewResult!=null){
            viewResult.dealViewResult();
        }
    }
}
