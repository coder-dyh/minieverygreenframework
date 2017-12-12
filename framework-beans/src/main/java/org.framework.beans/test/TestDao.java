package org.framework.beans.test;

import org.framework.beans.Component;
import org.framework.beans.Scope;

@Component("testDao")
@Scope("prototype")
public class TestDao {


    public void saveUser(){
        System.out.println("INSERT INTO USER_INFO VALUES(?,?,?,?)");
    }
}
