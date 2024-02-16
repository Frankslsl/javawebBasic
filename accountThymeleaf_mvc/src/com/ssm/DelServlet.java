package com.ssm;

import com.accountServlet.daoUpdate.AccountDaoImp;
import com.accountServlet.util.JDBCutileWithPool;
import com.accountServlet.util.Utile;
import com.mysql.cj.jdbc.JdbcConnection;
import com.ssm.ViewBaseServlet;

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
@WebServlet("/del.do")
public class DelServlet extends ViewBaseServlet {
    private AccountDaoImp dao = new AccountDaoImp();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection conn = null;
        String idStr = req.getParameter("id");
        if(Utile.strNotNull(idStr)){
            Integer id = Integer.parseInt(idStr);
            try{
                conn = JDBCutileWithPool.getConnection2();
                int i = dao.deleteById(conn, id);
            }catch (SQLException e){
                e.printStackTrace();
            }finally {
                JDBCutileWithPool.close(conn);
                resp.sendRedirect("search.do");
            }
        }
    }
}
