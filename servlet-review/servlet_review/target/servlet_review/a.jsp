
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Basic JSP Template</title>
</head>
<body>

<h2>Welcome to JSP!</h2>

<%-- Java Scriptlet --%>
<%
    String welcomeMessage = "Hello, this is a simple JSP example.";
%>

<%-- JSP 表达式脚本 --%>
<p><%= welcomeMessage %>
</p>

<%-- JSP 声明脚本 --%>

<%!
    public String getCurrentTime() {
        return new java.util.Date().toString();
    }
%>
<%!
    public Integer getABC() {
        return 123;
    }
%>


<p>Current time: <%= getCurrentTime() %>
</p>
<p> username is : <%= request.getAttribute("username")%>></p>>

</body>
</html>
