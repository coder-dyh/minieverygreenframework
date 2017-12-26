package org.framework.web;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DealDefaultServlet {

    /**
     * Tomcat, Jetty, JBoss, GlassFish 默认Servlet名称
     * 如果是静态资源或者是控制器无法处理的请求，则交给容器的默认Servlet处理
     */
    private static final String COMMON_DEFAULT_SERVLET_NAME = "default";

    /**
     * 从容器中获取的默认Servlet名
     */
    private static String defaultServletName;

    /**
     * 初始化web容器的默认Servlet名称
     * @param config
     */
    public static void initDefaultServlet(ServletConfig config) {
        ServletContext servletContext = config.getServletContext();
        defaultServletName = servletContext
                .getInitParameter("defaultServletName");
        if (defaultServletName == null || "".equals(defaultServletName)) {
            if (servletContext.getNamedDispatcher(COMMON_DEFAULT_SERVLET_NAME) != null) {
                defaultServletName = COMMON_DEFAULT_SERVLET_NAME;
            } else {
                throw new IllegalStateException("Unable to locate the default servlet for serving static content. " +
                        "Please set the 'defaultServletName' property explicitly.");
            }
        }
    }

    /**
     * 当请求匹配不到相应的映射描述, 则给容器默认的servlet处理
     *
     * @throws IOException
     * @throws ServletException
     */
    public static void forwardDefaultServlet(HttpServletRequest request,
                                         HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd = request.getServletContext().getNamedDispatcher(
                defaultServletName);
        if (rd == null) {
            throw new IllegalStateException(
                    "A RequestDispatcher could not be located for the default servlet '"
                            + defaultServletName + "'");
        }
        rd.forward(request, response);
    }
}
