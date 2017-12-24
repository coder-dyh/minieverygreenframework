package org.framework.web.filter;

import org.framework.web.HandlerInvoker;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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

    /**
     * 将集合中的元素迭代到迭代器中
     */
    public void executeIterator(){
        it=objectList.iterator();
    }

    public void execute(HttpServletRequest req, HttpServletResponse resp,FilterChain chain){

        if(!it.hasNext()){
            try {
                HandlerInvoker.invoker();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ServletException e) {
                e.printStackTrace();
            }
        }

        while (it.hasNext()){
            Filter filter=it.next();
            filter.init();
            filter.doFilter(req, resp, chain);
            filter.destroy();
        }
    }


}
