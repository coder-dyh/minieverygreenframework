package org.framework.beans;



import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SetMethodProperty implements org.framework.beans.Inf.Inject{

    @Override
    public void inject(Object target, BeanFactory beanFactory)
            throws IntrospectionException,IllegalAccessException,InvocationTargetException{
        analyseSetMethod(target,beanFactory);
    }

    /**
     * 递归遍历所有的Set方法并给需要注入的Set方法注入参数
     * @param target
     * @param beanFactory
     * @throws IntrospectionException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    private void analyseSetMethod(Object target, BeanFactory beanFactory)
            throws IntrospectionException,IllegalAccessException,InvocationTargetException{
        BeanInfo beanInfo= Introspector.getBeanInfo(target.getClass(),Object.class);
        PropertyDescriptor[] pds=beanInfo.getPropertyDescriptors();
        for(PropertyDescriptor pd : pds){
            Method setMethod=pd.getWriteMethod();
            if(setMethod.isAnnotationPresent(Inject.class)){
                String injectName=setMethod.getAnnotation(Inject.class).name();
                Object obj=beanFactory.getBean(injectName);
                setMethod.invoke(target,obj);
                analyseSetMethod(obj,beanFactory);
            }
        }

    }
}
