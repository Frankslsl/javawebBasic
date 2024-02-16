<%--
  Created by IntelliJ IDEA.
  User: sunlei
  Date: 1/1/2024
  Time: 9:46 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
${pageContext}
<%--    将request对象放到pageContext域中,来让后续代码简洁--%>
<%
pageContext.setAttribute("req", request);
%>

1. 协议:${req.scheme} <br>
2. 服务器ip: ${req.serverName}<br>
3. 服务器端口: ${req.serverPort}<br>
4. 获取工程路径: ${req.contextPath}<br>
5. 获取请求方法: ${req.method}<br>
6. 获取客户端ip地址: ${req.remoteHost}<br>
7. 获取会话的id编号:${req.requestedSessionId} <br>



</body>
</html>
