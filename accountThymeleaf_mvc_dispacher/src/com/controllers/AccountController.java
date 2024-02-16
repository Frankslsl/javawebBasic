package com.controllers;

import com.accountServlet.bean.Account;
import com.accountServlet.service.AccountService;
import com.accountServlet.service.AccountServiceImp;
import com.accountServlet.util.Utile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 *
 */

public class AccountController extends ViewBaseServlet {

    private AccountService accountService = new AccountServiceImp();

    public AccountController() {
    }

    private String index(HttpServletRequest req) {
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
        List<Account> list = accountService.getAccountList(keyword, (pageNum - 1) * 3, 3);
        Integer pageTotal = accountService.getPageCount(keyword);
        session.setAttribute("pageTotal", pageTotal);
        session.setAttribute("list", list);
        return "indexWithSearch";
//            super.processTemplate("indexWithSearch", req, resp);
    }

    private String add(HttpServletRequest req) {
        String name = req.getParameter("name");
        String balanceStr = req.getParameter("balance");
        String birthdayStr = req.getParameter("birthday");
        if (Utile.strNotNull(name) && Utile.strNotNull(balanceStr) && Utile.strNotNull(birthdayStr)) {
            Double balance = Double.parseDouble(balanceStr);
            Date birthday = null;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                birthday = sdf.parse(birthdayStr);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            Account account = new Account(1, name, balance, birthday);
            accountService.insert(account);
//                resp.sendRedirect("account.do");
            return "redirect:account.do";
        } else {
            req.setAttribute("isNull", "yes");
            return "add";
        }
//        else {
////            利用js的script语句中的confirm来确认下一步去哪.不能简单的返回本页,因为那样会默认doget方法,但是doget方法并没有重写,所以会405
//            String alertScript = "<script>"
//                    + "if (confirm('null')) {"
//                    + "  window.location.href = 'add.html';"
//                    + "} else {"
//                    + "  window.location.href = 'account.do';"
//                    + "}"
//                    + "</script>";
//            resp.getWriter().write(alertScript);
//            return null;
//        }
    }

    private String del(HttpServletRequest req) {
        String idStr = req.getParameter("id");
        if (Utile.strNotNull(idStr)) {
            Integer id = Integer.parseInt(idStr);
            accountService.delAccount(id);
//                resp.sendRedirect("account.do");
            return "redirect:account.do";
        }
        return null;
    }

    private String edit(HttpServletRequest req) {
        String idStr = req.getParameter("id");
        if (Utile.strNotNull(idStr)) {
            int id = Integer.parseInt(idStr);
            Account account = accountService.getAccountById(id);
            System.out.println(account);
            req.setAttribute("account", account);
//                super.processTemplate("edit", req, resp);
            return "edit";
        }
        return null;
    }

    private String update(HttpServletRequest req) {
        String idStr = req.getParameter("id");
        String name = req.getParameter("name");
        String balanceStr = req.getParameter("balance");
        String birthdayStr = req.getParameter("birthday");
        Integer id = null;
        Account account_pre = null;
        if (Utile.strNotNull(idStr)) {
            id = Integer.parseInt(idStr);
            account_pre = accountService.getAccountById(id);
        }
        if (Utile.strNotNull(name) && Utile.strNotNull(balanceStr) && Utile.strNotNull(birthdayStr)) {

            Double balance = Double.parseDouble(balanceStr);
            SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");
            Date birthday = null;
            try {
                birthday = sdf.parse(birthdayStr);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            Account account = new Account(id, name, balance, birthday);
            accountService.updateAccount(account);
//            super.processTemplate("index",req,resp); 需要重新让客户端发送请求,好像index网页刷新
            return "redirect:account.do";
        } else {
            req.setAttribute("account", account_pre);
            req.setAttribute("operate", "edit");
            req.setAttribute("isNull", true);
            return "edit";
        }
    }
}
