<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>parameter</title>
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
    <c:forEach items="${enums}" var="item">
        <li>name:${item.name}, index:${item.index},message:${item.message}</li>
    </c:forEach>
</ul>

<c:forEach items="${list}" var="item">
    <ul>
        <c:choose>
            <c:when test="${item.description!=null}">
                <li>注释:${item.description}</li>
            </c:when>
        </c:choose>
        <li>参数名:${item.name}</li>
        <c:choose>
            <c:when test="${item.enumeration}">
                <li><a href="/help/enumeration?type=${item.type}">类型名:${item.typeName}</a></li>
            </c:when>
            <c:otherwise>
                <c:choose>
                    <c:when test="${item.type==null}">
                        <li>类型名:${item.typeName}
                        <c:choose>
                            <c:when test="${item.genericType!=null}">
                                <li><a href="/help/parameter?type=${item.genericType}">泛型名:${item.typeName}</a>
                                </li>
                            </c:when>
                        </c:choose>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li>
                            <a href="/help/parameter?type=${item.type}&genericType=${item.genericType}">类型名:${item.typeName}</a>
                        </li>
                    </c:otherwise>
                </c:choose>
            </c:otherwise>
        </c:choose>
    </ul>
</c:forEach>
</body>
</html>
