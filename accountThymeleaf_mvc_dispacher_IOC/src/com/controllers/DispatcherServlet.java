package com.controllers;

import com.accountServlet.io.BeanFactory;
import com.accountServlet.io.ClassPathXmlApplicationContext;
import com.accountServlet.util.Utile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
@WebServlet("*.do")
public class DispatcherServlet extends ViewBaseServlet{
    private BeanFactory beanFactory;
    public DispatcherServlet(){
    }
    public void init() throws ServletException {
        super.init();
        beanFactory = new ClassPathXmlApplicationContext();
    }
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
//        假设URL 是 http://localhost:8080/hello.do
//        servletPath 是 /hello.do
//        1. /hello.do - > hello
        String servletPath = req.getServletPath();
        int i = servletPath.lastIndexOf(".do");
        String path = servletPath.substring(1, i);
//        通過beanFactory獲取
        Object controllerBeanObj = beanFactory.getBean(path);
        String operate = req.getParameter("operate");
        if (Utile.strIsNull(operate)) {
            operate = "index";
        }

//        using reflect to call the function
//        可以一步就获取想要的方法,不通过迭代和if判断
//        try {
//            Method method = controllerBeanObj.getClass().getDeclaredMethod(operate, HttpServletRequest.class,HttpServletResponse.class);
//            if(method != null){
//                method.setAccessible(true);
//                method.invoke(controllerBeanObj,req,resp);
//
//            }else {
//                throw new RuntimeException("the value of operate is invalid");
//            }
//        } catch (NoSuchMethodException e) {
//            throw new RuntimeException(e);
//        } catch (InvocationTargetException e) {
//            throw new RuntimeException(e);
//        } catch (IllegalAccessException e) {
//            throw new RuntimeException(e);
//        }


//        使用for循环if语句判断要执行的方法,也就是和operate的值相同的方法
        Method[] methods = controllerBeanObj.getClass().getDeclaredMethods();
        for (Method m:methods
        ) {
            if (operate.equals(m.getName())){
                try {
                    m.setAccessible(true);
//                    执行方法
                    Object returnObj = m.invoke(controllerBeanObj, req);
//                    视图处理
                    String methodReturn = (String) returnObj;
                    if (methodReturn.startsWith("redirect:")){
                        String redirectStr = methodReturn.substring("redirect:".length());
                        resp.sendRedirect(redirectStr);
                    }else {
                        super.processTemplate(methodReturn,req,resp);
                    }
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
}
