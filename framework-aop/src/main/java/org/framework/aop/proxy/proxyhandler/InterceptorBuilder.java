package org.framework.aop.proxy.proxyhandler;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.framework.aop.proxy.context.impl.CglibInvocationContext;

import java.lang.reflect.Method;

/**
 * Create by coder_dyh on 2018/1/8
 */
public class InterceptorBuilder implements MethodInterceptor{

    private Object target;

    public InterceptorBuilder(Object target){
        this.target=target;
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        //构建Cglib回调上下文
        CglibInvocationContext context = new CglibInvocationContext(target, objects, method);
        //设置代理方法以及代理代理对象
        context.setProxy(o);
        context.setMethodProxy(methodProxy);
        return context.invoke();
    }
}
