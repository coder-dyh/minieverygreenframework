package org.framework.integrate;

import org.framework.beans.BeanFactory;
import org.framework.web.factory.MVCFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Map;

/**
 * Create by coder_dyh on 2017/12/26
 */
public class ContextListener implements ServletContextListener{

    private static final String SCAN_PACKAGE="scanPackage";
    private static final String PLUGIN_FACTORY="PluginFactory";

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent){
        String path=servletContextEvent.getServletContext().getInitParameter(SCAN_PACKAGE);
        if(path!=null){
            BeanFactory beanFactory=new BeanFactory(path);
            Map<String,Object> map=beanFactory.singleton;
            System.out.println(map.size());
            for(String s : map.keySet()){
                System.out.println(map.get(s));
            }
            MVCFactory factory=new PluginFactory(beanFactory);
            servletContextEvent.getServletContext().setAttribute(PLUGIN_FACTORY,factory);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        PluginFactory pluginFactory=(PluginFactory) servletContextEvent.getServletContext().getAttribute(PLUGIN_FACTORY);
        try {
            pluginFactory.getBeanFactory().close();
        } catch (Exception e) {
            throw new RuntimeException("plugin close beanFactory failed");
        }
        servletContextEvent.getServletContext().removeAttribute(PLUGIN_FACTORY);
    }


}
