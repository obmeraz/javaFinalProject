<%--
  Created by IntelliJ IDEA.
  User: Valentin
  Date: 18.02.2019
  Time: 21:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<c:import url="header.jsp"/>

<div class="banner">
    <img src="${pageContext.request.contextPath}/img/lifehack.jpg" width="100%" alt="">
</div>


<main role="main" class="container">

    <div class="text-center mt-5 pt-5" style="margin-bottom: 100px">
        <h1>Добро пожаловать на LifeHacker!</h1>
        <p class="lead">Этот ресурс предназначен для людей, кто хочет значительно упростить свою жизнь.<br>
            Если тебе все еще интересно, то можешь смело смотреть самые крутые и новые лайфхаки!</p>
    </div>

</main><!-- /.container -->
<c:import url="footer.jsp"/>
</body>
</html>
