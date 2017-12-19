package org.framework.dispatcher;

import org.framework.anno.FilterMapping;
import org.framework.anno.RequestMapping;
import org.framework.utils.FirstCharToLowerCase;
import org.framework.utils.Scan;
import sun.misc.Request;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.ArrayList;
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
                (Map<String,HandlerDefinition>)config.getServletContext().getAttribute(RequestDispatcher.CONTEXTMAPING);
        List<FilterDefinition> filterList=
                (List<FilterDefinition>) config.getServletContext().getAttribute(RequestDispatcher.FILTERLIST);
        for(String s : pathList){
            HandlerDefinition definition=new HandlerDefinition();
            Class<?> clazz=Class.forName(s);
            //创建过滤器的描述定义
            createFilterDefinition(clazz,filterList);
            Method[] methods=clazz.getDeclaredMethods();
            String classAnnotation=getClassAnnotation(clazz);
            for(Method m : methods){
                definition.setClazz(clazz);
                definition.setMethod(m);
                if(m.isAnnotationPresent(RequestMapping.class)){
                    String requestMapName=m.getAnnotation(RequestMapping.class).value();
                    map.put(classAnnotation+requestMapName,definition);
                }else{
                    map.put(classAnnotation+"/"+FirstCharToLowerCase.toLowerCaseFirstOne(clazz.getSimpleName()),definition);
                }
            }
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
            if(clazz.getAnnotation(FilterMapping.class).value()!=null){
                value="/"+clazz.getAnnotation(FilterMapping.class).value();
                order=clazz.getAnnotation(FilterMapping.class).order();
            }else{
                value="/"+ FirstCharToLowerCase.toLowerCaseFirstOne(clazz.getSimpleName());
                order=clazz.getAnnotation(FilterMapping.class).order();
            }
            fd.setRequestMapName(value);
            fd.setOrder(order);
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
