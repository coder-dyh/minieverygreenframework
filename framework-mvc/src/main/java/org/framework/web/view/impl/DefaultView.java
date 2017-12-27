package org.framework.web.view.impl;

import org.framework.web.view.ViewResult;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Create by coder_dyh on 2017/12/27
 */
public class DefaultView extends ViewResult{

    final static String CONTENT_TYPE = "text/html;charset=utf-8";

    private Object returnVal;

    public DefaultView(Object returnVal){
        this.returnVal = returnVal;
    }

    @Override
    public void dealViewResult() {
        HttpServletResponse response = getResponse();
        response.setContentType(CONTENT_TYPE);
        try {
            response.getWriter().println(returnVal);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
