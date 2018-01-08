package org.framework.beans.test;

import org.framework.beans.annotation.Component;
import org.framework.beans.annotation.Inject;
import org.framework.beans.annotation.Scope;
import org.omg.CORBA.PRIVATE_MEMBER;

@Component("testService")
@Scope("prototype")
public class TestService {

    @Inject(name="testDao")
    private TestDao testDao;

    public void saveUserService(){
        testDao.saveUser();
    }
}
