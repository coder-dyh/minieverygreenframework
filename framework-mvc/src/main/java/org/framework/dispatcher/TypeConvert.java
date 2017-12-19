package org.framework.dispatcher;

import org.framework.dispatcher.TypeExecutor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;

/**
 * 处理类型转换的接口
 */
public interface TypeConvert {

    Object convert(Parameter parameter,TypeExecutor executor) throws InvocationTargetException,InstantiationException,IllegalAccessException;
}
