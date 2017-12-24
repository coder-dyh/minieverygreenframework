package org.framework.web;

import org.framework.web.anno.FilterMapping;
import org.framework.web.anno.RequestMapping;
import org.framework.web.utils.FirstCharToLowerCase;
import org.framework.web.utils.Scan;

import javax.servlet.ServletConfig;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

public class HandlerMapping {

    /**
     * 扫描当前项目下的所有文件
     * @param config
     */
    public void scan(ServletConfig config){
        List<String> pathList=Scan.scan();
        try {
            createHandlerDefinition(config,pathList);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 构建类的描述定义
     * @param config
     * @param pathList
     * @throws ClassNotFoundException
     */
    public void createHandlerDefinition(ServletConfig config, List<String> pathList) throws ClassNotFoundException{
        Map<String,HandlerDefinition> map=
                (Map<String,HandlerDefinition>)config.getServletContext().getAttribute(ContextInfo.CONTEXT_MAPPING);
        List<FilterDefinition> filterList=
                (List<FilterDefinition>) config.getServletContext().getAttribute(ContextInfo.FILTER_LIST);

        for(String s : pathList){
            HandlerDefinition definition=new HandlerDefinition();
            Class<?> clazz=Class.forName(s);
            //创建过滤器的描述定义
            createFilterDefinition(clazz,filterList);
            //构建方法的描述定义
            findRequestMapping(map, definition, clazz);
        }

    }

    /**
     * 找到所有的请求处理的方法并构建这个方法的描述定义
     * @param map
     * @param definition
     * @param clazz
     */
    private void findRequestMapping(Map<String, HandlerDefinition> map, HandlerDefinition definition, Class<?> clazz) {
        Method[] methods=clazz.getDeclaredMethods();
        String classAnnotation=getClassAnnotation(clazz);
        for(Method m : methods){
            definition.setClazz(clazz);
            definition.setMethod(m);
            new AnnotationInfo(clazz,m).getAnnotationReqMapping(map,definition,classAnnotation);
        }
    }




    /**
     * 构建过滤器的描述定义
     * @param clazz
     * @param filterList
     */
    private void createFilterDefinition(Class<?> clazz,List<FilterDefinition> filterList) {
        if(clazz.isAnnotationPresent(FilterMapping.class)){

            String value=null;
            int order=1;
            FilterDefinition fd=new FilterDefinition();
            new AnnotationInfo(clazz).getFilterDefinition(fd);
            fd.setClazz(clazz);
            filterList.add(fd);
        }
    }



    /**
     * 判断class对象是否有相对应的注解
     * @param clazz
     * @return String
     */
    private String getClassAnnotation(Class<?> clazz){
        String classAnnotation="";
        if(clazz.isAnnotationPresent(RequestMapping.class)){
            classAnnotation=clazz.getAnnotation(RequestMapping.class).value();
        }
        return classAnnotation;
    }


}
