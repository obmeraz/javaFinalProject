<%--
  Created by IntelliJ IDEA.
  User: Valentin
  Date: 19.02.2019
  Time: 15:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.language}" scope="session"/>
<fmt:setBundle basename="i18n.message" var="locale"/>
<fmt:message bundle="${locale}" key="create_lifehack.add_lifehack" var="add_lifehack"/>
<fmt:message bundle="${locale}" key="error" var="error"/>
<fmt:message bundle="${locale}" key="create_lifehack.name" var="name"/>
<fmt:message bundle="${locale}" key="create_lifehack.addButton" var="add"/>
<fmt:message bundle="${locale}" key="create_lifehack.content" var="content"/>
<fmt:message bundle="${locale}" key="create_lifehack.category" var="category"/>
<fmt:message bundle="${locale}" key="create_lifehack.excerpt" var="excerpt"/>
<fmt:message bundle="${locale}" key="create_lifehack.image" var="image"/>
<html>
<head>
    <c:import url="../header.jsp"/>
    <title>Add LifeHack</title>
</head>
<body>
<div class="cards-body" style="padding-top: 30px">
    <main role="main" class="container">
        <div class="d-flex justify-content-center mb-5" id="sign-up" style="margin-bottom: 10px;">
            <div class="card text-center">
                <div class="card-header p-3 mb-2 bg-success text-white">
                    <h3>${add_lifehack}</h3>
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
                    <c:set var="errorMessages" value="" scope="session"/>
                </c:if>
                <div class="card-body" id="sign-up-body" style="height: auto;">
                    <form class="needs-validation" method="POST" action="${pageContext.request.contextPath}/controller"
                          enctype="multipart/form-data" novalidate>
                        <input type="hidden" name="command" value="create_lifehack">
                        <div class="form-group mb-5" style="position: relative;">
                            <label for="lifehackName">${name}</label>
                            <input type="text"
                                   name="lifehack_name" pattern="^[а-яА-ЯёЁa-zA-Z\d\s\W]{5,100}$"
                                   class="form-control" maxlength="100" id="lifehackName"
                                   placeholder="Name"
                                   required>
                            <div class="invalid-tooltip">
                                Correct lifehack name
                            </div>
                        </div>
                        <div class="form-group mb-5" style="position: relative">
                            <label for="lifehackExcerpt">${excerpt}</label>
                            <input type="text" name="excerpt" pattern="^[а-яА-ЯёЁa-zA-Z\d\s\W]{5,60}$"
                                   class="form-control" maxlength="60" id="lifehackExcerpt"
                                   placeholder="Excerpt"
                                   required>
                            <div class="invalid-tooltip">
                                Correct excerpt
                            </div>
                        </div>
                        <div class="form-group mb-5" style="position: relative">
                            <label for="lifehackContent">${content}</label>
                            <textarea name="lifehack_content" class="form-control" id="lifehackContent"
                                      rows="5" cols="5" required></textarea>
                            <div class="invalid-tooltip">
                                Correct content
                            </div>
                        </div>
                        <div class="form-group mb-5">
                            <label for="categorySelect">${category}</label>
                            <select class="form-control" name="category" id="categorySelect" required>
                                <option>Sport</option>
                                <option>Education</option>
                                <option>Life</option>
                                <option>Cinema</option>
                                <option>Technologies</option>
                                <option>Motivation</option>
                                <option>Food</option>
                                <option>News</option>
                            </select>
                        </div>

                        <div class="form-group mb-5">
                            <label for="uploadLifehackImage">${image}</label>
                            <input type="file" name="image" class="form-control-file" id="uploadLifehackImage"
                                   required>
                        </div>
                        <button class="btn btn-success" id="myBtn" type="submit">${add}</button>
                    </form>
                </div>
            </div>
        </div>
    </main>
</div>

<c:import url="../footer.jsp"/>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/script.js"></script>
</body>
</html>
