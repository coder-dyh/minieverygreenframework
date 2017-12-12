package org.framework.beans.test;

import org.framework.beans.Component;
import org.framework.beans.Inject;
import org.framework.beans.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component("testController")
@Scope("singleton")
public class TestController {

    @Inject(name = "testService")
    private TestService testService;

    @PostConstruct
    public void init(){
        System.out.println("在容器初始化之前进行了一些操作");
    }

    public void saveUserController(){
        testService.saveUserService();
    }

    @PreDestroy
    public void close(){
        System.out.println("关闭之前做了一些操作");
    }
}
