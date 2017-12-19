package org.framework.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FilterChain {

    private List<Filter> objectList=new ArrayList<>();
    private Iterator<Filter> it;

    public FilterChain(List<Filter> objectList){
        this.objectList=objectList;
        executeIterator();
    }

    public void executeIterator(){
        it=objectList.iterator();
    }

    public void execute(HttpServletRequest req, HttpServletResponse resp,FilterChain chain){
        if(it.hasNext()){
            it.next().doFilter(req,resp,this);
        }
    }


}
