<%@ page import="EntityDAO.SongDAO" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: skylight
  Date: 07/04/14
  Time: 12:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Home Page</title>
        <% SongDAO songDAO = new SongDAO(); %>
    </head>
    <body>
        <form action="LoginServlet" method="POST">
            <label>
                <input id="username" name="username" placeholder="Username"/>
            </label>
            <label>
                <input type="password" name="password" placeholder="Password"/>
            </label>
            <input type="Submit" value="Log in"/>
        </form>
        ${ usernameErrorMessage }
        ${ passwordErrorMessage }
        <a href="createUser.jsp"> Create User </a>
        <br/>
        <h1>Social Tunes</h1>
        <br/>
        <br/>
        Trends
        <br/>
        <div id="songTrends">
            Songs:
            <c:forEach var="song" items="<%= songDAO.getTrendSongs() %>" varStatus="index">
                <c:out value="${ song.getTitle() }"/>
                <c:choose>
                    <c:when test="${index.isLast()}"> </c:when>
                    <c:otherwise>
                        -
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
        <br/>
        <div id="artistTrends">
            Artists:
            <c:forEach var="artist" items="<%= songDAO.getTrendArtists()%>" varStatus="index">
                <c:out value="${ artist }"/>
                <c:choose>
                    <c:when test="${index.isLast()}"> </c:when>
                    <c:otherwise>
                        -
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
        <br/>
        <div id="genreTrends">
            Genre:
            <c:forEach var="genre" items="<%= songDAO.getTrendGenre()%>" varStatus="index">
                <c:out value="${ genre }"/>
                <c:choose>
                    <c:when test="${index.isLast()}"> </c:when>
                    <c:otherwise>
                        -
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
    </body>
</html>
