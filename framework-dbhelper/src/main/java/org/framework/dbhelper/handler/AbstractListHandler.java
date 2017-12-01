package org.framework.dbhelper.handler;


import org.framework.dbhelper.ResultSetHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractListHandler<T> implements ResultSetHandler<List<T>> {

    @Override
    public List<T> handle(ResultSet rs) throws SQLException{
        List<T> list=new ArrayList<>();
        while (rs.next()){
            list.add(getRow(rs));
        }
        return list;
    }

    protected abstract <T> T getRow(ResultSet rs) throws SQLException;
}
