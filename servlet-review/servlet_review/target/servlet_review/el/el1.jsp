<%@ page import="POJO.User" %><%--
  Created by IntelliJ IDEA.
  User: sunlei
  Date: 12/31/2023
  Time: 7:14 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%
    pageContext.setAttribute("key", "pageContext");
    request.setAttribute("key", "request");
    session.setAttribute("key", "session");
    application.setAttribute("key", "application");
    User user = new User();
    user.setAge(12);
    user.setUserId("123");
    user.setFirstName("Lei");
    user.setLastName("Sun");
    pageContext.setAttribute("user",user);

%>

${key} <br>
${user.firstName}

${12 == 12} or ${12 eq 12} <br>
${12 >= 12} or ${12 ge 12} <br>
${12 <= 12} or ${12 le 12} <br>

</body>
</html>
