package org.framework.aop.proxy.proxyhandler;

import org.framework.aop.proxy.context.impl.JdkInvocationContext;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Create by coder_dyh on 2018/1/8
 */
public class InvocationBuilder implements InvocationHandler {

    private Object targetInstance;

    public InvocationBuilder(Object targetInstance){
        this.targetInstance = targetInstance;
    }

    public void createInvocationHandler(){
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Method targetMethod = targetInstance.getClass().getMethod(method.getName(),
                method.getParameterTypes());
        //创建Jdk回调上下文，执行回调处理
        JdkInvocationContext context = new JdkInvocationContext(targetInstance,args,targetMethod);
        return context.invoke();
    }
}
