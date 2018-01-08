package org.framework.aop;

import org.framework.aop.annotation.Around;
import org.framework.aop.annotation.Invocation;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Create by coder_dyh on 2018/1/5
 */
public class AdviceStackBuilder {

    /**
     * 创建通知栈
     * @param method
     * @return Stack<AdviceInfo>
     */
    public static Stack<AdviceInfo> createAdviceStack(Method method){
        Stack<AdviceInfo> adviceInfoStack=new Stack<>();
        resolveMethod(method,adviceInfoStack);
        return adviceInfoStack;
    }

    /**
     * 解析方法上面的注解
     */
    private static void resolveMethod(Method method,Stack<AdviceInfo> adviceInfoStack){
        if(method.isAnnotationPresent(Invocation.class)){
            Class<?>[] classes = method.getAnnotation(Invocation.class).value();
            for(Class<?> c : classes){
                Object aspectInstance = createAspectInstance(c);
                List<Method> list=getAroundMethod(c);
                for (Method m : list) {
                    AdviceInfo adviceInfo = createAdviceInfo(m, aspectInstance);
                    adviceInfoStack.push(adviceInfo);
                }
            }
        }

    }

    private static List<Method> getAroundMethod(Class<?> clazz){
        Method[] methods = clazz.getDeclaredMethods();
        List<Method> list = new ArrayList<>();
        for (Method m : methods) {
            if (m.isAnnotationPresent(Around.class)) {
                list.add(m);
            }
        }
        return list;
    }

//    private static void getAroundMethod(Class<?> c){
//        Method[] methods = c.getDeclaredMethods();
//        for(Method m : methods){
//
//        }
//    }



    /**
     * 创建代理方法的实例
     * @param aspectClass
     * @return Object
     */
    private static Object createAspectInstance(Class<?> aspectClass){
        try {
            return aspectClass.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException("Create aspect instance failed.",e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Create aspect instance failed.",e);
        }
    }

    /**
     * 创建通知的描述定义
     * @param adviceMethod
     * @param aspectInstance
     * @return AdviceInfo
     */
    private static AdviceInfo createAdviceInfo(Method adviceMethod,Object aspectInstance){
        AdviceInfo adviceInfo = new AdviceInfo();
        adviceInfo.setAdvice(adviceMethod);
        adviceInfo.setAspectInstance(aspectInstance);
        return adviceInfo;
    }

}
