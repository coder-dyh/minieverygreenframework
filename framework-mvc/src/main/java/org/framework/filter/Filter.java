package org.framework.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Filter {

    void doFilter(HttpServletRequest req, HttpServletResponse resp,FilterChain chain);
}
