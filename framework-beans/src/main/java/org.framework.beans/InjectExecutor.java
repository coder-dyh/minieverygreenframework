package org.framework.beans;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;
import org.framework.beans.inject.Inject;

/**
 * 注入执行器
 */
public class InjectExecutor {

    /**
     * 注入接口的所有实现类
     */
    private static List<Inject> ins = new ArrayList<>();

    static {
        /**
         * 使用spi将所有注入方式的实现类初始化到list集合中
         * 注入方式接口
         */
        Iterator<Inject> it =
                ServiceLoader.load(Inject.class).iterator();
        while (it.hasNext()) {
            ins.add(it.next());
        }
    }

    /**
     * 注入处理器
     * @param target
     * @param beanFactory
     * @throws IntrospectionException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static void executionInject(Object target, BeanFactory beanFactory)
            throws IntrospectionException,InvocationTargetException,IllegalAccessException,InstantiationException{
        /**
         * 遍历所有注入行为实现
         * 并依次执行所有的注入方法
         */
        for(Inject inject : ins){
            inject.inject(target,beanFactory);
        }
    }

    public static void main(String[] args) {
        System.out.println(ins.size());
    }


}
