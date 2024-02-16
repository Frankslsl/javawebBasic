package com.accountServlet.daoUpdate;


import com.accountServlet.bean.Account;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

/**
 *
 */
public class AccountDaoImp extends BaseDao<Account> implements AccountDao {
    @Override
    public int insert(Connection conn, Account account) {
        String sql = " insert into account (name, balance,birthday)values(?,?,?)";
        return updateWithoutConn(conn, sql, account.getName(), account.getBalance(), account.getBirthday());
    }

    @Override
    public int deleteById(Connection conn, int id) {
        String sql = "delete from account where id = ?";
        return updateWithoutConn(conn, sql, id);
    }

    @Override
    public int update(Connection conn, Account account) {
        String sql = "update account set name = ?, balance = ?, birthday = ? where id =?";
        return updateWithoutConn(conn, sql, account.getName(), account.getBalance(), account.getBirthday(), account.getId());
    }

    @Override
    public Account getCustomerById(Connection conn, int id) {
        String sql = "select id,name,birthday,balance from account where id = ?";
        Account account = queryInstanceWithoutConn(conn, sql, id);
        return account;
    }

    @Override
    public List<Account> getAll(Connection conn) {
        String sql = "select id,name,birthday,balance from account";
        List<Account> accounts = queryList(conn, sql);
        return accounts;
    }

    @Override
    public Long getCount(Connection conn) {
        String sql = "select count(1) from account";
        return getValue(conn, sql);
    }

    @Override
    public Date getMaxBirthday(Connection conn) {
        String sql = "select max(birthday) from account";
        return getValue(conn, sql);
    }

    @Override
    public List<Account> getAllbyPage(Connection conn, Integer pageNo, Integer limit) {
        String sql = "select * from account limit ? , ?";
        List<Account> list = queryList(conn, sql, pageNo, limit);
        return list;
    }

    @Override
    public List<Account> getPagebyKeyword(Connection conn, String keyword, Integer pageNum, Integer limit) {
        String sql = "select * from account where name like ? limit ?,?";
        List<Account> list = queryList(conn, sql, "%" + keyword + "%", pageNum, limit);
        return list;
    }

    @Override
    public Long getCountWithKeyword(Connection conn, String keyword) {
        String sql = "select count(1) from account where name like ?";
        return getValue(conn, sql, "%" + keyword + "%");
    }

    @Override
    public Integer getPageCount(Connection conn, String keyword) {
        Integer countTotal = getCountWithKeyword(conn, keyword).intValue();
        Integer count = (countTotal + 2) / 3;
        return count;

    }
}
