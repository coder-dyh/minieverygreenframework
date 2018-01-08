package org.framework.beans.test;

import org.framework.beans.annotation.Component;
import org.framework.beans.annotation.Inject;
import org.framework.beans.annotation.Scope;

/**
 * Create by coder_dyh on 2018/1/8
 */
@Component("updateService")
@Scope("singleton")
public class UpdateService {

    @Inject(name = "updateDao")
    private UpdateDao updateDao;

    public void updateUserService(){
        updateDao.updateUser();
    }
}
