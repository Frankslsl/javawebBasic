package com.accountServlet.util;



import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * Using C3P0 connection pool
 */
public class JDBCutileWithPool {
    /**
/**
 * get connection with druid connections pool
 */
    private static DataSource source;

    static {
        Properties pros = new Properties();
        InputStream is = JDBCutileWithPool.class.getResourceAsStream("/druid.properties");
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

