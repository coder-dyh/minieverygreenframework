package org.framework.dbhelper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {

    private static String URL = "jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=UTF-8";

    private static String DRIVER = "com.mysql.jdbc.Driver";

    private static String USER_NAME = "root";

    private static String PASSWORD = "root";

    static {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection(){
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL,USER_NAME,PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return conn;
    }

    public static void main(String[] args) {
        System.out.println(getConnection());
    }
}
