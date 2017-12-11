package org.framework.beans;


import java.beans.IntrospectionException;
import java.lang.reflect.Field;

public class PropertyInject implements org.framework.beans.Inf.Inject{


    @Override
    public void inject(String className, Class<?> clazz) throws IntrospectionException, IllegalAccessException, InstantiationException {

            Field[] fields=clazz.getDeclaredFields();
            for(Field f: fields){
                if(f.isAnnotationPresent(Inject.class)){
                    f.setAccessible(true);
                    String injectName=f.getAnnotation(Inject.class).name();
                    Class<?> cls=BeanFactory.getComponentNameClass().get(injectName);
                    //为属性注入实现类
                    f.set(BeanFactory.getBean(className),BeanFactory.getBean(injectName));
                    inject(injectName,cls);
                }
            }

    }
}
