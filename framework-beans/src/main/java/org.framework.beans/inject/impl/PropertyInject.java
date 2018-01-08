package org.framework.beans.inject.impl;

import org.framework.beans.BeanFactory;
import org.framework.beans.annotation.Inject;

import java.lang.reflect.Field;

public class PropertyInject implements org.framework.beans.inject.Inject {

    @Override
    public void inject(Object target, BeanFactory beanFactory) throws IllegalAccessException{
        analyseField(target,beanFactory);
    }

    /**
     * 递归遍历所有属性并为需要注入的属性执行注入
     * @param target
     * @param beanFactory
     * @throws IllegalAccessException
     */
    private void analyseField(Object target,BeanFactory beanFactory) throws IllegalAccessException{
        Class<?> clazz=target.getClass();
        Field[] fields=clazz.getDeclaredFields();
        String injectName=null;
        Object obj=null;
        for(Field field: fields){
            field.setAccessible(true);
            if(field.isAnnotationPresent(Inject.class)){
                injectName=field.getAnnotation(Inject.class).name();
                //从容器中取出对应的实现类
                obj=beanFactory.getBean(injectName);
                //给属性赋值参数：（实例，参数值）
                field.set(target,obj);
                analyseField(obj,beanFactory);
            }
        }

    }
}
