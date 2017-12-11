package org.framework.beans;



import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SetMethodProperty implements org.framework.beans.Inf.Inject{

    @Override
    public void inject(String target, Class<?> clazz)
            throws IntrospectionException, IllegalAccessException, InstantiationException,InvocationTargetException {

            Method[] methods=clazz.getDeclaredMethods();
            for(Method m: methods){
                if(m.isAnnotationPresent(Inject.class)){
                    m.setAccessible(true);
                    String injectName=m.getAnnotation(Inject.class).name();
                    Class<?> cls=BeanFactory.getComponentNameClass().get(injectName);
                    //为set方法注入实现类
                    m.invoke(BeanFactory.getBean(injectName),cls.newInstance());
                    inject(injectName,cls);
                }
            }


    }
}
