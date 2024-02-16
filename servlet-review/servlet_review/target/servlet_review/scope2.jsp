
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Basic JSP Template</title>
</head>
<body>

<h2>Welcome to JSP Scope!</h2>

<%-- Java Scriptlet --%>

request是否有值:<%=request.getAttribute("key")%>><br/>
session是否有值:<%=session.getAttribute("key")%>><br/>
application是否有值:<%=application.getAttribute("key")%>><br/>
这是2

<%--静态包含,将页面的一部分作为单独整体封装起来--%>
<%@include file="/include/footer.jsp"%>>
<%--动态包含--%>
<jsp:include page="include/footer.jsp"></jsp:include>
<%--请求转发--%>
<jsp:forward page="scope2.jsp"></jsp:forward>





</body>
</html>
