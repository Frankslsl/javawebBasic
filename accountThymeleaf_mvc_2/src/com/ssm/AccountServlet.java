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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 *
 */
@WebServlet("/account.do")
public class AccountServlet extends ViewBaseServlet {
    private AccountDaoImp dao = new AccountDaoImp();
    Connection conn = null;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String operate = req.getParameter("operate");
        if (Utile.strIsNull(operate)) {
            operate = "index";
        }

//        switch (operate) {
//            case "index":
//                index(req, resp);
//                break;
//            case "add":
//                add(req,resp);
//                break;
//            case "del":
//                del(req,resp);
//                break;
//            case "edit":
//                edit(req,resp);
//                break;
//            case "update":
//                update(req,resp);
//                break;
//            default:
//                index(req,resp);
//        }

//        using reflect to call the function
        Method[] methods = this.getClass().getDeclaredMethods();
        for (Method m:methods
             ) {
            if (operate.equals(m.getName())){
                try {
                    m.invoke(this,req, resp);
                    return;
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                } catch (InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }

        }
        throw new RuntimeException("the value of operate is invalid");

    }

    private void index(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        setting the encoding method, avoid the　garbled characters
        HttpSession session = req.getSession();
        String keyword = null;
        Integer pageNum = 1;
        String oper = req.getParameter("oper");
        if (Utile.strNotNull(oper) && "search".equals(oper)) {
//            get into this servlet by clicking the search button
//            so, for this situation, pageNum = 1, keyword should get from input
            pageNum = 1;
            keyword = req.getParameter("keyword");
            if (Utile.strIsNull(keyword)) {
                keyword = "";
            }
            session.setAttribute("keyword", keyword);

        } else {
//            for this situation, client gets into this servlet by clicking page button, or by type in the address
//            keyword should get from session
            Object keywordObj = session.getAttribute("keyword");
            if (keywordObj != null) {
                keyword = (String) keywordObj;
            } else {
                keyword = "";
            }
        }
        String pageNumStr = req.getParameter("pageNum");

        if (Utile.strNotNull(pageNumStr)) {
            pageNum = Integer.parseInt(pageNumStr);

        }

        session.setAttribute("pageNum", pageNum);
        conn = null;
        try {
            conn = JDBCutileWithPool.getConnection2();
            List<Account> list = dao.getPagebyKeyword(conn, keyword, (pageNum - 1) * 3, 3);
            Integer count = (dao.getCountWithKeyword(conn, keyword)).intValue();
            Integer pageTotal = (count + 2) / 3;
            session.setAttribute("pageTotal", pageTotal);
            session.setAttribute("list", list);
            super.processTemplate("indexWithSearch", req, resp);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCutileWithPool.close(conn);
        }
    }
    private void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
                resp.sendRedirect("account.do");
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
                    + "  window.location.href = 'account.do';"
                    + "}"
                    + "</script>";
            resp.getWriter().write(alertScript);
        }
    }
    private void del(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
                resp.sendRedirect("account.do");
            }
        }
    }
    private void edit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
    private void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
                resp.sendRedirect("account.do");
            }
            catch (SQLException e){
                e.printStackTrace();
            }finally {
                JDBCutileWithPool.close(conn);
            }
        }else {
            resp.sendRedirect("account.do");
        }

    }
}
