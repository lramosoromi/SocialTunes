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
<%@include file="createUser.jsp"%>
<html>
    <head>
        <title>Home Page</title>
        <link rel="stylesheet" href="css/bootstrap.min.css">
        <script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>
        <script type="text/javascript" src="js/bootstrap.min.js"></script>

        <% SongDAO songDAO = new SongDAO(); %>
    </head>
    <body>
    <script type="text/javascript">
        var usernameLogin;
        var passwordLogin;
        var email;
        var usernameSignIn;
        var passwordSignIn;
        var passwordEqual;
        var emptyInput;

        $(document).ready(function() {
            $('.trigger').popover({
                html: true,
                title: function () {
                    return $(this).parent().find('.head').html();
                },
                content: function () {
                    return $(this).parent().find('.content').html();
                }
            });

            usernameLogin = $('#usernameLoginError').text();
            passwordLogin = $('#passwordLoginError').text();
            if(usernameLogin.trim() == "Username is non existent" || passwordLogin.trim() == "Password is incorrect for this user"){
                $('.trigger').popover('show');
            }

            email = $('#emailError').text();
            usernameSignIn = $('#usernameSignInError').text();
            passwordSignIn = $('#passwordSignInError').text();
            passwordEqual = $('#passwordEqualError').text();
            emptyInput = $('#emptyInputError').text();
            if(email.trim() == "The email is already associated to another user" || usernameSignIn.trim() == "The username si already used by another user"
                    || passwordSignIn.trim() == "The password si already used by another user" || passwordEqual.trim() == "The passwords dose not coincide"
                    || emptyInput.trim() == "All the fields should be completed"){
                $('#createUserModal').modal('show');
            }
        });
    </script>

    <nav class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <div class="navbar-header">
                <a class="navbar-brand">Social Tunes</a>
            </div>
            <div id="navbar" class="navbar-collapse collapse">
                <form class="navbar-form navbar-right" action="LoginServlet" method="POST">
                    <div class="popover-markup">
                        <a href="#" class="trigger" data-placement="bottom">
                            <span class="glyphicon glyphicon-log-in"></span>
                            Log in
                        </a>
                        <div class="head hide">
                            Log In
                        </div>
                        <div class="content hide">
                            <div class="form-group">
                                <input type="text" class="form-control" id="username" name="username" placeholder="Username">
                                <label id="usernameLoginError" style="color: red"> ${ usernameLoginErrorMessage } </label>
                            </div>
                            <div class="form-group">
                                <input class="form-control" type="password" name="password" placeholder="Password">
                                <label id="passwordLoginError" style="color: red"> ${ passwordLoginErrorMessage } </label>
                            </div>
                            <button type="submit" class="btn btn-primary">Submit</button>
                        </div>
                        <div class="footer hide">test</div>
                    </div>
                    <a href="#" data-toggle="modal" data-target="#createUserModal">
                        <span class="glyphicon glyphicon-user"></span>
                        Sign Up
                    </a>
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
