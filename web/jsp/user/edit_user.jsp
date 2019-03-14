<%--
  Created by IntelliJ IDEA.
  User: Valentin
  Date: 09.03.2019
  Time: 16:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<html>
<head>
    <title>Change password</title>
</head>
<body>
<div class="d-flex justify-content-center" id="login">
    <div class="card text-center">
        <div class="card-header p-3 mb-2 bg-info text-white">
            <h3>Change</h3>
        </div>
        <div class="card-body" id="login-body">
            <c:if test="${not empty message}">
                <div class="alert alert-danger alert-dismissible text-left" role="alert">
                    <h4 class="alert-heading">Error!</h4>
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
                        <label for="login-email">
                            <h6>
                                <p class="text-secondary">Previous password</p>
                            </h6>
                        </label>
                        <input type="password" name="previous_password" class="form-control"
                               aria-describedby="emailHelp"
                               placeholder="Old"
                               id="login-email">

                    </div>
                    <div class="form-group">
                        <label for="login-password">
                            <h6>
                                <p class="text-secondary">New password</p>
                            </h6>
                        </label>
                        <input type="password" name="new_password" class="form-control" id="login-password"
                               placeholder="New">
                    </div>
                    <button type="submit" class="btn btn-primary">Submit</button>
                </form>
            </c:if>
            <c:if test="${param.edit eq 'email'}">
                <form name="change_password" action="${pageContext.request.contextPath}/controller" method="POST">
                    <input type="hidden" name="command" value="change_email">
                    <div class="form-group">
                        <label for="login-email">
                            <h6>
                                <p class="text-secondary">New email</p>
                            </h6>
                        </label>
                        <input type="email" name="new_email" class="form-control"
                               aria-describedby="emailHelp"
                               placeholder="New email"
                               id="new-email" required>
                    </div>

                    <button type="submit" class="btn btn-primary">Submit</button>


                </form>
            </c:if>
        </div>
    </div>
</div>
<c:import url="../header.jsp"/>
<c:import url="../footer.jsp"/>
</body>
</html>
