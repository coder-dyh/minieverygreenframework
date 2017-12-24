package org.framework.web;

import org.framework.web.anno.FilterMapping;
import org.framework.web.anno.RequestMapping;
import org.framework.web.utils.FirstCharToLowerCase;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * 这个类专门用于获得元素上的注解信息，
 * 单独设立这个类来获得注解信息是为了以
 * 后增加信息注解可以在这个类中增加方法
 */
public class AnnotationInfo {

    private Class<?> clazz;
    private Method method;
    private Field field;

    public AnnotationInfo(){

    }
    public AnnotationInfo(Class<?> clazz){
        this.clazz=clazz;
    }

    public AnnotationInfo(Method method){
        this.method=method;
    }

    public AnnotationInfo(Field field){
        this.field=field;
    }

    public AnnotationInfo(Class<?> clazz,Method method){
        this.clazz=clazz;
        this.method=method;
    }

    public AnnotationInfo(Class<?> clazz,Field field){
        this.clazz=clazz;
        this.field=field;
    }
    public AnnotationInfo(Method method,Field field){
        this.method=method;
        this.field=field;
    }

    public void getAnnotationReqMapping(Map<String,HandlerDefinition> map,HandlerDefinition definition,String classAnnotation){
        String requestMapName;
        if(method.isAnnotationPresent(RequestMapping.class)){
            requestMapName=method.getAnnotation(RequestMapping.class).value();
            map.put(classAnnotation+requestMapName,definition);
        }else{
            map.put(classAnnotation+"/"+FirstCharToLowerCase.toLowerCaseFirstOne(clazz.getSimpleName()),definition);
        }
    }

    public void getFilterDefinition(FilterDefinition fd) {
        String value;
        int order;
        if(clazz.getAnnotation(FilterMapping.class).value()!=null){
            value=clazz.getAnnotation(FilterMapping.class).value();
            order=clazz.getAnnotation(FilterMapping.class).order();
        }else{
            value= FirstCharToLowerCase.toLowerCaseFirstOne(clazz.getSimpleName());
            order=clazz.getAnnotation(FilterMapping.class).order();
        }
        fd.setRequestMapName(value);
        fd.setOrder(order);
    }








}


