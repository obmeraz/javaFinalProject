<%--
  Created by IntelliJ IDEA.
  User: Valentin
  Date: 23.02.2019
  Time: 21:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>Лайфхакер</title>
</head>
<body>
<c:import url="../header.jsp"/>

<div class="bg-dark">
    <h1 align="center" class="text-white" style="padding-top: 150px; padding-bottom: 80px;">
        <c:choose>
            <c:when test="${requestScope.type eq 'recent'}">
                Свежие
            </c:when>

            <c:when test="${requestScope.type eq 'popular'}">
                Популярные
            </c:when>

            <c:when test="${not empty category}">
                ${category}
            </c:when>

            <c:otherwise>
                ${sessionScope.user.firstName} любимые лайфхаки
            </c:otherwise>
        </c:choose>
    </h1>
</div>
<div class="cards-body" style="padding-top: 30px">
    <main role="main" class="container">
        <c:forEach items="${lifehacks}" var="lifehack" varStatus="status">
            <div class="cards">
                <div class="card mb-4 ">
                    <div class="row no-gutters">
                        <div class="col-md-4">
                            <img src="data:image/jpg;base64,${lifehack.image}" class="card-img" alt="...">
                        </div>
                        <div class="col-md-8">
                            <div class="card-body">
                                <h5 class="card-title"><a class="nav-link text-dark text-truncate"
                                                          href="${pageContext.request.contextPath}/controller?command=display_lifehack_post&lifehack_id=${lifehack.lifehackId}">
                                        ${lifehack.name}</a></h5>
                                <p class="card-text">${lifehack.excerpt}</p>
                                <div class="d-flex justify-content-around">
                                    <p class="card-text">
                                        <small class="text-muted">
                                            <jsp:useBean id="dateValue" class="java.util.Date"/>
                                            <jsp:setProperty name="dateValue" property="time"
                                                             value="${lifehack.publicationDate}"/>
                                            <fmt:formatDate value="${dateValue}" pattern="MM/dd/yyyy HH:mm:ss"/>
                                            <img src="${pageContext.request.contextPath}/img/heart.png"
                                                 class="ml-5" alt="..."
                                                 style="width: 15px; height: 15px">
                                            ${lifehack.likesAmount}
                                            <img src="${pageContext.request.contextPath}/img/comment-icon.svg"
                                                 class="ml-5" alt="..."
                                                 style="width: 15px; height: 15px">
                                            ${commentMap.get(lifehack)}
                                            <span class="badge badge-success ml-5">
                                                    <a class="text-light"
                                                       href="${pageContext.request.contextPath}/controller?command=display_lifehacks_by_type&type=category&category=${lifehack.category}&current_page=${1}"
                                                       style="text-decoration:none">
                                                            ${lifehack.category}</a></span>
                                        </small>
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
    </main>
</div>
<c:import url="../pagination.jsp"/>
<c:import url="../footer.jsp"/>
</body>
</html>

