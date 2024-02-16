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
@WebServlet("/search.do")
public class PageSearchServlet extends ViewBaseServlet{
    private AccountDaoImp dao = new AccountDaoImp();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        setting the encoding method, avoid the　garbled characters
        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();
        String keyword = null;
        Integer pageNum =1;
        String oper = req.getParameter("oper");
        if (Utile.strNotNull(oper) && "search".equals(oper)){
//            get into this servlet by clicking the search button
//            so, for this situation, pageNum = 1, keyword should get from input
            pageNum = 1;
            keyword = req.getParameter("keyword");
            if (Utile.strIsNull(keyword)){
                keyword = "";
            }
            session.setAttribute("keyword",keyword);

        }else {
//            for this situation, client gets into this servlet by clicking page button, or by type in the address
//            keyword should get from session
            Object keywordObj = session.getAttribute("keyword");
            if (keywordObj!=null){
                keyword = (String)keywordObj;
            }else {
                keyword = "";
            }
        }
        String pageNumStr = req.getParameter("pageNum");

        if (Utile.strNotNull(pageNumStr)){
            pageNum = Integer.parseInt(pageNumStr);

        }

        session.setAttribute("pageNum",pageNum);
        Connection conn = null;
        try{
            conn = JDBCutileWithPool.getConnection2();
            List<Account> list = dao.getPagebyKeyword(conn, keyword,(pageNum-1)*3, 3);
            Integer count =  (dao.getCountWithKeyword(conn,keyword)).intValue();
            Integer pageTotal = (count +2)/3;
            session.setAttribute("pageTotal", pageTotal);
            session.setAttribute("list",list);
            super.processTemplate("indexWithSearch",req,resp);

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            JDBCutileWithPool.close(conn);
        }
    }
}

