package com.accountServlet.listener;

import com.accountServlet.roc.BeanFactory;
import com.accountServlet.roc.ClassPathXmlApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


/**
 * 一个监听器,监听servletContext,在上下文启动的时候去创建IOC容器,然后将其保存到application作用域,中央控制器再从application作用域调取
 */
public class BeanFactoryListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("bean factory has benn created");
//        获取servletContext
        ServletContext servletContext = sce.getServletContext();
//          获取上下文初始化参数
        String contextConfigLocation = servletContext.getInitParameter("contextConfigLocation");
//        创建IOC容器
        BeanFactory beanFactory = new ClassPathXmlApplicationContext(contextConfigLocation);
//        将IOC容器存入application作用域中,让中央控制器调用
        servletContext.setAttribute("bean",beanFactory);

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("servlet context has been destroyed");
        }
}
