package com.accountServlet.service;

import com.accountServlet.bean.Account;
import com.accountServlet.daoUpdate.AccountDao;
import com.accountServlet.util.JDBCutileWithPool;
import com.accountServlet.util.JDBCutileWithPool_transaction;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 */
public class AccountServiceImp implements AccountService {
    private AccountDao accountDao = null;
    private Connection conn = null;

    public AccountServiceImp() throws SQLException {
        conn = JDBCutileWithPool_transaction.getConnectionWithLT();
    }

    @Override
//    测试localThread中的connection,所以将方法中的connection用localthread获取
    public List<Account> getAccountList(String keyword, Integer pageNo, Integer limit) throws SQLException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
        System.out.println("conn-->getAccountList = " + conn);
        return accountDao.getPagebyKeyword(conn, keyword, pageNo, limit);
    }

    @Override
    public void insert(Account account) throws SQLException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        accountDao.insert(conn, account);
    }

    @Override
    public Account getAccountById(Integer id) throws SQLException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return accountDao.getCustomerById(conn, id);
    }

    @Override
    public void delAccount(Integer id) throws SQLException {
        accountDao.deleteById(conn, id);
    }

    //    测试localThread中的connection,所以将方法中的connection用localthread获取
    @Override
    public Integer getPageCount(String keyword) throws SQLException {
        System.out.println("conn-->getPageCount = " + conn);
        return accountDao.getPageCount(conn, keyword);
    }

    @Override
    public void updateAccount(Account account) throws SQLException {
        accountDao.update(conn, account);



    }
}
