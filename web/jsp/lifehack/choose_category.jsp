<%--
  Created by IntelliJ IDEA.
  User: Valentin
  Date: 23.02.2019
  Time: 19:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.language}" scope="session"/>
<fmt:setBundle basename="i18n.message" var="locale"/>
<fmt:message bundle="${locale}" key="header.category" var="category"/>
<fmt:message bundle="${locale}" key="category.cinema" var="cinema"/>
<fmt:message bundle="${locale}" key="category.education" var="education"/>
<fmt:message bundle="${locale}" key="category.food" var="food"/>
<fmt:message bundle="${locale}" key="category.life" var="life"/>
<fmt:message bundle="${locale}" key="category.motivation" var="motivation"/>
<fmt:message bundle="${locale}" key="category.news" var="news"/>
<fmt:message bundle="${locale}" key="category.sport" var="sport"/>
<fmt:message bundle="${locale}" key="category.technologies" var="technologies"/>

<html>
<head>
    <c:import url="../header.jsp"/>
    <title>${category}</title>
</head>
<body>
<div class="bg-dark">
    <h1 align="center" class="text-white" style="padding-top: 150px; padding-bottom: 80px;">${category}</h1>
</div>

<div class="cards-body" style="padding-top: 30px">
    <main role="main" class="container mt-4" id="category-img">
        <div class="d-flex justify-content-center">
            <ul class=" list-group mb-5 " style="width: 35%" id="category-list">
                <c:forEach items="${categoryMap}" var="category" varStatus="status">
                    <li class="list-group-item mb-2 d-flex justify-content-between align-items-center"
                        id="custom-list-item">
                        <a class="nav-link text-dark"
                           href="${pageContext.request.contextPath}/controller?command=display_lifehacks_by_type&type=category&category=${category.key}&current_page=${1}">
                            <c:choose>
                                <c:when test="${category.key eq 'SPORT'}">
                                    ${sport}
                                </c:when>
                                <c:when test="${category.key eq 'LIFE'}">
                                    ${life}
                                </c:when>
                                <c:when test="${category.key eq 'MOTIVATION'}">
                                    ${motivation}
                                </c:when>
                                <c:when test="${category.key eq 'TECHNOLOGIES'}">
                                    ${technologies}
                                </c:when>
                                <c:when test="${category.key eq 'CINEMA'}">
                                    ${cinema}
                                </c:when>
                                <c:when test="${category.key eq 'NEWS'}">
                                    ${news}
                                </c:when>
                                <c:when test="${category.key eq 'EDUCATION'}">
                                    ${education}
                                </c:when>
                                <c:otherwise>
                                    ${food}
                                </c:otherwise>
                            </c:choose>
                        </a>
                        <span class="badge badge-dark badge-pill">${category.value}</span>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </main>
</div>
<c:import url="../footer.jsp"/>
</body>
</html>
