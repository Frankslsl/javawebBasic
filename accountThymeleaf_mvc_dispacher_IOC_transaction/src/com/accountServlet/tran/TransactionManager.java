package com.accountServlet.tran;

import com.accountServlet.util.JDBCutileWithPool;
import com.accountServlet.util.JDBCutileWithPool_transaction;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 */
public class TransactionManager {

    private ThreadLocal<Connection> threadLocal = new ThreadLocal<>();

    //开启事务
    public static void beginTrans() throws SQLException {
        Connection conn = JDBCutileWithPool_transaction.getConnectionWithLT();
        System.out.println("conn beginTrans= " + conn);
        conn.setAutoCommit(false);
    }

    public static void commit() throws SQLException {
        Connection conn = JDBCutileWithPool_transaction.getConnectionWithLT();
        conn.commit();
        conn.setAutoCommit(true);
        JDBCutileWithPool_transaction.getConnectionWithLT();
    }

    public static void rollBack() throws SQLException {
        Connection conn = JDBCutileWithPool_transaction.getConnectionWithLT();
        conn.rollback();
        System.out.println("conn rollback= " + conn);
        JDBCutileWithPool_transaction.getConnectionWithLT();
    }
}
