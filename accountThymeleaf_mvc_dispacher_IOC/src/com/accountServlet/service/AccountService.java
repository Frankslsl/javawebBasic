package com.accountServlet.service;

import com.accountServlet.bean.Account;

import java.util.List;

/**
 *
 */
public interface AccountService {
//    獲取指定頁面的庫存列表信息
    List<Account> getAccountList(String keyword, Integer pageNo, Integer limit);
//    根據ID查看指定庫存記録
    void insert(Account account);
//    根據id查看指定庫存記録
    Account getAccountById(Integer id);
//    刪除庫存指定記録
    void delAccount(Integer id);
//    獲取總頁數
    Integer getPageCount(String keyword);
//    更新
    void updateAccount(Account account);
}
