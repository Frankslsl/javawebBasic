package com.accountServlet.service;

import com.accountServlet.bean.Account;
import com.accountServlet.daoUpdate.AccountDao;
import com.accountServlet.daoUpdate.AccountDaoImp;
import com.accountServlet.util.JDBCutileWithPool;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 */
public class AccountServiceImp implements AccountService {
    private AccountDao dao = new AccountDaoImp();
    private Connection conn = null;

    @Override
    public List<Account> getAccountList(String keyword, Integer pageNo, Integer limit) {
        try {
            conn = JDBCutileWithPool.getConnection2();
            return dao.getPagebyKeyword(conn, keyword, pageNo, limit);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCutileWithPool.close(conn);
        }
        return null;
    }

    @Override
    public void insert(Account account) {
        try {
            conn = JDBCutileWithPool.getConnection2();
            dao.insert(conn, account);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCutileWithPool.close(conn);
        }
    }

    @Override
    public Account getAccountById(Integer id) {
        try {
            conn = JDBCutileWithPool.getConnection2();
            return dao.getCustomerById(conn, id);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCutileWithPool.close(conn);
        }
        return null;
    }

    @Override
    public void delAccount(Integer id) {
        try {
            conn = JDBCutileWithPool.getConnection2();
            dao.deleteById(conn, id);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCutileWithPool.close(conn);
        }
    }

    @Override
    public Integer getPageCount(String keyword) {
        try {
            conn = JDBCutileWithPool.getConnection2();
            return dao.getPageCount(conn,keyword);
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            JDBCutileWithPool.close(conn);
        }
        return null;
    }

    @Override
    public void updateAccount(Account account) {
        try {
            conn = JDBCutileWithPool.getConnection2();
            dao.update(conn,account);
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            JDBCutileWithPool.close(conn);
        }
    }
}
