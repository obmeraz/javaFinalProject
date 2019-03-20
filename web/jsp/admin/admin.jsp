<%--
  Created by IntelliJ IDEA.
  User: Valentin
  Date: 26.02.2019
  Time: 10:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.language}" scope="session"/>
<fmt:setBundle basename="i18n.message" var="locale"/>
<fmt:message bundle="${locale}" key="admin.pagename" var="page"/>
<fmt:message bundle="${locale}" key="admin.lifehack.table.action" var="action"/>
<fmt:message bundle="${locale}" key="admin.lifehack.table.author" var="author"/>
<fmt:message bundle="${locale}" key="admin.lifehack.table.category" var="category"/>
<fmt:message bundle="${locale}" key="admin.lifehack.table.likes_amount" var="likes"/>
<fmt:message bundle="${locale}" key="admin.lifehack.table.name" var="lifehack_name"/>
<fmt:message bundle="${locale}" key="admin.lifehack.table.publication_date" var="date"/>
<fmt:message bundle="${locale}" key="admin.lifehacklist" var="lifehacks_list"/>
<fmt:message bundle="${locale}" key="admin.userlist" var="users_list"/>
<fmt:message bundle="${locale}" key="admin.users" var="usersName"/>
<fmt:message bundle="${locale}" key="admin.lifehacks" var="lifehacksName"/>
<fmt:message bundle="${locale}" key="admin.user.table.delete" var="delete"/>
<fmt:message bundle="${locale}" key="admin.user.table.giverights" var="give"/>
<fmt:message bundle="${locale}" key="admin.user.table.lastname" var="lastname"/>
<fmt:message bundle="${locale}" key="admin.user.table.name" var="user_name"/>
<fmt:message bundle="${locale}" key="admin.user.table.role" var="role"/>
<fmt:message bundle="${locale}" key="admin.user.table.username" var="username"/>
<fmt:message bundle="${locale}" key="admin.addButton" var="add"/>
<html>
<head>
    <title>${page}</title>
</head>
<body>
<c:import url="../header.jsp"/>
<main role="main" class="container">
    <div class="row" style="margin-top: 100px">
        <div class="col-3">
            <div class="list-group">
                <a href="${pageContext.request.contextPath}/controller?command=display_users_list"
                   class="list-group-item">${usersName}</a>
                <a href="${pageContext.request.contextPath}/controller?command=display_lifehacks_list"
                   class="list-group-item">${lifehacksName}</a>

            </div>
        </div>
    </div>
    <div class="mr-3">
        <a href="${pageContext.request.contextPath}/jsp/admin/create_lifehack.jsp"
           class="btn btn-success mt-3">${add}</a>
    </div>
    <c:if test="${users ne null}">
        <div class="col-lg-9">
            <div class="table-responsive" id="adminTable">
                <table class="table">
                    <caption>${users_list}</caption>
                    <thead>
                    <tr>
                        <th scope="col">id</th>
                        <th scope="col">${user_name}</th>
                        <th scope="col">${lastname}</th>
                        <th scope="col">${username}</th>
                        <th scope="col">${role}</th>
                        <th scope="col">${delete}</th>
                        <th scope="col">${give}</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${users}" var="user" varStatus="status">
                        <tr>
                            <th scope="row">${user.userId}</th>
                            <td>${user.firstName}</td>
                            <td>${user.lastName}</td>
                            <td>${user.nickName}</td>
                            <td>${user.role}</td>
                            <c:if test="${sessionScope.user.userId ne user.userId}">
                                <td><a class="btn btn-danger"
                                       href="${pageContext.request.contextPath}/controller?command=delete_user&user_id=${user.userId}"
                                       role="button">DELETE</a></td>
                                <c:if test="${user.role eq 'USER'}">
                                    <td><a class="btn btn-warning"
                                           href="${pageContext.request.contextPath}/controller?command=make_user_admin&user_id=${user.userId}"
                                           role="button">MAKE ADMIN</a></td>
                                </c:if>
                                <c:if test="${user.role eq 'ADMIN'}">
                                    <td><a class="btn btn-danger"
                                           href="${pageContext.request.contextPath}/controller?command=remove_user_admin&user_id=${user.userId}"
                                           role="button">MAKE USER</a></td>
                                </c:if>
                            </c:if>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </c:if>
    <c:choose>
        <c:when test="${(lifehacks ne null) or (not empty lifehacks)}">
            <div class="col-lg-9">
                <div class="table-responsive" id="adminTable2">
                    <table class="table">
                        <caption>${lifehacks_list}</caption>
                        <thead>
                        <tr>
                            <th scope="col">id</th>
                            <th scope="col">${lifehack_name}</th>
                            <th scope="col">${category}</th>
                            <th scope="col">${date}</th>
                            <th scope="col">${author}</th>
                            <th scope="col">${likes}</th>
                            <th scope="col">${action}</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${lifehacks}" var="lifehack" varStatus="status">
                            <tr>
                                <th scope="row">${lifehack.lifehackId}</th>
                                <td>${lifehack.name}</td>
                                <td>${lifehack.category}</td>
                                <jsp:useBean id="dateValue" class="java.util.Date"/>
                                <jsp:useBean id="dataValue" class="java.util.Date"/>
                                <jsp:setProperty name="dateValue" property="time"
                                                 value="${lifehack.publicationDate}"/>
                                <td><fmt:formatDate value="${dateValue}"
                                                    pattern="MM/dd/yyyy HH:mm:ss"/></td>
                                <td>${lifehack.user.firstName}</td>
                                <td>${lifehack.likesAmount}</td>
                                <td><a class="btn btn-danger"
                                       href="${pageContext.request.contextPath}/controller?command=delete_lifehack&lifehack_id=${lifehack.lifehackId}"
                                       role="button">DELETE</a></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </c:when>
        <c:otherwise>
            ${message}
            <c:set var="message" value="" scope="session"/>
        </c:otherwise>

    </c:choose>

</main>
<c:import url="../footer.jsp"/>
</body>
</html>
