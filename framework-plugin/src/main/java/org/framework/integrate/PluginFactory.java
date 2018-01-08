package org.framework.integrate;


import org.framework.beans.BeanFactory;
import org.framework.beans.annotation.Component;
import org.framework.web.HandlerDefinition;
import org.framework.web.factory.MVCFactory;
import org.framework.web.utils.FirstCharToLowerCase;

import java.lang.reflect.Method;

/**
 * Create by coder_dyh on 2017/12/26
 */
public class PluginFactory implements MVCFactory{

    private BeanFactory beanFactory;

    public PluginFactory(){

    }
    public PluginFactory(BeanFactory beanFactory){
        this.beanFactory=beanFactory;
    }

    public Object createInstance(HandlerDefinition definition){
        if(definition==null){
            throw new RuntimeException("The plugin's HandlerDefinition is null");
        }
        Method method=definition.getMethod();
        if(method!=null){
            return beanFactory.getBean(getBeanName(method));
        }else{
            throw new RuntimeException("Plugin can't create instance because the method is null");
        }

    }

    protected String getBeanName(Method method) {
        if (method.getDeclaringClass().getAnnotation(Component.class) == null)
            throw new RuntimeException("The plugin of the class haven't a component annotation");
        String beanName = method.getDeclaringClass()
                .getAnnotation(Component.class).value();
        beanName = ("".equals(beanName)) ? FirstCharToLowerCase.toLowerCaseFirstOne((method
                .getDeclaringClass().getSimpleName())) : beanName;
        return beanName;
    }

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }
}
