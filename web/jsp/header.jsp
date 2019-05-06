<%--
  Created by IntelliJ IDEA.
  User: Valentin
  Date: 18.02.2019
  Time: 21:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="adt" uri="customtags" %>

<fmt:setLocale value="${sessionScope.language}" scope="session"/>
<fmt:setBundle basename="i18n.message" var="locale"/>
<fmt:message bundle="${locale}" key="header.recent" var="recent"/>
<fmt:message bundle="${locale}" key="header.popular" var="popular"/>
<fmt:message bundle="${locale}" key="header.category" var="category"/>
<fmt:message bundle="${locale}" key="header.login" var="login"/>
<fmt:message bundle="${locale}" key="header.language" var="language"/>
<fmt:message bundle="${locale}" key="header.signup" var="sign_up"/>
<fmt:message bundle="${locale}" key="header.admin_panel" var="admin_panel"/>
<fmt:message bundle="${locale}" key="header.language.en" var="english"/>
<fmt:message bundle="${locale}" key="header.language.ru" var="russian"/>
<fmt:message bundle="${locale}" key="header.homepage" var="homepage"/>
<fmt:message bundle="${locale}" key="header.logout" var="logout"/>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <!-- Bootstrap core CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css"
          integrity="sha384-GJzZqFGwb1QTTN6wy59ffF1BuGJpLSa9DkKMp0DgiMDm4iYMj70gZWKYbI706tWS" crossorigin="anonymous">

    <!-- Place your stylesheet here-->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>

<body>


<nav class="navbar navbar-expand-md navbar-light bg-light fixed-top">
    <a class="navbar-brand" href="${pageContext.request.contextPath}/jsp/main.jsp">LifeHacker</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarsExampleDefault"
            aria-controls="navbarsExampleDefault" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarsExampleDefault">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <a class="nav-link"
                   href="${pageContext.request.contextPath}/controller?command=display_lifehacks_by_type&type=recent&current_page=${1}">
                    ${recent}<span class="sr-only">(current)</span></a>
            </li>
            <li class="nav-item">
                <a class="nav-link"
                   href="${pageContext.request.contextPath}/controller?command=display_lifehacks_by_type&type=popular&current_page=${1}">${popular}</a>
            </li>
            <li class="nav-item">
                <a class="nav-link"
                   href="${pageContext.request.contextPath}/controller?command=choose_lifehack_category">${category}</a>
            </li>
        </ul>

        <c:if test="${(sessionScope.role eq 'ADMIN') or (sessionScope.role eq 'USER')}">
            <div class="btn-group mr-4">
                <button type="button" class="btn btn-light dropdown-toggle" data-toggle="dropdown" aria-haspopup="true"
                        aria-expanded="false">
                        ${sessionScope.user.firstName}
                </button>
                <div class="dropdown-menu">
                    <a class="dropdown-item" href="${pageContext.request.contextPath}/jsp/user/user.jsp">${homepage}</a>
                    <a class="dropdown-item"
                       href="${pageContext.request.contextPath}/controller?command=log_out">${logout}</a>
                </div>
            </div>
        </c:if>

        <adt:admin role="${sessionScope.role}">
            <div class="mr-3">
                <a href="${pageContext.request.contextPath}/jsp/admin/admin.jsp"
                   class="btn btn-warning">${admin_panel}</a>
            </div>
        </adt:admin>

        <c:if test="${sessionScope.role eq 'GUEST'}">
            <div class="mr-3">
                <a href="${pageContext.request.contextPath}/jsp/guest/login.jsp" class="btn btn-primary">${login}</a>
            </div>

            <div class="mr-3">
                <a href="${pageContext.request.contextPath}/jsp/guest/sign_up.jsp"
                   class="btn btn-primary">${sign_up}</a>
            </div>
        </c:if>

        <div class="d-flex flex-row-reverse bd-highlight">
            <ul class="navbar-nav">
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="dropdown01" data-toggle="dropdown"
                       aria-haspopup="true" aria-expanded="false">${language}</a>
                    <div class="dropdown-menu" aria-labelledby="dropdown01">
                        <a class="dropdown-item"
                           href="${pageContext.request.contextPath}/controller?session_locale=en&command=locale">${english}</a>
                        <a class="dropdown-item"
                           href="${pageContext.request.contextPath}/controller?session_locale=ru&command=locale">${russian}</a>
                    </div>
                </li>
            </ul>
            <form action="${pageContext.request.contextPath}/controller" class="form-inline my-2 my-lg-0">
                <input type="hidden" name="command" value="search_lifehack"/>
                <input class="form-control mr-sm-2" name="searchCriteria" type="search" placeholder="Search"
                       aria-label="Search">
                <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
            </form>
        </div>

    </div>
</nav>

<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
        integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
        crossorigin="anonymous"></script>

<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js"
        integrity="sha384-wHAiFfRlMFy6i5SRaxvfOCifBUQy1xHdJ/yoi7FRNXMRBu5WHdZYu1hA6ZOblgut"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js"
        integrity="sha384-B0UglyR+jN6CkvvICOB2joaf5I4l3gm9GU6Hc1og6Ls7i6U/mkkaduKaBhlAXv9k"
        crossorigin="anonymous"></script>
</body>
</html>
