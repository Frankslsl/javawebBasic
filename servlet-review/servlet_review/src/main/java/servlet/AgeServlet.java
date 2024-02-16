package servlet;

import POJO.User;
import POJO.UserDaoImp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 */
@WebServlet("/age")
public class AgeServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String  age = req.getParameter("age");
        int i = Integer.parseInt(age);
        UserDaoImp userDaoImp = new UserDaoImp();
        User user = userDaoImp.queryByAge(i);
        resp.setStatus(200);
        PrintWriter writer = resp.getWriter();
        writer.println(user.toString());
    }
}
