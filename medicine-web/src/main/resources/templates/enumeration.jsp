<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Title</title>
</head>
<style>
    li {
        list-style-type: none;
    }

    a {
        color: #000
    }
</style>
<body>
公共错误
<ul>
    <c:forEach items="${publicEnums}" var="item">
        <li>name:${item.name}, index:${item.index},message:${item.message}</li>
    </c:forEach>
</ul>

错误类型
<ul>
    <c:forEach items="${enums}" var="item">
        <li>name:${item.name}, index:${item.index},message:${item.message}</li>
    </c:forEach>
</ul>
</body>
</html>
