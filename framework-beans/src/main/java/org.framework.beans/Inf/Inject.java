package org.framework.beans.Inf;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;

/**
 * 注入的接口
 */
public interface Inject {

    /**
     * 注入行为
     * @param target  需要注入的目标对象
     * @param clazz
     */
    void inject(String target, Class<?> clazz) throws IntrospectionException,IllegalAccessException,InstantiationException,InvocationTargetException;
}
