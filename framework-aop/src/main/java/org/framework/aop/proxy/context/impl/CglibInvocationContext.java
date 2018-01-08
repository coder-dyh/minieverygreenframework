package org.framework.aop.proxy.context.impl;

import net.sf.cglib.proxy.MethodProxy;
import org.framework.aop.proxy.context.InvocationContextImpl;

import java.lang.reflect.Method;

/**
 * Create by coder_dyh on 2018/1/5
 */
public class CglibInvocationContext extends InvocationContextImpl {

    private MethodProxy methodProxy;

    private Object proxy;

    public CglibInvocationContext(Object obj, Object[] parameters, Method method){
        super(obj, parameters, method);
    }

    public void setMethodProxy(MethodProxy methodProxy) {
        this.methodProxy = methodProxy;
    }

    public void setProxy(Object proxy) {
        this.proxy = proxy;
    }

    @Override
    protected Object invokeTarget() {
        Object obj=null;
        try {
             obj = methodProxy.invokeSuper(proxy, parameters);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return obj;
    }
}
