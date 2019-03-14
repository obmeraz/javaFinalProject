<%--
  Created by IntelliJ IDEA.
  User: Valentin
  Date: 23.02.2019
  Time: 16:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<html>
<head>
    <title>Log in</title>
</head>
<body>
<c:import url="../header.jsp"/>
<main role="main" class="container">
    <div class="d-flex justify-content-center" id="login">
        <div class="card text-center">
            <div class="card-header p-3 mb-2 bg-info text-white">
                <h3>Log in</h3>
            </div>
            <div class="card-body" id="login-body">
                <form name="login" action="${pageContext.request.contextPath}/controller" method="POST">
                    <input type="hidden" name="command" value="log_in">
                    <c:if test="${not empty message}">
                        <div class="alert alert-danger alert-dismissible text-left" role="alert">
                            <h4 class="alert-heading">Error!</h4>
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
                                <p class="text-secondary">Email address</p>
                            </h6>
                        </label>
                        <input type="email" name="email" maxlength="55" class="form-control"
                               aria-describedby="emailHelp"
                               placeholder="Enter email"
                               id="login-email">

                    </div>
                    <div class="form-group">
                        <label for="login-password">
                            <h6>
                                <p class="text-secondary">Password</p>
                            </h6>
                        </label>
                        <input type="password" name="password" class="form-control" maxlength="256" id="login-password"
                               placeholder="Password">
                    </div>
                    <button type="submit" class="btn btn-primary">Submit</button>
                </form>
            </div>
            <div class="card-footer text-muted">
                Not a sign up yet?<p><a href="${pageContext.request.contextPath}/jsp/guest/sign_up.jsp"
                                        class="text-primary">Sign
                up</a></p>
            </div>
        </div>
    </div>
</main><!-- /.container -->

<c:import url="../footer.jsp"/>
</body>
</html>
