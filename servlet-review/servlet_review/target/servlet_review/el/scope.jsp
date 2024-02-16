<%--
  Created by IntelliJ IDEA.
  User: sunlei
  Date: 1/1/2024
  Time: 9:43 PM
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
    application.setAttribute("key", "application");
    request.setAttribute("key", "request");
    session.setAttribute("key", "session");

%>
<h1>

${pageScope.key}
</h1>
</body>
</html>
