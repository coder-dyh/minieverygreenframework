package org.framework.dbhelper.handler;



import org.framework.dbhelper.BeanUtil;
import org.framework.dbhelper.ResultSetHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class MapHandler<T> implements ResultSetHandler<Map<String,Object>> {

    private int columnIndex;

    public MapHandler(int columnIndex,Class<?> clazz){
        this.columnIndex=columnIndex;
    }

    @Override
    public Map<String,Object> handle(ResultSet rs) throws SQLException{
        return rs.next()? BeanUtil.toMap(rs,columnIndex):null;
    }

}
