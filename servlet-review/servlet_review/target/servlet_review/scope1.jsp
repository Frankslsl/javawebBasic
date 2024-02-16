
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Basic JSP Template</title>
</head>
<body>

<h2>Welcome to JSP Scope!</h2>

<%-- Java Scriptlet --%>
<%
    request.setAttribute("key", "request");
    session.setAttribute("key", "session");
    application.setAttribute("key", "session");
%>
request是否有值:<%=request.getAttribute("key")%>>
session是否有值:<%=session.getAttribute("key")%>>
application是否有值:<%=application.getAttribute("key")%>>
<%
request.getRequestDispatcher("/scope2.jsp").forward(request,response);
%>


</body>
</html>
