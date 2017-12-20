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

    /**
     * 将集合中的元素迭代到迭代器中
     */
    public void executeIterator(){
        it=objectList.iterator();
    }

    public void execute(HttpServletRequest req, HttpServletResponse resp,FilterChain chain){
        while (it.hasNext()){
            it.next().doFilter(req,resp,this);
        }
    }


}
