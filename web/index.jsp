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
        <link rel="stylesheet" href="css/bootstrap.min.css">
        <script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <% SongDAO songDAO = new SongDAO(); %>
    </head>
    <body>

    <nav class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand">Social Tunes</a>
            </div>
            <div id="navbar" class="navbar-collapse collapse">
                <form class="navbar-form navbar-right" action="LoginServlet" method="POST">
                    <div class="form-group">
                        <input type="text" class="form-control" id="username" name="username" placeholder="Username">
                        <label style="color: red"> ${ usernameErrorMessage } </label>
                    </div>
                    <div class="form-group">
                        <input class="form-control" type="password" name="password" placeholder="Password">
                        <label style="color: red"> ${ passwordErrorMessage } </label>
                    </div>
                    <button type="submit" class="btn btn-success">Log in</button>
                    <a href="createUser.jsp"><span class="glyphicon glyphicon-user"></span> Sign Up</a>
                </form>
            </div><!--/.navbar-collapse -->
        </div>
    </nav>

    <div class="jumbotron">
        <div class="container">
            <h1>Social Tunes</h1>
            <p>Manage your music and checkout whats trending in the world!!</p>
        </div>
    </div>

    <div class="container">
        <h2>Trending:</h2>
        <!-- Example row of columns -->
        <div class="row">
            <div class="col-md-4">
                <h2>Songs</h2>
                <div id="songTrends">
                    <c:forEach var="song" items="<%= songDAO.getTrendSongs() %>" varStatus="index">
                        <c:out value="${ song.getTitle() }"/>
                        <br/>
                    </c:forEach>
                </div>
            </div>
            <div class="col-md-4">
                <h2>Artists</h2>
                <div id="artistTrends">
                    <c:forEach var="artist" items="<%= songDAO.getTrendArtists()%>" varStatus="index">
                        <c:out value="${ artist }"/>
                        <br/>
                    </c:forEach>
                </div>
            </div>
            <div class="col-md-4">
                <h2>Genre</h2>
                <div id="genreTrends">
                    <c:forEach var="genre" items="<%= songDAO.getTrendGenre()%>" varStatus="index">
                        <c:out value="${ genre }"/>
                        <br/>
                    </c:forEach>
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
