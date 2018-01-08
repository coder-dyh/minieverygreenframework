package org.framework.aop;

import java.lang.reflect.Method;

/**
 * Create by coder_dyh on 2018/1/5
 */
public class AdviceInfo {

    /**
     * 通知
     */
    private Method advice;

    /**
     * 切面实例
     */
    private Object aspectInstance;

    public Method getAdvice() {
        return advice;
    }

    public void setAdvice(Method advice) {
        this.advice = advice;
    }

    public Object getAspectInstance() {
        return aspectInstance;
    }

    public void setAspectInstance(Object aspectInstance) {
        this.aspectInstance = aspectInstance;
    }
}
