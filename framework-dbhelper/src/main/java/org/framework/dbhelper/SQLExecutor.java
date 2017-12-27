package org.framework.dbhelper;

import org.framework.dbhelper.exception.CloseException;
import org.framework.dbhelper.exception.SQLExecutorException;
import org.framework.dbhelper.exception.TransactionException;

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
    public void beginTransaction(){
        this.autoClose=false;
        //设置自动提交为false，不让它自动提交事务
        try {
            connection.setAutoCommit(autoClose);
        } catch (SQLException e) {
            throw new TransactionException("Begin transaction failed.",e);
        }
    }

    /**
     * 提交事务
     * @throws SQLException
     */
    public void commit(){
        try {
            connection.commit();
        } catch (SQLException e) {
            throw new TransactionException("Commit transaction failed.",e);
        }
    }

    /**
     * 回滚事务
     * @throws SQLException
     */
    public void rollback(){
        try {
            connection.rollback();
        } catch (SQLException e) {
            throw new TransactionException("Rollback transaction failed.",e);
        }
    }

    /**
     * 执行DQL操作
     * @param sql
     * @param handler
     * @param params
     * @param <T>
     * @return
     */
    public <T> T executeQuery(String sql,ResultSetHandler<T> handler,Object...params){

        PreparedStatement ps=null;
        ResultSet rs=null;
        T t=null;
        try {
            ps=connection.prepareStatement(sql);
            setParameter(params,ps);
            rs=ps.executeQuery();
            t=handler.handle(rs);
        } catch (Exception e) {
            throw new SQLExecutorException("Execute query failed.",e);
        } finally {
            try {
                close(rs);
                close(ps);
                if(autoClose){
                    close(connection);
                }
            } catch (SQLException e) {
                throw new SQLExecutorException("Execute query failed.",e);
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
    public void executeBatch(String sql,Object params[][]){
        PreparedStatement ps=null;
        try {
            ps=connection.prepareStatement(sql);
            for(int i=0;i<params.length;i++){
                //循环设置参数
                setParameter(params[i],ps);
            }
            ps.executeBatch();
        } catch (SQLException e) {
            throw new SQLExecutorException("Execute batch failed.",e);
        } finally {
            try {
                close(ps);
                if(autoClose){
                    close(connection);
                }
            } catch (SQLException e) {
                throw new CloseException("close failed.",e);
            }
        }

    }

    /**
     * 执行DML操作
     * @param sql
     * @param params
     * @return
     */
    public void executeUpdate(String sql,Object...params){
        PreparedStatement ps=null;
        ResultSet rs=null;
        try {
            ps=connection.prepareStatement(sql);
            setParameter(params,ps);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLExecutorException("Execute update failed.",e);
        } finally {
            try {
                close(ps);
            } catch (SQLException e) {
                throw new CloseException("close failed.",e);
            }
        }
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
     * 添加成功后返回记录的主键的值
     * @param sql
     * @param params
     * @return Object 返回一个添加成功后返回添加的那条记录的主键的值
     * @throws SQLException
     */
    public Object insert(String sql,Object...params){
        PreparedStatement ps= null;
        Object generatedKey= null;
        try {
            ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            setParameter(params,ps);
            generatedKey = null;

            ps.executeUpdate();
            ResultSet rs=ps.getGeneratedKeys();
            if(rs.next()){
                generatedKey=rs.getObject(1);
            }
        } catch (SQLException e) {
            throw new SQLExecutorException("Execute insert failed.",e);
        }
         finally {
            try {
                close(ps);
                if(autoClose){
                    close(connection);
                }
            } catch (SQLException e) {
                throw new CloseException("close failed",e);
            }
        }
        return generatedKey;
    }

    /**
     * 批量添加并返回主键的值
     * @param sql
     * @param params
     * @return Object[] 返回一个包含所有主键的值Object[]数组
     * @throws SQLException
     */
    public Object[] insertBatch(String sql,Object params[][]){
        PreparedStatement ps=null;
        List generatedKeys=new ArrayList();
        try {
            ps=connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            ResultSet rs=null;

            for(int i=0;i<params.length;i++){
                setParameter(params[i],ps);
                ps.addBatch();
            }

            ps.executeBatch();
            rs=ps.getGeneratedKeys();
            while (rs.next()){
                generatedKeys.add(rs.getObject(1));
            }
        } catch (SQLException e) {
            throw new SQLExecutorException("Execute insertBatch failed.",e);
        } finally {
            try {
                close(ps);
                if (autoClose){
                    close(connection);
                }
            } catch (SQLException e) {
                throw new CloseException("Close failed.",e);
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
