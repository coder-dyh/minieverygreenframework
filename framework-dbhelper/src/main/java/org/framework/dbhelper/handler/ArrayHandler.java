package org.framework.dbhelper.handler;


import org.framework.dbhelper.BeanUtil;
import org.framework.dbhelper.ResultSetHandler;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ArrayHandler<T> implements ResultSetHandler<Object[]> {

    @Override
    public Object[] handle(ResultSet rs) throws SQLException{
        return rs.next()? BeanUtil.toArray(rs):null;

    }
}


