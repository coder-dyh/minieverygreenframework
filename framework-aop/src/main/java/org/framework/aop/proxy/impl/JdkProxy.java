package org.framework.aop.proxy.impl;

import org.framework.aop.proxy.ProxyHandler;
import org.framework.aop.proxy.proxyhandler.InvocationBuilder;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * Create by coder_dyh on 2018/1/8
 */
public class JdkProxy implements ProxyHandler{
    /**
     * 创建JDK执行代理
     * @Return <T> T 返回一个代理实例
     */
    @Override
    public <T> T createProxy(Class<?> targetClass){
        //实例化目标对象
        Object targetInstance = createTargetInstance(targetClass);
        //构建JDK回调上下文处理器
        InvocationHandler handler = new InvocationBuilder(targetInstance);
        //完成织入过程，创建好代理实例
        return (T) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                targetClass.getInterfaces(),handler);
    }

    /**
     * 创建代理实例
     * @param targetClass
     * @return Object
     */
    public Object createTargetInstance(Class<?> targetClass){
        try {
            return targetClass.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException("Build target instance failed.",e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Build target instance failed.",e);
        }
    }
}
