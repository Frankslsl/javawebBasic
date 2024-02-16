package com.controllers;

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
    private Map<String, Object> beanMap = new HashMap<>();
    public DispatcherServlet() throws ParserConfigurationException, IOException, SAXException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
//          以下步骤是为了解析xml配置文件
//          获得inputStream

        InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("applicationContext.xml");
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
//        获得document
        Document document = documentBuilder.parse(resourceAsStream);
//        获取所有的bean节点
        NodeList beanNodeList = document.getElementsByTagName("bean");
//        迭代循环这个bean节点集合
        for (int i = 0; i < beanNodeList.getLength(); i++) {
            Node beanNode = beanNodeList.item(i);
//            获取每一个bean的id和class名字,通过reflect创建一个实例,并用hashmap将bean的id和相对应的类的一个实例关联起来
            if (beanNode.getNodeType() == Node.ELEMENT_NODE){
                Element beanElement = (Element) beanNode;
                String beanId = beanElement.getAttribute("id");
                String className = beanElement.getAttribute("class");
                Object beanObj = Class.forName(className).getConstructor().newInstance();
                beanMap.put(beanId, beanObj);
            }
        }

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
//        用xml配置文件进行说明,通过上面那个构造器对xml进行解读
        Object controllerBeanObj = beanMap.get(path);
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
