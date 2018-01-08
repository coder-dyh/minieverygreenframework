package org.framework.beans.test;

import org.framework.beans.BeanFactory;

import java.lang.reflect.InvocationTargetException;

public class Main {

    public static void main(String[] args) {
        BeanFactory factory=null;
        try {
            factory=new BeanFactory("org.framework.beans.test");
        } catch (Exception e) {
            e.printStackTrace();
        }
        TestController controller=factory.getBean("testController",TestController.class);
        controller.saveUserController();
        controller.updateController();
        try {
            factory.close();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
