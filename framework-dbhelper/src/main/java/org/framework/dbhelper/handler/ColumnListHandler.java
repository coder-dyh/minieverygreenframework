package org.framework.dbhelper.handler;



import org.framework.dbhelper.BeanUtil;
import org.framework.dbhelper.handler.AbstractListHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ColumnListHandler<T> extends AbstractListHandler<List<T>> {

    private Class<?> clazz;
    private int columnIndex;

    public ColumnListHandler(Class<?> clazz,int columnIndex){
        this.clazz=clazz;
        this.columnIndex=columnIndex;
    }

    public <T> T getRow(ResultSet rs) throws SQLException{
        return (T) BeanUtil.createValue(rs,clazz,columnIndex);
    }

}
