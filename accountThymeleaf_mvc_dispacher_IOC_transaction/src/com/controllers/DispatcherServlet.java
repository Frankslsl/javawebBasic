package com.controllers;

import com.accountServlet.roc.BeanFactory;
import com.accountServlet.roc.ClassPathXmlApplicationContext;
import com.accountServlet.util.Utile;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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
        //因为继承了ViewBaseServlet,又继承了HttpServlet,所以可以直接调用父类的getServletContect来获取
        ServletContext servletContext = getServletContext();
        Object beanFactoryObj = servletContext.getAttribute("bean");
        beanFactory = null;
        if (beanFactoryObj != null) {
            beanFactory = (BeanFactory) beanFactoryObj;
        }else {
            throw new RuntimeException("can not get the beanMap");
        }
    }
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
