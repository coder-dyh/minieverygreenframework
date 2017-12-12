package org.framework.beans.test;

import org.framework.beans.BeanException;
import org.framework.beans.BeanFactory;

import java.lang.reflect.InvocationTargetException;

public class Main {

    public static void main(String[] args) {
        BeanFactory factory=null;
        try {
            factory=new BeanFactory("org.framework.beans.test");
        } catch (BeanException e) {
            e.printStackTrace();
        }
        TestController controller=factory.getBean("testController",TestController.class);
        controller.saveUserController();
        try {
            factory.close();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
