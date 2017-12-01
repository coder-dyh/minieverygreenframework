package org.framework.dbhelper.handler;


import org.framework.dbhelper.BeanUtil;
import org.framework.dbhelper.ResultSetHandler;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BeanHandler<T> implements ResultSetHandler<T> {

    private Class<?> clazz;

    public BeanHandler(Class<?> clazz){
        this.clazz=clazz;
    }

    @Override
    public T handle(ResultSet rs) throws SQLException{
        return rs.next()?(T) BeanUtil.createBean(clazz,rs):null;

    }
}
