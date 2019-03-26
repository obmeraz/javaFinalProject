<%--
  Created by IntelliJ IDEA.
  User: Valentin
  Date: 23.02.2019
  Time: 16:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.language}" scope="session"/>
<fmt:setBundle basename="i18n.message" var="locale"/>
<fmt:message bundle="${locale}" key="header.login" var="login"/>
<fmt:message bundle="${locale}" key="header.signup" var="sign_up"/>
<fmt:message bundle="${locale}" key="login.email" var="email"/>
<fmt:message bundle="${locale}" key="login.password" var="password"/>
<fmt:message bundle="${locale}" key="login.submit" var="submit"/>
<fmt:message bundle="${locale}" key="error" var="error"/>
<fmt:message bundle="${locale}" key="login.text" var="text"/>


<html>
<head>
    <title>${login}</title>
</head>
<body>
<c:import url="../header.jsp"/>
<main role="main" class="container">
    <div class="d-flex justify-content-center mb-5" id="login">
        <div class="card text-center">
            <div class="card-header p-3 mb-2 bg-info text-white">
                <h3>${login}</h3>
            </div>
            <div class="card-body" id="login-body" style="height: auto">
                <form class="needs-validation" name="login" action="${pageContext.request.contextPath}/controller"
                      method="POST" novalidate>
                    <input type="hidden" name="command" value="log_in">
                    <c:if test="${not empty message}">
                        <div class="alert alert-danger alert-dismissible text-left" role="alert">
                            <h4 class="alert-heading">${error}!</h4>
                            <hr/>
                            <ul>
                                <li>${message}</li>
                            </ul>
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <c:set var="message" value="" scope="session"/>
                    </c:if>
                    <div class="form-group">
                        <label for="login-email">
                            <h6>
                                <p class="text-secondary">${email}</p>
                            </h6>
                        </label>
                        <input type="email"
                               pattern="^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$"
                               name="email" maxlength="60" class="form-control"
                               aria-describedby="emailHelp"
                               placeholder="Enter email"
                               id="login-email"
                               required>

                    </div>
                    <div class="form-group">
                        <label for="login-password">
                            <h6>
                                <p class="text-secondary">${password}</p>
                            </h6>
                        </label>
                        <input type="password" name="password" class="form-control" maxlength="15" id="login-password"
                               placeholder="Password" pattern="^((?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,15})$" required>
                    </div>
                    <button type="submit" class="btn btn-primary">${submit}</button>
                </form>
            </div>
            <div class="card-footer text-muted">
                ${text}<p><a href="${pageContext.request.contextPath}/jsp/guest/sign_up.jsp"
                             class="text-primary">${sign_up}</a></p>
            </div>
        </div>
    </div>
</main>
<c:import url="../footer.jsp"/>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/script.js"></script>
</body>
</html>
