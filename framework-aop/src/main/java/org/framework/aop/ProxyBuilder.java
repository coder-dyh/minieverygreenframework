package org.framework.aop;

import org.framework.aop.annotation.Invocation;
import org.framework.aop.proxy.ProxyHandler;
import org.framework.aop.proxy.impl.CglibProxy;
import org.framework.aop.proxy.impl.JdkProxy;

import java.lang.reflect.Method;

/**
 * Create by coder_dyh on 2018/1/5
 */
public class ProxyBuilder {

    private Class<?> targetClass;

    private ProxyHandler handler;

    public ProxyBuilder(Class<?> targetClass){
        this.targetClass=targetClass;
        //初始化代理
        handler=initProxyHandler();
    }

    /**
     * 初始化代理对象
     * @return
     */
    private ProxyHandler initProxyHandler(){
        if(hasInterceptor()){
            return targetClass.getInterfaces().length > 0 ? new JdkProxy()
                    : new CglibProxy();
        }
        throw new RuntimeException("No target method can proxy.");
    }

    /**
     * 判断是否有代理的方法
     * @return boolean
     */
    private boolean hasInterceptor(){
        Method[] methods = targetClass.getDeclaredMethods();
        for(Method m : methods){
            m.setAccessible(true);
            if(m.isAnnotationPresent(Invocation.class)){
                return true;
            }
        }
        return false;
    }

    public <T> T createProxy(){
        return (T) handler.createProxy(targetClass);
    }


}
