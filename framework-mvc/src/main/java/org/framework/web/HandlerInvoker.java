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


    public static void invoker() throws IOException,ServletException{

        HttpServletRequest req=ActionContext.getActionContext().getRequest();
        MVCFactory factory=(MVCFactory) req.getServletContext().getAttribute("PluginFactory");
        HandlerDefinition definition = getDefinition(req);
        if(definition!=null){
            Object obj=factory.createInstance(definition);
            try {
                Object[] params= TypeConvertUtils.parameterConvert(definition.getMethod());
                ViewResult viewResult = (ViewResult) definition.getMethod().invoke(obj,params);
                //对视图结果进行处理
                executeViewResult(viewResult);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }else{
            DealDefaultServlet.forwardDefaultServlet(req,ActionContext.getActionContext().getResponse());
        }

    }

    /**
     * 获得描述定义
     * @param req
     * @return HandlerDefinition
     */
    private static HandlerDefinition getDefinition(HttpServletRequest req) {
        Map<String,HandlerDefinition> requestMap=
                (Map<String, HandlerDefinition>) ActionContext
                        .getActionContext().getRequest()
                        .getServletContext()
                        .getAttribute(ContextInfo.CONTEXT_MAPPING);
        return requestMap.get(req.getServletPath());
    }

    private static void executeViewResult(ViewResult viewResult){
        if(viewResult!=null){
            viewResult.dealViewResult();
        }
    }
}
