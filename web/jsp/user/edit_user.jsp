<%--
  Created by IntelliJ IDEA.
  User: Valentin
  Date: 09.03.2019
  Time: 16:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.language}" scope="session"/>
<fmt:setBundle basename="i18n.message" var="locale"/>
<fmt:message bundle="${locale}" key="edit_user.email" var="new_email"/>
<fmt:message bundle="${locale}" key="edit_user.newpass" var="new_password"/>
<fmt:message bundle="${locale}" key="edit_user.pagename" var="page_name"/>
<fmt:message bundle="${locale}" key="edit_user.password" var="previous"/>
<fmt:message bundle="${locale}" key="error" var="error"/>
<fmt:message bundle="${locale}" key="sign_up.submit" var="submit"/>
<html>
<head>
    <title>${page_name}</title>
</head>
<body>
<div class="d-flex justify-content-center" id="login">
    <div class="card text-center">
        <div class="card-header p-3 mb-2 bg-info text-white">
            <h3>${page_name}</h3>
        </div>
        <div class="card-body" id="login-body" style="height: auto;">
            <c:if test="${not empty message}">
                <div class="alert alert-danger alert-dismissible text-left" role="alert">
                    <h4 class="alert-heading">${error}!</h4>
                    <hr/>
                    <ul>
                            ${message}
                    </ul>
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <c:set var="message" value="" scope="session"/>
            </c:if>
            <c:if test="${param.edit eq 'password'}">
                <form name="change_password" action="${pageContext.request.contextPath}/controller" method="POST">
                    <input type="hidden" name="command" value="change_password">
                    <div class="form-group">
                        <label for="edit-previous-password">
                            <h6>
                                <p class="text-secondary">${previous}</p>
                            </h6>
                        </label>
                        <input type="password" name="previous_password" pattern="^((?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,15})$" class="form-control"
                               aria-describedby="emailHelp"
                               placeholder="Old"
                               id="edit-previous-password">

                    </div>
                    <div class="form-group">
                        <label for="edit-password">
                            <h6>
                                <p class="text-secondary">${new_password}</p>
                            </h6>
                        </label>
                        <input type="password" pattern="^((?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,15})$" name="new_password" class="form-control" id="edit-password"
                               placeholder="New">
                    </div>
                    <button type="submit" class="btn btn-primary">${submit}</button>
                </form>
            </c:if>
            <c:if test="${param.edit eq 'email'}">
                <form name="change_email" action="${pageContext.request.contextPath}/controller" method="POST">
                    <input type="hidden" name="command" value="change_email">
                    <div class="form-group">
                        <label for="new-email">
                            <h6>
                                <p class="text-secondary">${new_email}</p>
                            </h6>
                        </label>
                        <input type="email" maxlength="55"
                               pattern="^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$"
                               name="new_email" class="form-control"
                               aria-describedby="emailHelp"
                               placeholder="New email"
                               id="new-email" required>
                    </div>

                    <button type="submit" class="btn btn-primary">${submit}</button>


                </form>
            </c:if>
        </div>
    </div>
</div>
<c:import url="../header.jsp"/>
<c:import url="../footer.jsp"/>
</body>
</html>
