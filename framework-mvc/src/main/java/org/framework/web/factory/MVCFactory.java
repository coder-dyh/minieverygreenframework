package org.framework.web.factory;

import org.framework.web.HandlerDefinition;

/**
 * Create by coder_dyh on 2017/12/26
 */
public interface MVCFactory {

    Object createInstance(HandlerDefinition definition);
}
