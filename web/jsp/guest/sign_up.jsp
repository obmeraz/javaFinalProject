<%--
  Created by IntelliJ IDEA.
  User: Valentin
  Date: 18.02.2019
  Time: 22:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <c:import url="../header.jsp"/>
    <title>Sign up</title>
</head>
<body>
<main role="main" class="container">
    <div class="d-flex justify-content-center" id="sign-up">
        <div class="card text-center">
            <div class="card-header p-3 mb-2 bg-info text-white">
                <h3>Sign up</h3>
            </div>
            <c:if test="${not empty errorMessages}">
                <div class="alert alert-danger alert-dismissible text-left" role="alert">
                    <h4 class="alert-heading">Error!</h4>
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
            <div class="card-body" id="sign-up-body">
                <form class="needs-validation" method="POST" action="${pageContext.request.contextPath}/controller"
                      novalidate>
                    <input type="hidden" name="command" value="sign_up">
                    <div class="form-group row mb-5">
                        <label for="inputFirstName" class="col-sm-2 col-form-label">First name</label>
                        <div class="col-sm-10">
                            <input type="text" pattern="^[A-ZЁА-Я]([a-z]{1,20}|[а-яё]{1,20})$" name="firstname"
                                   class="form-control" id="inputFirstName" maxlength="45" placeholder="First name"
                                   required>
                            <small id="firstNameHelp" class="form-text text-muted">
                                first name must be started from big letter
                            </small>
                        </div>
                    </div>
                    <div class="form-group row mb-5">
                        <label for="inputLastName" class="col-sm-2 col-form-label">Last name</label>
                        <div class="col-sm-10">
                            <input type="text" name="lastname" pattern="^[A-ZЁА-Я]([a-z]{1,20}|[а-яё]{1,20})$"
                                   class="form-control" id="inputLastName" maxlength="45" placeholder="Last name"
                                   required>
                            <small id="lastNameHelp" class="form-text text-muted">
                                last name must be started from big letter
                            </small>
                        </div>
                    </div>


                    <div class="form-group row mb-5">
                        <label for="inputUserName" class="col-sm-2 col-form-label">Username</label>
                        <div class="col-sm-10">
                            <input type="text" name="nickname" pattern="^[A-Za-zА-ЯЁа-яё\d]{1,20}$"
                                   class="form-control" maxlength="45" id="inputUserName" placeholder="Username"
                                   required>
                        </div>
                    </div>

                    <div class="form-group row mb-5">
                        <label for="inputEmail" class="col-sm-2 col-form-label">Email</label>
                        <div class="col-sm-10">
                            <input type="email" name="email" maxlength="55"
                                   pattern="^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$"
                                   class="form-control" id="inputEmail" placeholder="Email" required>
                        </div>
                    </div>

                    <div class="form-group row mb-5">
                        <label for="inputPassword" class="col-sm-2 col-form-label">Password</label>
                        <div class="col-sm-10">
                            <input type="password" name="password" pattern="^((?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,15})$"
                                   class="form-control" maxlength="256" id="inputPassword" placeholder="Password"
                                   required>
                            <small id="passwordHelpBlock" class="form-text text-muted">
                                Your password must be 8-20 characters long, contain letters and numbers
                            </small>
                        </div>
                    </div>

                    <div class="form-group row mb-5">
                        <label for="repeatPassword" class="col-sm-2 col-form-label">Repeat</label>
                        <div class="col-sm-10">
                            <input type="password" maxlength="256" name="repeat_password"
                                   pattern="^((?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,15})$"
                                   class="form-control" id="repeatPassword" placeholder="Password" required>
                        </div>
                    </div>

                    <div class="d-flex justify-content-start">
                        <div class="form-group">
                            <div class="form-check">
                                <input class="form-check-input" type="checkbox" value="" id="checkAgreement" required>
                                <label class="form-check-label" for="checkAgreement">
                                    Agree to terms and conditions of the website LifeHacker
                                </label>
                            </div>
                        </div>
                    </div>
                    <div class="d-flex justify-content-around">
                        <div class="form-group row">
                            <div class="col-sm-10">
                                <button type="submit" class="btn btn-primary">Submit</button>
                            </div>
                        </div>
                    </div>
                </form>
            </div>

            <div class="card-footer text-muted" style="height: 65px">
                Already sign up?<p><a href="${pageContext.request.contextPath}/jsp/guest/login.jsp"
                                      class="text-primary">Log
                in</a></p>
            </div>
        </div>
    </div>
</main>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/script.js"></script>
</body>
</html>
