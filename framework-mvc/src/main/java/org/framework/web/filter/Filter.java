package org.framework.web.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Filter {

    void init();

    void doFilter(HttpServletRequest req, HttpServletResponse resp,FilterChain chain);

    void destroy();

}
