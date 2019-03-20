<%--
  Created by IntelliJ IDEA.
  User: Valentin
  Date: 09.03.2019
  Time: 19:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.language}" scope="session"/>
<fmt:setBundle basename="i18n.message" var="locale"/>
<fmt:message bundle="${locale}" key="error" var="error"/>
<fmt:message bundle="${locale}" key="edit_lifehack.pagename" var="edit_lifehack"/>
<fmt:message bundle="${locale}" key="create_lifehack.name" var="name"/>
<fmt:message bundle="${locale}" key="create_lifehack.content" var="content"/>
<fmt:message bundle="${locale}" key="create_lifehack.category" var="category"/>
<fmt:message bundle="${locale}" key="create_lifehack.excerpt" var="excerpt"/>
<html>
<head>
    <title>${edit_lifehack}</title>
    <c:import url="../header.jsp"/>
</head>
<body>
<main role="main" class="container">
    <div class="d-flex justify-content-center" id="sign-up" style="margin-bottom: 10px;">
        <div class="card text-center">
            <div class="card-header p-3 mb-2 bg-success text-white">
                <h3>${edit_lifehack}</h3>
            </div>
            <div class="card-body" id="sign-up-body" style="height: auto;">
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
                <c:if test="${param.edit eq 'name'}">
                    <form class="needs-validation" method="POST" action="${pageContext.request.contextPath}/controller"
                          novalidate>
                        <input type="hidden" name="command" value="edit_lifehack_name">
                        <input type="hidden" name="lifehack_id" value="${param.id}">
                        <div class="form-group mb-5">
                            <label for="exampleFormControlInput1">${name}</label>
                            <input type="text" name="new_name"<%-- pattern="^[а-яА-ЯёЁa-zA-Z\d\s«»:,!?]{1,100}$"--%>
                                   class="form-control" maxlength="100" id="exampleFormControlInput1" placeholder="Название лайфхак"
                                   required>
                        </div>
                        <button class="btn btn-success" type="submit">Edit</button>
                    </form>
                </c:if>
                <c:if test="${param.edit eq 'excerpt'}">
                    <form class="needs-validation" method="POST" action="${pageContext.request.contextPath}/controller"
                          novalidate>
                        <input type="hidden" name="command" value="edit_lifehack_excerpt">
                        <input type="hidden" name="lifehack_id" value="${param.id}">
                        <div class="form-group mb-5">
                            <label for="exampleFormControlInput2">${excerpt}</label>
                            <input type="text" name="new_excerpt" pattern="^[а-яА-ЯёЁa-zA-Z\d\s«»:,!?.+]{1,100}$"
                                   class="form-control" maxlength="60" id="exampleFormControlInput2" placeholder="Цитата"
                                   required>
                        </div>
                        <button class="btn btn-success" type="submit">Edit</button>
                    </form>
                </c:if>
                <c:if test="${param.edit eq 'content'}">
                    <form class="needs-validation" method="POST" action="${pageContext.request.contextPath}/controller"
                          novalidate>
                        <input type="hidden" name="command" value="edit_lifehack_content">
                        <input type="hidden" name="lifehack_id" value="${param.id}">
                        <div class="form-group mb-5">
                            <label for="exampleFormControlTextarea1">${content}</label>
                            <textarea name="new_content" class="form-control" id="exampleFormControlTextarea1"
                                      rows="5" cols="5" required></textarea>
                        </div>
                        <button class="btn btn-success" type="submit">Edit</button>
                    </form>
                </c:if>
                <c:if test="${param.edit eq 'category'}">
                    <form class="needs-validation" method="POST" action="${pageContext.request.contextPath}/controller"
                          novalidate>
                        <input type="hidden" name="command" value="edit_lifehack_category">
                        <input type="hidden" name="lifehack_id" value="${param.id}">

                        <div class="form-group mb-5">
                            <label for="exampleFormControlSelect1">${category}</label>
                            <select class="form-control" name="new_category" id="exampleFormControlSelect1" required>
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
                        <button class="btn btn-success" type="submit">Edit</button>
                    </form>
                </c:if>

            </div>
        </div>
    </div>
</main>
</body>
</html>
