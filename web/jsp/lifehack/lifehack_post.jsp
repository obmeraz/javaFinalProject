<%--
  Created by IntelliJ IDEA.
  User: Valentin
  Date: 24.02.2019
  Time: 11:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>


    <title>
        Лайфхакер
    </title>
</head>
<body>
<script>var lifehack_id =${lifehack.lifehackId}</script>

<c:import url="../header.jsp"/>
<div class="cards-body" style="padding-top: 30px">
    <main role="main" class="container">
        <jsp:useBean id="dateValue" class="java.util.Date"/>
        <div class="conteiner d-flex justify-content-center" style="margin-top: 68px;">
            <div class="card  text-center">
                <img src="data:image/jpg;base64,${lifehack.image}" class="card-img-top" alt="...">
                <div class="card-body" style="width: 830px; height: auto; white-space: normal">
                    <h5 class="card-title">${lifehack.name}</h5>
                    <p class="card-text">${lifehack.content}</p>
                </div>

                <div class="card-footer text-muted">
                    <div class="d-flex justify-content-center">
                        <p class="card-text">
                            ${lifehack.user.nickName}
                            <jsp:useBean id="dataValue" class="java.util.Date"/>
                            <jsp:setProperty name="dateValue" property="time"
                                             value="${lifehack.publicationDate}"/>
                            <small class="text-muted">Publication date <fmt:formatDate value="${dateValue}"
                                                                                       pattern="MM/dd/yyyy HH:mm:ss"/>
                                <c:if test="${(sessionScope.user.role eq 'USER') or (sessionScope.user.role eq 'ADMIN')}">
                                    <c:if test="${liked ne true}">
                                        <img src="${pageContext.request.contextPath}/img/heart.png" id="likeLifehack"
                                             class="ml-5 mr-1" alt="..."
                                             style="width: 24px; height: 24px">
                                    </c:if>
                                    <c:if test="${liked eq true}">
                                        <img src="${pageContext.request.contextPath}/img/active.png" id="likeLifehack"
                                             class="ml-5 mr-1" alt="..."
                                             style="width: 24px; height: 24px">
                                    </c:if>
                                    <div id="ajaxGetUserServletResponse">
                                        <h6>${lifehack.likesAmount}</h6>
                                    </div>
                                </c:if>
                                <c:if test="${sessionScope.user.role eq 'ADMIN'}">
                                <span class="badge badge-danger">  <a class="text-light"
                                                                      href="${pageContext.request.contextPath}/controller?command=edit_lifehack&name=${lifehack.name}&lifehack_id=${lifehack.lifehackId}"
                                                                      style="text-decoration:none">
                                   EDIT</a></span>
                                </c:if>
                            </small>
                        </p>
                    </div>
                </div>
            </div>
        </div>

        <c:if test="${not empty errorMessages}">
            <div class="alert alert-danger alert-dismissible" role="alert">
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

        <c:if test="${sessionScope.role eq 'ADMIN' or sessionScope.role eq 'USER'}">
            <div class="comments " style="margin-bottom: 100px;">
                <form action="${pageContext.request.contextPath}/controller?lifehack_id=${lifehack.lifehackId}"
                      method="POST">
                    <input type="hidden" name="command" value="leave_comment">
                    <div class="form-group">
                        <label for="exampleFormControlTextarea1">Комментарии</label>
                        <textarea name="comment_content" class="form-control mb-3" id="exampleFormControlTextarea1"
                                  rows="4" maxlength="255"
                                  required></textarea>
                    </div>
                    <button class="btn btn-danger" type="submit">Send</button>
                </form>


                <div class="btn-group">
                    <button type="button" class="btn btn-danger dropdown-toggle" data-toggle="dropdown"
                            aria-haspopup="true" aria-expanded="false">
                        Sort comments
                    </button>
                    <div class="dropdown-menu">
                        <a class="dropdown-item"
                           href="${pageContext.request.contextPath}/controller?command=sort_comments&lifehack_id=${lifehack.lifehackId}&sort_type=sort_by_likes">By
                            likes</a>
                        <a class="dropdown-item"
                           href="${pageContext.request.contextPath}/controller?command=sort_comments&lifehack_id=${lifehack.lifehackId}&sort_type=sort_by_date">By
                            date</a>
                    </div>
                </div>

                <c:forEach items="${comments}" var="comment" varStatus="status">
                    <script>var comment_id =${comment.commentId}</script>
                    <input type="hidden" id="ppp" value="${comment.commentId}">
                    <jsp:setProperty name="dateValue" property="time"
                                     value="${comment.postDate}"/>
                    <div class="card" style="margin-top: 35px">
                        <div class="card-header">
                            <c:out value="${comment.user.firstName}"/>
                            <c:out value="${comment.user.lastName}"/>
                            <fmt:formatDate value="${dateValue}" pattern="MM/dd/yyyy HH:mm:ss"/>
                            <c:if test="${sessionScope.user.role eq 'ADMIN'}">
                                <button type="button" class="close" aria-label="Close">
                                    <a href="${pageContext.request.contextPath}/controller?command=delete_comment&comment_id=${comment.commentId}">
                                        <span aria-hidden="true">&times;</span>
                                    </a>
                                </button>
                            </c:if>
                        </div>
                        <div class="card-body">
                            <p class="card-text">${comment.content}</p>
                        </div>
                        <div class="card-footer text-muted">
                            <div class="d-flex justify-content-center">
                                <p class="card-text">
                                    <small>
                                        <c:if test="${commentsLikesMap.get(comment) ne true}">
                                            <img src="${pageContext.request.contextPath}/img/heart.png"
                                                 class="ml-5 mr-1 classlike" alt="..." id="${comment.commentId}"
                                                 style="width: 24px; height: 24px">
                                        </c:if>
                                        <c:if test="${commentsLikesMap.get(comment) eq true}">
                                            <img src="${pageContext.request.contextPath}/img/active.png"
                                                 class="ml-5 mr-1 classlike" alt="..." id="${comment.commentId}"
                                                 style="width: 24px; height: 24px">
                                        </c:if>
                                        <div id="ajaxGetCommentResponse${comment.commentId}">
                                            <h6>${comment.likesAmount}</h6></div>
                                    </small>
                                </p>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </c:if>
    </main>
</div>
<hr>
<script src="${pageContext.request.contextPath}/js/jquery-3.3.1.min.js"></script>
<script src="${pageContext.request.contextPath}/js/ajax-code.js" type="text/javascript"></script>

<c:import url="../footer.jsp"/>
</body>
</html>
