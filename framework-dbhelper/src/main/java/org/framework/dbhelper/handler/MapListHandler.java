package org.framework.dbhelper.handler;

import org.framework.dbhelper.BeanUtil;
import org.framework.dbhelper.handler.AbstractListHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class MapListHandler<T> extends AbstractListHandler<Map<String,Object>> {

    @Override
    protected T getRow(ResultSet rs) throws SQLException{
        return (T) BeanUtil.toMap(rs);
    }
}
