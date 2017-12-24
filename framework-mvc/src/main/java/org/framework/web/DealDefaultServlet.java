package org.framework.web;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DealDefaultServlet {

    private static final String DEFAULT_SERVLET_NAME="defaultServletName";

    /**
     * 当请求匹配不到相应的映射描述, 则给容器默认的servlet处理
     *
     * @throws IOException
     * @throws ServletException
     */
    protected static void forwardDefaultServlet() throws ServletException, IOException {
        RequestDispatcher rd = ActionContext.getActionContext()
                .getRequest().getServletContext()
                .getNamedDispatcher(DEFAULT_SERVLET_NAME);
        if (rd == null) {
            throw new IllegalStateException(
                    "A RequestDispatcher could not be located for the default servlet '"
                            + DEFAULT_SERVLET_NAME + "'");
        }
        rd.forward(ActionContext.getActionContext().getRequest(), ActionContext.getActionContext().getResponse());
    }
}
