package org.framework.beans.inject;

import org.framework.beans.BeanFactory;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;

/**
 * 注入的接口
 */
public interface Inject {

    /**
     * 注入的抽象方法
     * @param target
     * @param beanFactory
     * @throws IntrospectionException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws InvocationTargetException
     */
    void inject(Object target, BeanFactory beanFactory) throws IntrospectionException,IllegalAccessException,InstantiationException,InvocationTargetException;
}
