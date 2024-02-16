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
import java.text.spi.DateFormatSymbolsProvider;
import java.util.Date;

/**
 * 添加新account进入db
 */
@WebServlet("/add.do")
public class AddServlet extends ViewBaseServlet {
    private AccountDaoImp dao = new AccountDaoImp();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection conn = null;
        String name = req.getParameter("name");
        String balanceStr = req.getParameter("balance");
        String birthdayStr = req.getParameter("birthday");
        if (Utile.strNotNull(name) && Utile.strNotNull(balanceStr) && Utile.strNotNull(birthdayStr)){
            Double balance = Double.parseDouble(balanceStr);
            Date birthday = null;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                birthday = sdf.parse(birthdayStr);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            Account account = new Account(1, name, balance, birthday);
            try{
                conn = JDBCutileWithPool.getConnection2();
                int insert = dao.insert(conn, account);
                resp.sendRedirect("search.do");
            }catch (SQLException e){
                e.printStackTrace();
            }finally {
                JDBCutileWithPool.close(conn);

            }
        }else {
//            利用js的script语句中的confirm来确认下一步去哪.不能简单的返回本页,因为那样会默认doget方法,但是doget方法并没有重写,所以会405
            String alertScript = "<script>"
                    + "if (confirm('null')) {"
                    + "  window.location.href = 'add.html';"
                    + "} else {"
                    + "  window.location.href = 'search.do';"
                    + "}"
                    + "</script>";
            resp.getWriter().write(alertScript);
        }
    }
}
