package org.framework.beans.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Target作用的目标位置（可以是方法，属性，构造器，类上）
 * Retention 作用的时机（可以是编译器，运行时，加载时等等）
 */
@Target({ElementType.FIELD,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Inject {
    String name();
}
