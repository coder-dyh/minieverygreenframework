package org.framework.dbhelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLExecutor {

    private Connection connection;
    //事务开关，默认设置为true
    private boolean autoClose=true;

    public SQLExecutor(Connection connection){
        this.connection=connection;
    }

    /**
     * 开启事务
     * @throws SQLException
     */
    public void beginTransation() throws SQLException{
        this.autoClose=false;
        //设置自动提交为false，不让它自动提交事务
        connection.setAutoCommit(autoClose);
    }

    /**
     * 提交事务
     * @throws SQLException
     */
    public void commit() throws SQLException{
        connection.commit();
    }

    /**
     * 回滚事务
     * @throws SQLException
     */
    public void rollback() throws SQLException{
        connection.rollback();
    }

    /**
     * 执行DQL操作
     * @param sql
     * @param handler
     * @param params
     * @param <T>
     * @return
     */
    public <T> T executeQuery(String sql,ResultSetHandler<T> handler,Object...params) throws SQLException{
        PreparedStatement ps=null;
        ResultSet rs=null;
        T t=null;
        try {
            ps=connection.prepareStatement(sql);
            setParameter(params,ps);
            rs=ps.executeQuery();
            t=handler.handle(rs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(rs);
            close(ps);
            if(autoClose){
                close(connection);
            }
        }

        return t;
    }

    /**
     * 批量添加
     * @param sql
     * @param params
     * @return
     * @throws SQLException
     */
    public int[] executeBatch(String sql,Object params[][]) throws SQLException{
        int rows[]=null;
        PreparedStatement ps=null;
        try {
            ps=connection.prepareStatement(sql);
            for(int i=0;i<params.length;i++){
                //循环设置参数
                setParameter(params[i],ps);
            }
            rows=ps.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(ps);
            if(autoClose){
                close(connection);
            }
        }

        return null;
    }

    /**
     * 执行DML操作
     * @param sql
     * @param params
     * @return
     */
    public int executeUpdate(String sql,Object...params) throws SQLException{
        PreparedStatement ps=null;
        ResultSet rs=null;
        int rows=0;
        try {
            ps=connection.prepareStatement(sql);
            setParameter(params,ps);
            rows=ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(ps);
        }

        return rows;
    }


    /**
     * 设置参数
     * @param params
     * @param ps
     */
    private void setParameter(Object[] params,PreparedStatement ps){
        for(int i=1;i<=params.length;i++){
            try {
                ps.setObject(i,params[i-1]);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 添加时能够加密
     * @param sql
     * @param params
     * @return Object 返回一个添加成功后加密好的key
     * @throws SQLException
     */
    public Object insert(String sql,Object...params) throws SQLException{
        PreparedStatement ps=connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
        setParameter(params,ps);
        Object generatedKey=null;
        try {
            ps.executeUpdate();
            ResultSet rs=ps.getGeneratedKeys();
            if(rs.next()){
                generatedKey=rs.getObject(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(ps);
            if(autoClose){
                close(connection);
            }
        }
        return generatedKey;
    }

    /**
     * 批量添加并加密
     * @param sql
     * @param params
     * @return Object[] 返回一个加密好的钥匙数组
     * @throws SQLException
     */
    public Object[] insertBatch(String sql,Object params[][]) throws SQLException{
        PreparedStatement ps=connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
        ResultSet rs=null;
        List generatedKeys=new ArrayList();
        for(int i=0;i<params.length;i++){
            setParameter(params[i],ps);
            ps.addBatch();
        }
        try {
            ps.executeBatch();
            rs=ps.getGeneratedKeys();
            while (rs.next()){
                generatedKeys.add(rs.getObject(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(ps);
            if (autoClose){
                close(connection);
            }
        }

        return generatedKeys.toArray();
    }

    /**
     * 关闭连接对象
     * @param rs
     * @throws SQLException
     */
    private void close(ResultSet rs) throws SQLException{
        if(rs!=null){
            rs.close();
        }
    }

    private void close(Statement stmt) throws SQLException{
        if(stmt!=null){
            stmt.close();
        }
    }

    private void close(Connection connection) throws SQLException{
        if(connection!=null){
            connection.close();
        }
    }


    public static void main(String[] args) {
        Object param[][]=new Object[][]{{2,1,3,4},{3,2}};
        System.out.println();
        System.out.println(param[1].length);

    }
}
