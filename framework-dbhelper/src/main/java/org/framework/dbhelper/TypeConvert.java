package org.framework.dbhelper;

import java.sql.ResultSet;
import java.sql.SQLException;

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
        }

        return value;
    }
}
