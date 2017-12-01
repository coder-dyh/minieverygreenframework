package org.framework.dbhelper.handler;



import org.framework.dbhelper.BeanUtil;
import org.framework.dbhelper.ResultSetHandler;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ColumnHandler<T> implements ResultSetHandler<Object> {

    //字段类型对应的java类型
    private Class<?> clazz;
    private int columnIndex;

    public ColumnHandler(Class<?> clazz,int columnIndex){
        this.clazz=clazz;
        this.columnIndex=columnIndex;
    }

    @Override
    public Object handle(ResultSet rs) throws SQLException{
       return rs.next()? BeanUtil.createValue(rs,clazz,columnIndex):null;

    }
}
