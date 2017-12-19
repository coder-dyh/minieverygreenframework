package org.framework.dispatcher;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class HandlerDefinition {

    private Method method;
    private Class<?> clazz;


    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }
}
