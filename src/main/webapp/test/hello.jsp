<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Hello</title>
</head>
<body>
<p>
    <h4>Tyt nado write Hello</h4>
    <c:out value="${requestScope.message}"> message </c:out>
</p>
</body>
</html>
