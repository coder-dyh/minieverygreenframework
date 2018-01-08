package org.framework.aop.proxy.context.impl;

import org.framework.aop.proxy.context.InvocationContextImpl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Create by coder_dyh on 2018/1/5
 */
public class JdkInvocationContext extends InvocationContextImpl {

    public JdkInvocationContext(Object target, Object[] parameters, Method method){
        super(target,parameters,method);
    }

    public JdkInvocationContext(){

    }

    @Override
    protected Object invokeTarget() {
        try {
            return method.invoke(target,parameters);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Invoking objective method failed.",e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException("Invoking objective method failed.",e);
        }
    }
}
