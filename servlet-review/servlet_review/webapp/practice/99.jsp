<%--
  Created by IntelliJ IDEA.
  User: sunlei
  Date: 12/30/2023
  Time: 9:50 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%
    for (int i = 1; i <= 9; i++) {
        for (int j = 1; j <= i; j++) {
%>
<%=
i + "x" + j + "=" + (i * j)
%>
<%}%>
<br>
<%}%>
</body>
</html>
