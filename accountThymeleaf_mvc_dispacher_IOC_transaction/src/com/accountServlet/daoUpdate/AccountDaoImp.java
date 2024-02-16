package com.accountServlet.daoUpdate;


import com.accountServlet.bean.Account;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

/**
 *
 */
public class AccountDaoImp extends BaseDao<Account> implements AccountDao {
    @Override
    public int insert(Connection conn, Account account) throws SQLException {
        String sql = " insert into account (name, balance,birthday)values(?,?,?)";
        return updateWithoutConn(conn, sql, account.getName(), account.getBalance(), account.getBirthday());
    }

    @Override
    public int deleteById(Connection conn, int id) throws SQLException {
        String sql = "delete from account where id = ?";
        return updateWithoutConn(conn, sql, id);
    }

    @Override
    public int update(Connection conn, Account account) throws SQLException {
        String sql = "update account set name = ?, balance = ?, birthday = ? where id =?";
        return updateWithoutConn(conn, sql, account.getName(), account.getBalance(), account.getBirthday(), account.getId());
    }

    @Override
    public Account getCustomerById(Connection conn, int id) throws SQLException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String sql = "select id,name,birthday,balance from account where id = ?";
        Account account = queryInstanceWithoutConn(conn, sql, id);
        return account;
    }

    @Override
    public List<Account> getAll(Connection conn) throws SQLException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
        String sql = "select id,name,birthday,balance from account";
        List<Account> accounts = queryList(conn, sql);
        return accounts;
    }

    @Override
    public Long getCount(Connection conn) throws SQLException {
        String sql = "select count(1) from account";
        return getValue(conn, sql);
    }

    @Override
    public Date getMaxBirthday(Connection conn) throws SQLException {
        String sql = "select max(birthday) from account";
        return getValue(conn, sql);
    }

    @Override
    public List<Account> getAllbyPage(Connection conn, Integer pageNo, Integer limit) throws SQLException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
        String sql = "select * from account limit ? , ?";
        List<Account> list = queryList(conn, sql, pageNo, limit);
        return list;
    }

    @Override
    public List<Account> getPagebyKeyword(Connection conn, String keyword, Integer pageNum, Integer limit) throws SQLException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
        String sql = "select * from account where name like ? limit ?,?";
        List<Account> list = queryList(conn, sql, "%" + keyword + "%", pageNum, limit);
        return list;
    }

    @Override
    public Long getCountWithKeyword(Connection conn, String keyword) throws SQLException {
        String sql = "select count(1) from account where name like ?";
        return getValue(conn, sql, "%" + keyword + "%");
    }

    @Override
    public Integer getPageCount(Connection conn, String keyword) throws SQLException {
        Integer countTotal = getCountWithKeyword(conn, keyword).intValue();
        Integer count = (countTotal + 2) / 3;
        return count;

    }
}
