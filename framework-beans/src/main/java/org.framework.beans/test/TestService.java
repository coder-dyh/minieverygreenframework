package org.framework.beans.test;

import org.framework.beans.Component;
import org.framework.beans.Inject;
import org.framework.beans.Scope;

@Component("testService")
@Scope("prototype")
public class TestService {

    @Inject(name="testDao")
    private TestDao testDao;

    public void saveUserService(){
        testDao.saveUser();
    }
}
