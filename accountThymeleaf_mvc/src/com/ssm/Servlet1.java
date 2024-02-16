package com.ssm;

import com.accountServlet.bean.Account;
import com.accountServlet.daoUpdate.AccountDaoImp;
import com.accountServlet.util.JDBCutileWithPool;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *最基础的index
 * 没有分页,查询功能
 */
@WebServlet("/servlet1")
public class Servlet1 extends ViewBaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        System.out.println(session.getId());
        Connection conn = null;
        AccountDaoImp dao = new AccountDaoImp();
        try{
            conn = JDBCutileWithPool.getConnection2();
            List<Account> list = dao.getAll(conn);
            list.stream().forEach(System.out::println);
            session.setAttribute("list",list);
            super.processTemplate("index",req,resp);

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            JDBCutileWithPool.close(conn);
        }
    }
}
