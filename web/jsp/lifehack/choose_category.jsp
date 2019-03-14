<%--
  Created by IntelliJ IDEA.
  User: Valentin
  Date: 23.02.2019
  Time: 19:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <c:import url="../header.jsp"/>
    <title>Choose Category</title>
</head>
<body>
<div class="bg-dark">
    <h1 align="center" class="text-white" style="padding-top: 150px; padding-bottom: 80px;">Категории</h1>
</div>

<div class="cards-body" style="padding-top: 30px">
    <main role="main" class="container mt-4" id="category-img">
        <div class="d-flex justify-content-center">
            <ul class=" list-group mb-5 " style="width: 35%" id="category-list">
                <c:forEach items="${categoryMap}" var="category" varStatus="status">
                    <li class="list-group-item mb-2 d-flex justify-content-between align-items-center"
                        id="custom-list-item">
                        <a class="nav-link text-dark"
                           href="${pageContext.request.contextPath}/controller?command=display_lifehacks_by_type&type=category&category=${category.key}&current_page=${1}">${category.key}</a>
                        <span class="badge badge-dark badge-pill">${category.value}</span>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </main>
    <div class="cards-body" style="padding-top: 30px">
        <c:import url="../footer.jsp"/>
</body>
</html>
