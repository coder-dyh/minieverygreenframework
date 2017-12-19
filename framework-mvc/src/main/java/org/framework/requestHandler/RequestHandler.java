package org.framework.requestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface RequestHandler {

    /**
     * 处理请求的方法
     * @param request
     * @param response
     */
    void requestHandler(HttpServletRequest request, HttpServletResponse response);
}
