<%--
  Created by IntelliJ IDEA.
  User: Valentin
  Date: 09.03.2019
  Time: 19:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.language}" scope="session"/>
<fmt:setBundle basename="i18n.message" var="locale"/>
<fmt:message bundle="${locale}" key="edit_lifehack.category" var="edit_category"/>
<fmt:message bundle="${locale}" key="edit_lifehack.content" var="edit_content"/>
<fmt:message bundle="${locale}" key="edit_lifehack.delete" var="delete"/>
<fmt:message bundle="${locale}" key="edit_lifehack.excerpt" var="edit_excerpt"/>
<fmt:message bundle="${locale}" key="edit_lifehack.name" var="edit_name"/>
<fmt:message bundle="${locale}" key="edit_lifehack.pagename" var="edit_lifehack"/>

<html>
<head>
    <title>${edit_lifehack}</title>
    <c:import url="../header.jsp"/>
</head>
<body>

<div class="cards-body" style="padding-top: 30px">
    <main role="main" class="container">
        <div class="d-flex justify-content-center mb-5" style="margin-top: 100px;">
            <div class="card text-center" style="width: 5000px;max-width: 100%;">
                <div class="card-header p-3 mb-2 bg-success text-white">
                    <h3>${edit_lifehack}</h3>
                </div>
                <c:if test="${not empty message}">
                    <c:set var="message" value="" scope="session"/>
                </c:if>
                <div class="card-body">
                    <h5 class="card-title">id(${sessionScope.id})</h5>
                    <p class="card-text"><a
                            href="${pageContext.request.contextPath}/jsp/admin/edit_lifehack_input.jsp?edit=name&id=${sessionScope.id}"
                            class="btn btn-danger">${edit_name}</a></p>
                    <p class="card-text"><a
                            href="${pageContext.request.contextPath}/jsp/admin/edit_lifehack_input.jsp?edit=excerpt&id=${sessionScope.id}"
                            class="btn btn-danger">${edit_excerpt}</a></p>
                    <p class="card-text"><a
                            href="${pageContext.request.contextPath}/jsp/admin/edit_lifehack_input.jsp?edit=content&id=${sessionScope.id}"
                            class="btn btn-danger">${edit_content}</a></p>
                    <p class="card-text"><a
                            href="${pageContext.request.contextPath}/jsp/admin/edit_lifehack_input.jsp?edit=category&id=${sessionScope.id}"
                            class="btn btn-danger">${edit_category}</a></p>
                    <p class="card-text"><a
                            href="${pageContext.request.contextPath}/controller?command=delete_lifehack&lifehack_id=${sessionScope.id}"
                            class="btn btn-danger">${delete}</a></p>
                </div>
            </div>
        </div>
    </main>
</div>
</body>
</html>
