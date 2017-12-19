package org.framework.filter.impl;

import org.framework.dispatcher.ActionContext;
import org.framework.filter.Filter;
import org.framework.filter.FilterChain;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FilterAbstract implements Filter{

    static {
        HttpServletRequest req=ActionContext.getRequest();
        HttpServletResponse resp=ActionContext.getResponse();
    }

    protected void before(){

    }

    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) {

    }

    protected void executeFilter(HttpServletRequest req,HttpServletResponse resp,FilterChain chain){
        before();
        doFilter(req,resp,chain);
        after();
    }

    protected void after(){

    }


}
