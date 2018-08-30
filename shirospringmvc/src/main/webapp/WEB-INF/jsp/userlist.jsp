<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: ljh
  Date: 2018/8/11
  Time: 19:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>nice</title>
</head>
<body>
<h2>this is springmvc demo test</h2>

<c:forEach items="${users}" var="user">
    <c:out value="${user.username}" /><br/>
    <c:out value="${user.age}" /><br />
</c:forEach>

</body>
</html>
