package org.framework.dbhelper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 注意：在此处类上定义的范型称为范型类，用于将类实例化是一起指定具体类型
 * 只有在调用者中指定好了类范型时，handle方法才知道我需要返回什么类型
 * @param <T>
 */
public interface ResultSetHandler<T> {

    /**
     * 将结果集封装成哪种类型
     * @param rs
     * @return
     */
    T handle(ResultSet rs) throws SQLException;
}
