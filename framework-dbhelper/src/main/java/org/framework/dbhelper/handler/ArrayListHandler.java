package org.framework.dbhelper.handler;

import org.framework.dbhelper.BeanUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ArrayListHandler extends AbstractListHandler<Object[]> {

    private Class<?> clazz;

    public ArrayListHandler(Class<?> clazz){
        this.clazz=clazz;
    }

    @Override
    protected Object[] getRow(ResultSet rs) throws SQLException {
        return BeanUtil.toArray(rs);
    }
}
