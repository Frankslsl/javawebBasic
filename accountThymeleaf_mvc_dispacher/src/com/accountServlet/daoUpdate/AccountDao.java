package com.accountServlet.daoUpdate;


import com.accountServlet.bean.Account;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

/**
 *
 */
public interface AccountDao {
    /**
     * @param conn    -- connection
     * @param account -- an instance will be added into table
     * @Description -- add account into table
     */
    int insert(Connection conn, Account account);

    /**
     * @param conn -- connection
     * @param id   -- id
     * @Description -- delete an element from table according to the id
     */
    int deleteById(Connection conn, int id);

    /**
     * @param conn    -- connection
     * @param account -- the instance contains all the data you want to update in table
     * @Description -- update the data according to the account given
     */
    int update(Connection conn, Account account);

    /**
     * @param conn -- connection
     * @param id   -- id
     * @Description -- search and get a record and return a account instance
     */
    Account getCustomerById(Connection conn, int id);

    /**
     * @param conn
     * @return -- a list contains all the accounts instance got from table
     * @Description -- get all record from table and return a list
     */
    List<Account> getAll(Connection conn);

    /**
     * @param conn - connection
     * @return -- the total counts of the table
     * @Description get the count of a colum
     */

    Long getCount(Connection conn);

    Date getMaxBirthday(Connection conn);


    /**
     *
     * present limit pieces of data per page
     * SELECT * FROM TABLE LIMIT (PAGEnum-1)*5
     */
    List<Account> getAllbyPage(Connection conn,Integer pageNo, Integer limit);

    /**
     *
     * @param conn
     * @param keyword for search
     * @param pageNum for split the pages
     * @param limit max items per page
     * @return list
     */
    List<Account> getPagebyKeyword(Connection conn, String keyword,Integer pageNum, Integer limit);

    Long getCountWithKeyword(Connection conn, String keyword);

    Integer getPageCount(Connection conn, String keyword);
}
