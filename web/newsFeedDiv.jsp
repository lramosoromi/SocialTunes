<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="userDAO" scope="session" class="EntityDAO.UserDAO"/>
<%--
  Created by IntelliJ IDEA.
  User: skylight
  Date: 19/02/2015
  Time: 06:50 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div>
  <c:forEach var="news" items="${ userDAO.getNewsfeed().getNews() }">
    <c:out value="${ news }"/>
    <br/>
  </c:forEach>
</div>