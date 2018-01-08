package org.framework.beans.test;

import org.framework.beans.annotation.Component;
import org.framework.beans.annotation.Inject;
import org.framework.beans.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component("testController")
@Scope("singleton")
public class TestController {

    @Inject(name = "testService")
    private TestService testService;

    @Inject(name = "updateService")
    private UpdateService updateService;

    @PostConstruct
    public void init(){
        System.out.println("在容器初始化之前进行了一些操作");
    }

    public void saveUserController(){
        testService.saveUserService();
    }

    public void updateController(){
        updateService.updateUserService();
    }

    @PreDestroy
    public void close(){
        System.out.println("关闭之前做了一些操作");
    }
}
