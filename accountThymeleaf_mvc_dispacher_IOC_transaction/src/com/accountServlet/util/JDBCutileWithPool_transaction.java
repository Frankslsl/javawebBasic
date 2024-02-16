package com.accountServlet.util;



import com.alibaba.druid.*;
import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.chrono.ThaiBuddhistChronology;
import java.util.Properties;

public class JDBCutileWithPool_transaction {
    /**
     * get connection with druid connections pool
     */
    private static DataSource source;
    private static ThreadLocal<Connection> threadLocal = new ThreadLocal<>();

    static {
        Properties pros = new Properties();
        InputStream is = JDBCutileWithPool_transaction.class.getResourceAsStream("/druid.properties");
        try {
            pros.load(is);
            source = DruidDataSourceFactory.createDataSource(pros);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection2() throws SQLException {
        Connection conn = source.getConnection();
        return conn;
    }

    public static Connection getConnectionWithLT() throws SQLException {
        Connection conn = threadLocal.get();
        if (conn == null) {
            conn = getConnection2();
            threadLocal.set(conn);
        }
        return threadLocal.get();
    }

    public void closeWithTL() {
        Connection conn = threadLocal.get();
        if (conn == null) {
            return;
        }
        close(conn);
        threadLocal.set(null);
    }

    /**
     * release the resource
     *
     * @param connection
     * @param statement
     * @param resultSet
     */
    public static void close(ResultSet resultSet, Statement statement, Connection connection) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * release the resource
     *
     * @param connection
     * @param statement
     */
    public static void close(Statement statement, Connection connection) {

        try {// seperated the try/catch can ensure the resource behind can be closed correctly
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * release the resource
     *
     * @param connection
     */
    public static void close(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

