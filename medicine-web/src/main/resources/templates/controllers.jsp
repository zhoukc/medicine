<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>controllers</title>
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
<ul>
    <c:forEach items="${list}" var="item">
        <c:choose>
            <c:when test="${item.description!=null}">
                <li>注释:${item.description}</li>
            </c:when>
        </c:choose>
        <a href="/help/action?controller=${item.name}">
            <li>名称:${item.simpleName}，路径:${item.path}</li>
        </a>
    </c:forEach>
</ul>
</body>
</html>
