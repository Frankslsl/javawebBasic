package com.ssm;

import com.accountServlet.bean.Account;
import com.accountServlet.daoUpdate.AccountDaoImp;
import com.accountServlet.util.JDBCutileWithPool;
import com.accountServlet.util.Utile;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * 只有分页功能
 * 没有查询功能
 */
@WebServlet("/pageServlet")
public class PageServlet extends ViewBaseServlet{
    private AccountDaoImp dao = new AccountDaoImp();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer pageNum =1;
        String pageNumStr = req.getParameter("pageNum");
        if (Utile.strNotNull(pageNumStr)){
            Integer pageNumTemp = Integer.parseInt(pageNumStr);
            if (pageNumTemp>pageNum){
                pageNum = pageNumTemp;
            }
        }
        HttpSession session = req.getSession();
        session.setAttribute("pageNum",pageNum);
        Connection conn = null;
        try{
            conn = JDBCutileWithPool.getConnection2();
            List<Account> list = dao.getAllbyPage(conn, (pageNum-1)*3, 3);
            Integer count =  (dao.getCount(conn)).intValue();
            Integer pageTotal = (count +2)/3;
            session.setAttribute("pageTotal", pageTotal);
            session.setAttribute("list",list);
            super.processTemplate("index",req,resp);

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            JDBCutileWithPool.close(conn);
        }
    }
}
