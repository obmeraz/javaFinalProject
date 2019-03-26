<%--
  Created by IntelliJ IDEA.
  User: Valentin
  Date: 18.02.2019
  Time: 22:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.language}" scope="session"/>
<fmt:setBundle basename="i18n.message" var="locale"/>
<fmt:message bundle="${locale}" key="header.login" var="login"/>
<fmt:message bundle="${locale}" key="header.signup" var="sign_up"/>
<fmt:message bundle="${locale}" key="sign_up.firstname" var="name"/>
<fmt:message bundle="${locale}" key="sign_up.lastname" var="last_name"/>
<fmt:message bundle="${locale}" key="sign_up.password" var="password"/>
<fmt:message bundle="${locale}" key="sign_up.repeat" var="repeat_password"/>
<fmt:message bundle="${locale}" key="sign_up.submit" var="submit"/>
<fmt:message bundle="${locale}" key="sign_up.terms" var="terms"/>
<fmt:message bundle="${locale}" key="sign_up.text" var="text"/>
<fmt:message bundle="${locale}" key="sign_up.username" var="username"/>
<fmt:message bundle="${locale}" key="sign_up.email" var="email"/>
<fmt:message bundle="${locale}" key="error" var="error"/>
<fmt:message bundle="${locale}" key="sign_up.text.firstname" var="name_text"/>
<fmt:message bundle="${locale}" key="sign_up.text.lastname" var="lastname_text"/>
<fmt:message bundle="${locale}" key="sign_up.text.password" var="password_text"/>
<fmt:message bundle="${locale}" key="sign_up.text.repeat" var="repeat_text"/>


<html>
<head>
    <c:import url="../header.jsp"/>
    <title>${sign_up}</title>
</head>
<body>
<main role="main" class="container">
    <div class="d-flex justify-content-center" id="sign-up">
        <div class="card text-center">
            <div class="card-header p-3 mb-2 bg-info text-white">
                <h3>${sign_up}</h3>
            </div>
            <c:if test="${not empty errorMessages}">
                <div class="alert alert-danger alert-dismissible text-left" role="alert">
                    <h4 class="alert-heading">${error}!</h4>
                    <hr/>
                    <ul>
                        <c:forEach items="${errorMessages}" var="message">
                            <li>${message}</li>
                        </c:forEach>
                    </ul>
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </c:if>
            <div class="card-body" id="sign-up-body" style="height: auto;width: auto">
                <form class="needs-validation" method="POST" action="${pageContext.request.contextPath}/controller"
                      novalidate>
                    <input type="hidden" name="command" value="sign_up">
                    <div class="form-group row mb-5">
                        <label for="inputFirstName" class="col-sm-2 col-form-label">${name}</label>
                        <div class="col-sm-10">
                            <input type="text" pattern="^[A-ZЁА-Я]([a-z]{1,20}|[а-яё]{1,20})$" name="firstname"
                                   class="form-control" id="inputFirstName" maxlength="45" placeholder="First name"
                                   required>
                            <small id="firstNameHelp" class="form-text text-muted">
                                ${name_text}
                            </small>
                        </div>
                    </div>
                    <div class="form-group row mb-5">
                        <label for="inputLastName" class="col-sm-2 col-form-label">${last_name}</label>
                        <div class="col-sm-10">
                            <input type="text" name="lastname" pattern="^[A-ZЁА-Я]([a-z]{1,20}|[а-яё]{1,20})$"
                                   class="form-control" id="inputLastName" maxlength="45" placeholder="Last name"
                                   required>
                            <small id="lastNameHelp" class="form-text text-muted">
                               ${lastname_text}
                            </small>
                        </div>
                    </div>


                    <div class="form-group row mb-5">
                        <label for="inputUserName" class="col-sm-2 col-form-label">${username}</label>
                        <div class="col-sm-10">
                            <input type="text" name="nickname" pattern="^[A-Za-zА-ЯЁа-яё\d]{1,20}$"
                                   class="form-control" maxlength="45" id="inputUserName" placeholder="Username"
                                   required>
                        </div>
                    </div>

                    <div class="form-group row mb-5">
                        <label for="inputEmail" class="col-sm-2 col-form-label">${email}</label>
                        <div class="col-sm-10">
                            <input type="email" name="email" maxlength="55"
                                   pattern="^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$"
                                   class="form-control" id="inputEmail" placeholder="Email" required>
                        </div>
                    </div>

                    <div class="form-group row mb-5">
                        <label for="inputPassword" class="col-sm-2 col-form-label">${password}</label>
                        <div class="col-sm-10">
                            <input type="password" name="password" pattern="^((?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,15})$"
                                   class="form-control" maxlength="15" id="inputPassword" placeholder="Password"
                                   required>
                            <small id="passwordHelpBlock" class="form-text text-muted">
                               ${password_text}
                            </small>
                        </div>
                    </div>

                    <div class="form-group row mb-5">
                        <label for="repeatPassword" class="col-sm-2 col-form-label">${repeat_password}</label>
                        <div class="col-sm-10">
                            <input type="password" maxlength="15" name="repeat_password"
                                   pattern="^((?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,15})$"
                                   class="form-control" id="repeatPassword" placeholder="Password" required>
                            <small id="repeatPasswordHelp" class="form-text text-muted">
                                ${repeat_text}
                            </small>
                        </div>
                    </div>

                    <div class="d-flex justify-content-start">
                        <div class="form-group">
                            <div class="form-check">
                                <input class="form-check-input" type="checkbox" value="" id="checkAgreement" required>
                                <label class="form-check-label" for="checkAgreement">
                                    ${terms}
                                </label>
                            </div>
                        </div>
                    </div>
                    <div class="d-flex justify-content-around">
                        <div class="form-group row">
                            <div class="col-sm-10">
                                <button type="submit" class="btn btn-primary">${submit}</button>
                            </div>
                        </div>
                    </div>
                </form>
            </div>

            <div class="card-footer text-muted" style="height: 65px">
                ${text}<p><a href="${pageContext.request.contextPath}/jsp/guest/login.jsp"
                             class="text-primary">${login}</a></p>
            </div>
        </div>
    </div>
</main>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/script.js"></script>
</body>
</html>
