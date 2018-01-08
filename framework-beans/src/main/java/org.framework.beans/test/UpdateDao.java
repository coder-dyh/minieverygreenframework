package org.framework.beans.test;

import org.framework.beans.annotation.Component;
import org.framework.beans.annotation.Scope;

/**
 * Create by coder_dyh on 2018/1/8
 */
@Component("updateDao")
@Scope("singleton")
public class UpdateDao {

    public void updateUser(){
        System.out.println("UPDATE STUDENT SET USER_ID=123 WHERE NAME=DYH");
    }
}
