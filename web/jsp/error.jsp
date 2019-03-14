<%--
  Created by IntelliJ IDEA.
  User: Valentin
  Date: 24.02.2019
  Time: 11:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <c:import url="header.jsp"/>
    <title>Error jsp</title>
</head>
<body>
<div class="container" style="margin-top: 80px;">
    <a href="${pageContext.request.contextPath}/jsp/main.jsp" class="btn btn-warning">Main page</a>
    ${sessionScope.message}
</div>
<ul>
    <li>Stack trace:
        <pre>${pageContext.out.flush()}${exception.printStackTrace(pageContext.response.writer)}</pre>
    </li>
</ul>
<c:import url="footer.jsp"/>
</body>
</html>
