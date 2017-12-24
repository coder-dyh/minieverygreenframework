package org.framework.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 此类专门用来存放放在上下文对象上的一些共享信息
 */
public class ContextInfo {

    protected final static String CONTEXT_MAPPING="contextMap";
    protected final static String FILTER_LIST="filterList";

    /**
     * key为请求的URL地址，value为处理请求的类的描述定义
     */
    private static Map<String,HandlerDefinition> requestMap=new HashMap<>();
    /**
     * 用于存放所有的实现filter接口的所有实现类的描述定义
     */
    private static List<FilterDefinition> filterList=new ArrayList<>();

    public static String getContextMapping() {
        return CONTEXT_MAPPING;
    }

    public static String getFilterListStr() {
        return FILTER_LIST;
    }

    public static List<FilterDefinition> getFilterList(){
        return filterList;
    }

    public static void setFilterList(List<FilterDefinition> filterList) {
        ContextInfo.filterList = filterList;
    }

    public static Map<String, HandlerDefinition> getRequestMap() {
        return requestMap;
    }

    public static void setRequestMap(Map<String, HandlerDefinition> requestMap) {
        ContextInfo.requestMap = requestMap;
    }
}
