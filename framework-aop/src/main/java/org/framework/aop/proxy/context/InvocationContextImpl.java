package org.framework.aop.proxy.context;

import org.framework.aop.AdviceInfo;
import org.framework.aop.AdviceStackBuilder;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Stack;

/**
 * Create by coder_dyh on 2018/1/5
 */
public abstract class InvocationContextImpl implements InvocationContext {

    /**
     * 需要代理的目标对象
     */
    protected Object target;

    /**
     * 代理的方法需要的参数
     */
    protected Object[] parameters;

    /**
     * 存放需要代理方法的栈
     */
    protected Stack<AdviceInfo> adviceInfoStack;

    /**
     * 代理的方法
     */
    protected Method method;

    public InvocationContextImpl(){

    }

    public InvocationContextImpl(Object target,Object[] parameters,Method method){
        this.target=target;
        this.parameters=parameters;
        this.method=method;
        //初始化通知栈
        createAdviceStack();
    }

    @Override
    public Object[] getParameters() {
        return parameters;
    }

    @Override
    public Object getTarget() {
        return target;
    }

    @Override
    public Method method() {
        return method;
    }

    /**
     * 创建环绕通知
     */
    private void createAdviceStack(){
        adviceInfoStack= AdviceStackBuilder.createAdviceStack(method);
    }

    /**
     * 调用拦截器栈（递归调用）
     * @return Object
     */
    @Override
    public Object proceed() {
        while(!adviceInfoStack.empty()){
            AdviceInfo adviceInfo=adviceInfoStack.pop();
            try {
                return adviceInfo.getAdvice().invoke(adviceInfo.getAspectInstance(),this);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Invoke target adviceInfo stack failed.",e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException("Invoke target adviceInfo stack failed.",e);
            }
        }
        return invokeTarget();
    }

    /**
     * 执行代理方法
     * @return
     */
    public Object invoke(){
        if(!method.getDeclaringClass().equals(Object.class)){
            return proceed();
        }
        return invokeTarget();
    }

    protected abstract Object invokeTarget();
}
