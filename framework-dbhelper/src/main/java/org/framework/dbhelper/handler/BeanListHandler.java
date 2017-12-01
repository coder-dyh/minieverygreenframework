package org.framework.dbhelper.handler;



import org.framework.dbhelper.BeanUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BeanListHandler<T> extends AbstractListHandler<T> {

    private Class<?> clazz;

    public BeanListHandler(Class<?> clazz){
        this.clazz=clazz;
    }

    @Override
    protected T getRow(ResultSet rs) throws SQLException{
        return (T) BeanUtil.createBean(clazz,rs);
    }
}
