package com.ssm;

import com.accountServlet.bean.Account;
import com.accountServlet.daoUpdate.AccountDaoImp;
import com.accountServlet.util.JDBCutileWithPool;
import com.accountServlet.util.Utile;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 */
@WebServlet("/edit.do")
public class EditServlet extends ViewBaseServlet{
    private AccountDaoImp dao = new AccountDaoImp();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection conn = null;
        try{
            conn = JDBCutileWithPool.getConnection2();
            String idStr = req.getParameter("id");
            if (Utile.strNotNull(idStr)){
                int id = Integer.parseInt(idStr);
                Account account = dao.getCustomerById(conn,id);
                System.out.println(account);
                req.setAttribute("account",account);
                super.processTemplate("edit",req,resp);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            JDBCutileWithPool.close(conn);
        }


    }
}
