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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 */
@WebServlet("/update.do")
public class UpdateServlet extends ViewBaseServlet{
    private AccountDaoImp dao = new AccountDaoImp();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection conn = null;
        String idStr = req.getParameter("id");
        String name = req.getParameter("name");
        String balanceStr = req.getParameter("balance");
        String birthdayStr = req.getParameter("birthday");
        if (Utile.strNotNull(idStr)&&Utile.strNotNull(name)&&Utile.strNotNull(balanceStr)&&Utile.strNotNull(birthdayStr)) {
            Integer id = Integer.parseInt(idStr);
            Double balance = Double.parseDouble(balanceStr);
            SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");
            Date birthday = null;
            try {
                birthday = sdf.parse(birthdayStr);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            Account account = new Account(id, name, balance, birthday);
            try{
                conn = JDBCutileWithPool.getConnection2();
                int update = dao.update(conn, account);
//            super.processTemplate("index",req,resp); 需要重新让客户端发送请求,好像index网页刷新
                resp.sendRedirect("search.do");
            }
            catch (SQLException e){
                e.printStackTrace();
            }finally {
                JDBCutileWithPool.close(conn);
            }
        }else {
            resp.sendRedirect("search.do");
        }

    }
}
