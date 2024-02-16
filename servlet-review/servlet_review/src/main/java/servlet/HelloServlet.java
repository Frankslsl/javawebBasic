package servlet;

import javax.servlet.*;
import java.io.IOException;

/**
 *
 */
public class HelloServlet implements Servlet {
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        System.out.println("2. init初始化方法");
        //可以获取servlet的别名servlet-name
        System.out.println("servlet的别名是"+servletConfig.getServletName());
        //可以获取初始化参数init-param
        System.out.println("初始化参数是" + servletConfig.getInitParameter("username"));
        //可以获得servletContext对象
        ServletContext servletContext = servletConfig.getServletContext();
        String contextPath = servletContext.getContextPath();
        System.out.println(contextPath);
        String haha =(String) servletContext.getAttribute("haha");
        System.out.println(haha);
    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }


    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        System.out.println("Hello servlet");

    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {

    }
}
