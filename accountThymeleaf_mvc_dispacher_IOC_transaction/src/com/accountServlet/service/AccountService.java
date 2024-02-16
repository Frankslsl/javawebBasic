package com.accountServlet.service;

import com.accountServlet.bean.Account;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

/**
 *
 */
public interface AccountService {
//    獲取指定頁面的庫存列表信息
    List<Account> getAccountList(String keyword, Integer pageNo, Integer limit) throws SQLException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException;
//    根據ID查看指定庫存記録
    void insert(Account account) throws SQLException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;
//    根據id查看指定庫存記録
    Account getAccountById(Integer id) throws SQLException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;
//    刪除庫存指定記録
    void delAccount(Integer id) throws SQLException;
//    獲取總頁數
    Integer getPageCount(String keyword) throws SQLException;
//    更新
    void updateAccount(Account account) throws SQLException;
}
