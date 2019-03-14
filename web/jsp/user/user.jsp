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
<html>
<head>
    <title>User Account</title>
</head>
<body>
<c:import url="../header.jsp"/>
<div class="cards-body" style="padding-top: 30px">
    <main role="main" class="container" style="width: 550px;">
        <div class="d-flex justify-content-center mb-5" style="margin-top: 100px;">
            <div class="card text-center">
                <div class="card-body">
                    <h5 class="card-title">${sessionScope.user.firstName} ${sessionScope.user.lastName}</h5>
                    <p>Username:</p>
                    <p class="card-text">${sessionScope.user.nickName}</p>
                    <p>Email:</p>
                    <p class="card-text">${sessionScope.user.email}</p>
                    <p class="card-text"><a
                            href="${pageContext.request.contextPath}/jsp/user/edit_user.jsp?edit=password"
                            class="btn btn-danger">Edit password</a></p>
                    <p class="card-text"><a href="${pageContext.request.contextPath}/jsp/user/edit_user.jsp?edit=email"
                                            class="btn btn-danger">Edit email</a></p>
                    <a href="${pageContext.request.contextPath}/controller?command=display_lifehacks_by_type&type=user_like&current_page=1"
                       class="btn btn-success">Like lifehacks</a>
                </div>
            </div>
        </div>
    </main>
</div>
<c:import url="../footer.jsp"/>
</body>
</html>
