<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="friend" scope="session" class="Entity.User"/>
<jsp:useBean id="userDAO" scope="session" class="EntityDAO.UserDAO"/>
<%--
  Created by IntelliJ IDEA.
  User: skylight
  Date: 06/06/14
  Time: 12:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title> Friend Home Page </title>
        <link type="text/css" href="css/tabs.css" rel="stylesheet">
    </head>
    <body>
        ${ friend.getName() }
        ${ friend.getLastName() }
        <br/>
        <br/>
        <a href="http://localhost:8080/LogoutServlet"> Logout </a>
        <br/>
        <form action="AddFriendServlet" method="post">
            <input type="submit" value="AddFriend">
        </form>
        <br/>
        <div class="tabs">
            <div class="tab">
                <input type="radio" id="tab-1" name="tab-group-1" checked>
                <label for="tab-1">Songs</label>
                <div class="content">
                    <div id="songs">
                        <c:forEach var="song" items="${userDAO.listSongs(friend.getUsername())}">
                            Title:
                            <c:out value="${ song.getTitle() }" />
                            Artist:
                            <c:out value="${ song.getArtist() }" />
                            Album:
                            <c:out value="${ song.getAlbum() }" />
                            Genre:
                            <c:out value="${ song.getGenre() }"/>
                            <br/>
                        </c:forEach>
                    </div>
                </div>
            </div>
            <div class="tab">
                <input type="radio" id="tab-2" name="tab-group-1">
                <label for="tab-2">Friends</label>
                <div class="content">
                    <div id="friends">
                        <c:forEach var="user" items="${ userDAO.listFriends(friend.getUsername()) }">
                            <c:out value="${ user.getUsername()}"/>
                            <c:out value="${ user.getName()}"/>
                            <c:out value="${ user.getLastName()}"/>
                        </c:forEach>
                    </div>
                </div>
            </div>
            <div class="tab">
                <input type="radio" id="tab-3" name="tab-group-1">
                <label for="tab-3">Lists</label>
                <div class="content">
                    <div id="lists">
                        <c:forEach var="playlist" items="${ userDAO.listPlaylists(friend.getUsername()) }">
                            <c:out value="${ playlist.getPlaylistName()}"/>
                            <br/>
                        </c:forEach>
                    </div>
                </div>
            </div>
         </div>
    </body>
</html>
