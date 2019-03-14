<%--
  Created by IntelliJ IDEA.
  User: Valentin
  Date: 23.02.2019
  Time: 18:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<body>
<nav aria-label="Navigation for Lifehacks">
    <ul class="pagination pagination-md justify-content-center" style="margin: 0px; padding-bottom: 23px;">
        <c:if test="${currentPage != 1}">
            <li class="page-item"><a class="page-link text-dark"
                                     href="${pageContext.request.contextPath}/controller?
                                     recordsPerPage=${recordsPerPage}&current_page=${currentPage-1}&command=display_lifehacks_by_type&type=${type}&category=${category}">Previous</a>
            </li>
        </c:if>

        <c:forEach begin="1" end="${numberOfPages}" var="i">
            <c:choose>
                <c:when test="${currentPage eq i}">
                    <li class="page-item active"><a class="page-link text-dark">
                            ${i} <span class="sr-only">(current)</span></a>
                    </li>
                </c:when>
                <c:otherwise>
                    <li class="page-item"><a class="page-link text-dark"
                                             href="${pageContext.request.contextPath}/controller?
                                             recordsPerPage=${recordsPerPage}&current_page=${i}&command=display_lifehacks_by_type&type=${type}&category=${category}">${i}</a>
                    </li>
                </c:otherwise>
            </c:choose>
        </c:forEach>

        <c:if test="${currentPage lt numberOfPages}">
            <li class="page-item"><a class="page-link text-dark"
                                     href="${pageContext.request.contextPath}/controller?
                                     recordsPerPage=${recordsPerPage}&current_page=${currentPage+1}&command=display_lifehacks_by_type&type=${type}&category=${category}">Next</a>
            </li>
        </c:if>
    </ul>
</nav>
</body>
</html>
