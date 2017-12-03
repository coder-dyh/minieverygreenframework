package org.framework.dbhelper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * 从数据库中取出对应的数据，这里要进行判断的原因是因为从数据库中取出的数据并不知道是什么数据类型
 */
public class TypeConvert {

    public static Object convert(ResultSet rs,String fieldName,Class type) throws SQLException{
        if(rs.getObject(fieldName)==null){
            throw new RuntimeException("值为null，无法转换");
        }
        Object value=null;
        if(type.equals(String.class)){
            value=rs.getString(fieldName);
        }else if(type.equals(Integer.TYPE) || type.equals(Integer.class)){
            value=rs.getInt(fieldName);
        }else if(type.equals(Double.TYPE) || type.equals(Double.class)){
            value=rs.getDouble(fieldName);
        }else if(type.equals(Long.TYPE) || type.equals(Long.class)){
            value=rs.getLong(fieldName);
        }else if(type.equals(Short.TYPE) || type.equals(Short.class)){
            value=rs.getShort(fieldName);
        }else if(type.equals(Byte.TYPE) || type.equals(Byte.class)){
            value=rs.getByte(fieldName);
        }else if(type.equals(Float.TYPE) || type.equals(Float.class)){
            value=rs.getFloat(fieldName);
        }else if(type.equals(Boolean.TYPE) || type.equals(Boolean.class)){
            value=rs.getBoolean(fieldName);
        }else if(type.equals(Character.TYPE) || type.equals(Character.class)){
            value=rs.getString(fieldName).charAt(0);
        }else{
            value=rs.getObject(fieldName);
            dateConvert(value,type);
        }

        return value;
    }

    /**
     * 时间转换工具
     * @param value
     * @param type
     * @return
     */
    private static Object dateConvert(Object value,Class<?> type){
        if (type.equals(java.sql.Date.class)){
            value=new java.sql.Date(((java.util.Date)value).getTime());
        }else if (type.equals(java.sql.Time.class)){
            value=new java.sql.Time(((java.util.Date)value).getTime());
        }else {
            Timestamp ts=(Timestamp) value;
            //获得时间戳的最小精度
            int nanos=ts.getNanos();
            value=new java.sql.Timestamp(ts.getTime());
            ts.setNanos(nanos);
        }
        return value;
    }
}
