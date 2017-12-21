package org.framework.web.filter;

import org.framework.web.FilterDefinition;

import java.util.ArrayList;
import java.util.List;

public class FilterAdaptor {

    private List<FilterDefinition> list;
    private List<Filter> filterInstances=new ArrayList<>();

    public FilterAdaptor(){

    }

    public FilterAdaptor(List<FilterDefinition> list){
        this.list=list;
    }

    /**
     * 返回过滤器实例
     * @return
     */
    public List<Filter> getFilterInstances(){
        createInstance();
        return filterInstances;
    }



    /**
     * 创建过滤器实例
     */
    private void createInstance(){
        for(FilterDefinition fd : list){
            try {
                filterInstances.add((Filter) fd.getClazz().newInstance());
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }


}
