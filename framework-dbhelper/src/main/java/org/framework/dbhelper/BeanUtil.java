package org.framework.dbhelper;

import org.framework.dbhelper.annotation.Column;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class BeanUtil {

    private final static Map<Class<?>,Object> primitiveDefaults=new HashMap<>();

    //valueOf()方法用于返回指定类型的原生数据类型，此外valueOf()还能用来进行类型转换
    //例如：String.valueOf(double b);将double类型的值转换为String类型
    static {
        primitiveDefaults.put(Integer.TYPE,Integer.valueOf(0));
        primitiveDefaults.put(Double.TYPE,Double.valueOf(0d));
        primitiveDefaults.put(Float.TYPE,Float.valueOf(0f));
        primitiveDefaults.put(Long.TYPE,Long.valueOf(0L));
        primitiveDefaults.put(Boolean.TYPE, Boolean.valueOf(false));
        primitiveDefaults.put(Character.TYPE,Character.valueOf((char) 0));
        primitiveDefaults.put(Short.TYPE,Short.valueOf((short) 0));
        primitiveDefaults.put(Byte.TYPE,Byte.valueOf((byte) 0));
    }

    /**
     * 创建bean对象
     * @param clazz
     * @param rs
     * @return
     * @throws SQLException
     */
    public static Object createBean(Class<?> clazz, ResultSet rs) throws SQLException{
        //获得对应的Class对象的实例
        Object bean= null;
        try {
            bean = clazz.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        //获得结果集信息
        ResultSetMetaData rsmd= null;
        try {
            rsmd = rs.getMetaData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //循环表下面的所有字段

            for(int i=1;i<=rsmd.getColumnCount();i++){
                String columnName=rsmd.getColumnLabel(i);
                setProperties(bean,rs,columnName,clazz);
            }

        return bean;
    }

    /**
     *将一条（行）记录转换为Map集合
     * @param rs
     * @return 返回指定的Map集合
     */
    public static Map<String,Object> toMap(ResultSet rs) throws SQLException{
        Map<String,Object> map=new HashMap<>();
        ResultSetMetaData rsmd=null;
        rsmd=rs.getMetaData();
        for(int i=1;i<=rsmd.getColumnCount();i++){
            map.put(rsmd.getColumnLabel(i),rs.getObject(i));
        }
        return map;
    }

    /**
     * 将一行数据转换为数组
     * @param rs
     * @return
     * @throws SQLException
     */
    public static Object[] toArray(ResultSet rs) throws SQLException{
        ResultSetMetaData rsmd=rs.getMetaData();
        Object[] rowResult=new Object[rsmd.getColumnCount()];
        for(int i=1;i<=rowResult.length;i++){
            rowResult[i-1]=rs.getObject(i);
        }
        return rowResult;
    }

    /**
     * 返回指定字段序号的Map值（只有一个值）
     * @param rs
     * @param columnIndex
     * @return
     */
    public static Map<String,Object> toMap(ResultSet rs,int columnIndex) throws SQLException{
        Map<String,Object> map=new HashMap<>();
        map.put(rs.getMetaData().getColumnLabel(columnIndex),rs.getObject(columnIndex));
        return map;
    }

    /**
     * 返回指定字段索引的值
     * @param rs
     * @param clazz
     * @param columnIndex
     * @return
     * @throws SQLException
     * @throws Exception
     */
    public static Object createValue(ResultSet rs,Class<?> clazz,int columnIndex) throws SQLException{
        ResultSetMetaData rsmd=rs.getMetaData();
        String columnName=null;
        //获得指定字段索引的值
        Object value=rs.getObject(columnIndex);
        if(value!=null){
            columnName=rsmd.getColumnLabel(columnIndex);
        }
        value=TypeConvert.convert(rs,columnName,clazz);
        return value;
    }

    private static void setProperties(Object bean,ResultSet rs,String columnName,Class<?> clazz) throws SQLException{
        //获得实体下的所有属性
        Field[] fields=clazz.getDeclaredFields();
        String fieldName=null;
        for(Field f:fields){
            f.setAccessible(true);
            if(f.isAnnotationPresent(Column.class)){
                fieldName=f.getAnnotation(Column.class).value();
            }else{
                fieldName=f.getName();
            }

            if(columnName.equalsIgnoreCase(fieldName)){
                //获得属性的类型
                Class type=f.getType();
                //将实体各个属性进行类型转换
                Object value= TypeConvert.convert(rs,fieldName,type);
                //如果取出的值为null且属性的类型是八大基本类型则给基本类型赋初始值
                if(value==null && f.getType().isPrimitive()){
                    value=primitiveDefaults.get(f.getType());
                }
                try {
                    f.set(bean,value);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }
}
