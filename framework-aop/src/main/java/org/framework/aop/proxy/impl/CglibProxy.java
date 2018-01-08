package org.framework.aop.proxy.impl;

import net.sf.cglib.proxy.Enhancer;
import org.framework.aop.proxy.ProxyHandler;
import org.framework.aop.proxy.proxyhandler.InterceptorBuilder;

/**
 * Create by coder_dyh on 2018/1/8
 */
public class CglibProxy implements ProxyHandler{

    private Enhancer createEnhancer(Class<?> targetClass){
        Enhancer enhancer=new Enhancer();
        enhancer.setSuperclass(targetClass);
        enhancer.setCallback(new InterceptorBuilder(createTargetInstance(targetClass)));
        return enhancer;
    }

    public Object createTargetInstance(Class<?> targetClass){
        Object obj = null;
        try {
            obj = targetClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return obj;
    }

    @Override
    public <T> T createProxy(Class<?> targetClass){
        Enhancer enhancer=createEnhancer(targetClass);
        T t=(T)enhancer.create();
        return t;
    }
}
