package org.framework.aop.proxy;

/**
 * Create by coder_dyh on 2018/1/8
 */
public interface ProxyHandler {
    /**
     * 创建代理的抽象接口
     * @param <T>
     * @return
     */
    <T> T createProxy(Class<?> targetClass);
}
