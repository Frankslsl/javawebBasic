<%@ page import="POJO.User" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.Map" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: sunlei
  Date: 1/2/2024
  Time: 4:30 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<html>
<head>
    <title>Title</title>
</head>
<body>
<%--set标签
    可以往域对象中保存数据
    scope表示域对象
    默认是page

--%>
保存前${pageScope.key}<br>
<c:set scope="page" value="123" var="key"></c:set>
保存后${pageScope.key}<br>

<%--if标签
    test属性表示判断的条件(使用EL表达式输出)
    没有if-else的写法
    --%>
<c:if test="${12==12}">
    <h1>
        12 == 12
    </h1>
</c:if>

<%--choose, when, otherwise标签
    就是等同于java的switch,case,defaut
--%>
<c:choose>
    <c:when test="">
    </c:when>
    <c:otherwise>

    </c:otherwise>
</c:choose>
<%--foreach 标签
    begin设置开始的索引
    end设置结束的索引
    var表示循环变量


    for(int i=1; i<10;i++)

--%>
<c:forEach begin="1" end="10" var="i">
    <h2>这是第${i}行</h2>
</c:forEach>
<hr>
<%--使用foreach标签遍历一个object数组
    items 表示遍历的集合
    var 表示遍历到的数据
    begin 表示遍历的开始索引
    end 表示遍历的结束索引
    setp 表示遍历的步长
    varStatus 表示当前便利道的数据的状态
    --%>
<%
    ArrayList<User> users = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
        User user = new User();
        user.setUserId(i + "");
        users.add(user);
    }
    request.setAttribute("users",users);

%>

<c:forEach items="${requestScope.users}" var="user">
    the user id is : ${user.userId}<br>
</c:forEach>
<%
    HashMap<String, User> userMap = new HashMap<>();
    for (int i = 0; i < 10; i++) {
        User user = new User();
        user.setUserId(i + "");
        userMap.put(user.userId, user);
    }

    request.setAttribute("users",users);

%>
</body>
</html>
