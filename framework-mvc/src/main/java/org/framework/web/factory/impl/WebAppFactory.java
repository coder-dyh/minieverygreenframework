package org.framework.web.factory.impl;

import org.framework.web.HandlerDefinition;
import org.framework.web.factory.MVCFactory;

/**
 * Create by coder_dyh on 2017/12/26
 */
public class WebAppFactory implements MVCFactory{

    @Override
    public Object createInstance(HandlerDefinition definition) {
        Object obj=null;
        try {
            obj=definition.getMethod().getDeclaringClass().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return obj;
    }
}
