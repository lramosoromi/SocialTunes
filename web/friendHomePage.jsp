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
        <link rel="stylesheet" href="css/bootstrap.min.css">
        <script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script type="text/javascript" src="js/friendHomePage.js"></script>
        <script type="text/javascript" src="js/bootsrap.file-input.js"></script>
    </head>
    <body>
    <nav class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <div class="navbar-header">
                <a class="navbar-brand">${ friend.getName() } ${ friend.getLastName() } Page</a>
            </div>
            <div id="navbar" class="navbar-collapse collapse">
                <ul class="nav navbar-nav navbar-right">
                    <li><a href="userSettings.jsp" style="color:#28a4c9"> Settings </a></li>
                    <li><a href="http://localhost:8080/LogoutServlet" style="color:#28a4c9">
                        <span class="glyphicon glyphicon-log-out"></span>
                        Logout
                    </a></li>
                </ul>
            </div>
        </div>
    </nav>
    <br/>
    <div class="container">
        <div class="jumbotron">
            <div class="container">
                <form action="AddFriendServlet" method="post">
                    <button type="submit" class="btn btn-primary btn-md">
                        <span class="glyphicon glyphicon-plus"></span>
                        Add as Friend
                    </button>
                </form>
            </div>
        </div>

        <div class="container">
            <ul class="nav nav-tabs">
                <li class="nav active"><a href="#A" data-toggle="tab">Songs</a></li>
                <li class="nav"><a href="#B" data-toggle="tab">Friends</a></li>
                <li class="nav"><a href="#C" data-toggle="tab">Lists</a></li>
            </ul>
            <br/>
            <!-- Tab panes -->
            <div class="tab-content">
                <div class="tab-pane fade in active" id="A">
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
                            Duration:
                            <c:out value="${ song.getDuration() }"/>
                            <a href="http://localhost:8080/AddSongServlet" class="btn btn-primary btn-sm" id="${ song.getTitle() }" onclick="setSongTitle(this.id)">
                                <span class="glyphicon glyphicon-plus"></span>
                                Add song
                            </a>
                            <br/>
                        </c:forEach>
                    </div>
                </div>
                <div class="tab-pane fade" id="B">
                    <div id="friends">
                        <c:forEach var="user" items="${ userDAO.listFriends(friend.getUsername()) }">
                            <c:out value="${ user.getName()}"/>
                            <c:out value="${ user.getLastName()}"/>
                            <br/>
                        </c:forEach>
                    </div>
                </div>
                <div class="tab-pane fade" id="C">
                    <div id="lists">
                        <c:forEach var="playlist" items="${ userDAO.listPlaylists(friend.getUsername()) }">
                            <c:out value="${ playlist.getPlaylistName()}"/>
                            <br/>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>

        <hr>

        <footer>
            <p>&copy; Lucas Ramos 2015</p>
        </footer>
    </div>
    </body>
</html>
