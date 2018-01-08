package org.framework.aop.proxy.context;

import java.lang.reflect.Method;

/**
 * Create by coder_dyh on 2018/1/5
 */
public interface InvocationContext {

    /**
     * 获取目标对象方法的所有参数
     * @return Object[]
     */
    Object[] getParameters();

    /**
     * 获取目标对象
     * @return Object
     */
    Object getTarget();

    /**
     * 获取目标对象的方法
     * @return Method
     */
    Method method();

    /**
     * 回调处理
     * @return Object
     */
    Object proceed();
    
}
