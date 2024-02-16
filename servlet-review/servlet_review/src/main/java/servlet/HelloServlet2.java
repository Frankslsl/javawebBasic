package servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 */
@WebServlet("/hello2")
public class HelloServlet2 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletConfig servletConfig = getServletConfig();
        ServletContext servletContext = servletConfig.getServletContext();
        //获取context-params的初始化参数,所有servlet公用一个servletContext
        String context = servletContext.getInitParameter("context");
        System.out.println("context params the key is context, the value is " + context);

        //获取当前工程路径, 格式:/工程路径 ===> 也就是web文件夹
        System.out.println("当前工程路径" + servletContext.getContextPath());
        //获取工程部署后在服务器硬盘上的绝对路径
        //  /  斜杠被服务器解析为:http://ip:port/工程名 ==> 也就是映射到web文件夹
        System.out.println("绝对路径是" + servletContext.getRealPath("/"));
        servletContext.setAttribute("haha", "haha");

        System.out.println("URI ===> " + req.getRequestURI());
        System.out.println("URL ===> " + req.getRequestURL());
        System.out.println("ip ===> " + req.getRemoteHost());
        System.out.println("header ===> " + req.getHeader("User-Agent"));




    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String username = req.getParameter("username");
        System.out.println("username is " + username);


    }
}
