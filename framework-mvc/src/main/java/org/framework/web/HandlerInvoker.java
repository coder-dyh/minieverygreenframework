package org.framework.web;

import org.framework.web.factory.MVCFactory;
import org.framework.web.factory.impl.WebAppFactory;
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

    private static Object obj;
    private static HandlerDefinition definition;

    public static void requestInvoker(HttpServletRequest req) throws IOException,ServletException{
        MVCFactory factory=(MVCFactory) req.getServletContext().getAttribute("PluginFactory");
        Map<String,HandlerDefinition> requestMap=
                (Map<String, HandlerDefinition>) ActionContext
                        .getActionContext().getRequest()
                        .getServletContext()
                        .getAttribute(ContextInfo.CONTEXT_MAPPING);
        definition=requestMap.get(req.getServletPath());
        if(definition!=null){
            obj=factory.createInstance(definition);
            invoker();
        }else{
            DealDefaultServlet.forwardDefaultServlet();
        }
    }

    public static void invoker() throws IOException,ServletException{
        try {
            Object[] params= TypeConvertUtils.parameterConvert(definition.getMethod());
            ViewResult viewResult = (ViewResult) definition.getMethod().invoke(obj,params);
            //对视图结果进行处理
            executeViewResult(viewResult);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void executeViewResult(ViewResult viewResult){
        if(viewResult!=null){
            viewResult.dealViewResult();
        }
    }
}
