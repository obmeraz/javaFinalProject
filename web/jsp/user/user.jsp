<%--
  Created by IntelliJ IDEA.
  User: Valentin
  Date: 02.03.2019
  Time: 19:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.language}" scope="session"/>
<fmt:setBundle basename="i18n.message" var="locale"/>
<fmt:message bundle="${locale}" key="user.email" var="email"/>
<fmt:message bundle="${locale}" key="user.page" var="page"/>
<fmt:message bundle="${locale}" key="user.username" var="username"/>
<fmt:message bundle="${locale}" key="user.edit.email" var="edit_email"/>
<fmt:message bundle="${locale}" key="user.edit.password" var="edit_password"/>
<fmt:message bundle="${locale}" key="user.likes" var="likes"/>

<html>
<head>
    <title>${page}</title>
</head>
<body>
<c:import url="../header.jsp"/>
<div class="cards-body" style="padding-top: 30px">
    <main role="main" class="container" style="width: 550px;">
        <div class="d-flex justify-content-center mb-5" style="margin-top: 100px;">
            <div class="card text-center">
                <div class="card-body">
                    <h5 class="card-title">${sessionScope.user.firstName} ${sessionScope.user.lastName}</h5>
                    <p>${username}:</p>
                    <p class="user-card-text">${sessionScope.user.nickName}</p>
                    <p>${email}:</p>
                    <p class="user-card-text">${sessionScope.user.email}</p>
                    <p class="user-card-text"><a
                            href="${pageContext.request.contextPath}/jsp/user/edit_user.jsp?edit=password"
                            class="btn btn-danger" style="min-width: 124px">${edit_password}</a></p>
                    <p class="user-card-text"><a
                            href="${pageContext.request.contextPath}/jsp/user/edit_user.jsp?edit=email"
                            class="btn btn-danger" style="min-width: 124px">${edit_email}</a></p>
                    <a href="${pageContext.request.contextPath}/controller?command=display_lifehacks_by_type&type=user_like&current_page=1"
                       class="btn btn-success" style="min-width: 124px">${likes}</a>
                </div>
            </div>
        </div>
    </main>
</div>
<c:import url="../footer.jsp"/>
</body>
</html>
