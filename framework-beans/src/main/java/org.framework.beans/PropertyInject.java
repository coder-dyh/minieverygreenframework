package org.framework.beans;


import java.beans.IntrospectionException;
import java.lang.reflect.Field;

public class PropertyInject implements org.framework.beans.Inf.Inject{


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
                obj=beanFactory.getBean(injectName);
                field.set(target,obj);
                analyseField(obj,beanFactory);
            }
        }

    }
}
