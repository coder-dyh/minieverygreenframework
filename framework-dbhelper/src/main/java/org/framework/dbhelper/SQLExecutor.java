package org.framework.dbhelper;

import java.sql.*;

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
}
