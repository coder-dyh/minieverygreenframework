package org.framework.beans;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

/**
 * 注入执行器
 */
public class InjectExecutor {

    /**
     * 注入接口的所有实现类
     */
    private static List<org.framework.beans.Inf.Inject> ins = new ArrayList<>();

    static {
        /**
         * 使用spi将所有注入方式的实现类初始化到list集合中
         * 注入方式接口
         */
        Iterator<org.framework.beans.Inf.Inject> it =
                ServiceLoader.load(org.framework.beans.Inf.Inject.class).iterator();
        while (it.hasNext()) {
            ins.add(it.next());
        }
    }

    /**
     * 执行注入行为
     *
     * @param className
     * @param clazz
     */
    public static void executionInject(String className, Class<?> clazz)
            throws IntrospectionException,InvocationTargetException,IllegalAccessException,InstantiationException{
        System.out.println(ins.size());

        /**
         * 遍历所有注入行为实现
         * 并依次执行所有的注入方法
         */
        for(org.framework.beans.Inf.Inject inject : ins){
            inject.inject(className,clazz);
        }
    }
}
