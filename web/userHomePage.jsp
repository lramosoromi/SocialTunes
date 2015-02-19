<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="user" scope="session" class="Entity.User"/>
<jsp:useBean id="userDAO" scope="session" class="EntityDAO.UserDAO"/>
<jsp:useBean id="songDAO" scope="session" class="EntityDAO.SongDAO"/>
<jsp:useBean id="searchParameter" scope="session" class="java.lang.String"/>
<%--
  Created by IntelliJ IDEA.
  User: skylight
  Date: 07/04/14
  Time: 12:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="savePlaylist.jsp"%>
<%@include file="userSettings.jsp"%>
<%@include file="searchResults.jsp"%>
<html>
    <head>
        <title> User Home Page </title>
        <link rel="stylesheet" href="css/bootstrap.min.css">
        <script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script type="text/javascript" src="js/userHomePage.js"></script>
        <script type="text/javascript" src="js/bootsrap.file-input.js"></script>
    </head>
    <body>

    <nav class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <div class="navbar-header">
                <a class="navbar-brand">Welcome: ${ user.getName() } ${ user.getLastName() }</a>
            </div>
            <div id="navbar" class="navbar-collapse collapse">
                <ul class="nav navbar-nav">
                    <li class="active"><a href="#">Home</a></li>
                </ul>
                <form class="navbar-form navbar-left" role="search" action="SearchServlet" method="get">
                    <div class="input-group">
                        <input type="text" class="form-control input-sm" placeholder="Search" name="search" id="srch-term">
                        <label id="friendError" style="color: red"> ${ friendError } </label>
                            <span class="input-group-btn">
                                <button class="btn btn-primary btn-md" type="submit">
                                    <span class="glyphicon glyphicon-search"></span>
                                </button>
                            </span>
                    </div>
                </form>
                <ul class="nav navbar-nav navbar-right">
                    <li><a href="#" data-toggle="modal" data-target="#userSettingsModal" style="color:#28a4c9"> Settings </a></li>
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
                <label>Upload Song:</label>
                <form enctype = "multipart/form-data" action = "UploadMusicServlet" method = "POST">
                    <div class="input-prepend">
                        <input id="fileupload" class="btn btn-primary" title="Browse files" type="file" name="file"/>
                        <span class="input-prepend-btn">
                            <button class="btn btn-primary btn-sm btn-success" type="submit">
                                <span class="glyphicon glyphicon-cloud-upload"></span>
                                <span>Upload</span>
                            </button>
                        </span>
                    </div>
                </form>
                <label id="uploadSongError" style="color: red"> ${ uploadSongErrorMessage } </label>
            </div>

            <div id="cwrap">
                <div id="nowPlay">
                    <label id="npAction">Paused:</label>
                    <div id="npTitle"></div>
                </div>
                <div id="audiowrap">
                    <div id="audio0">
                        <audio id="audio1" controls="controls">
                            Your browser does not support the HTML5 Audio Tag.
                        </audio>
                    </div>
                    <br/>
                    <div id="extraControls">
                        <label>Playlist</label>
                        <br/>
                        <a href="#" id="btnPrev" class="btn ctrlbtn btn-lg">
                            <span class="glyphicon glyphicon-step-backward"></span>
                        </a>
                        <a href="#" id="btnNext" class="btn ctrlbtn btn-lg">
                            <span class="glyphicon glyphicon-step-forward"></span>
                        </a>
                        <a href="#" id="btnSave" class="btn" data-toggle="modal" data-target="#savePlaylistModal" onclick="savePlaylist()">
                            <span class="glyphicon glyphicon-floppy-save"></span>Save</a>
                        <a href="#" id="btnErase" class="btn">
                            <span class="glyphicon glyphicon-trash"></span>Remove</a>
                    </div>
                </div>
                <div id="plwrap">
                    <div class="row" id="plHeader">
                        <div class="col-md-2">Title</div>
                        <div class="col-md-2">Artist</div>
                        <div class="col-md-2">Album</div>
                        <div class="col-md-2">Genre</div>
                        <div class="col-md-2">Duration</div>
                    </div>
                    <ul id="plUL" class="container list-unstyled">
                    </ul>
                </div>
            </div>
        </div>


        <div class="container">
            <ul class="nav nav-tabs">
                <li class="nav active"><a href="#A" data-toggle="tab">Trends</a></li>
                <li class="nav"><a href="#B" data-toggle="tab">Songs</a></li>
                <li class="nav"><a href="#C" data-toggle="tab">Friends</a></li>
                <li class="nav"><a href="#D" data-toggle="tab">Lists</a></li>
            </ul>
            <br/>
            <!-- Tab panes -->
            <div class="tab-content">
                <div class="tab-pane fade in active" id="A">
                    <div id="recentlyListened">
                        Recently listened:
                        <c:forEach var="song" items="${ userDAO.listLastReproducedSongs(user.getUsername()) }" varStatus="index">
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
                    <div id="recommendedSongs">
                        Recommended to you:
                        <c:forEach var="song" items="${ userDAO.getRecommendedSongs(user.getUsername()) }" varStatus="index">
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
                    <div id="songTrends">
                        Songs:
                        <c:forEach var="song" items="${ songDAO.getTrendSongs() }" varStatus="index">
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
                        <c:forEach var="artist" items="${ songDAO.getTrendArtists() }" varStatus="index">
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
                        <c:forEach var="genre" items="${ songDAO.getTrendGenre() }" varStatus="index">
                            <c:out value="${ genre }"/>
                            <c:choose>
                                <c:when test="${index.isLast()}"> </c:when>
                                <c:otherwise>
                                    -
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </div>
                </div>
                <div class="tab-pane fade" id="B">
                    <div id="songsList">
                        <c:forEach var="song" items="${userDAO.listSongs(user.getUsername())}">
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
                            <a href="#" class="btn btn-primary btn-sm"
                                   id="${ song.getTitle() },${ song.getArtist() },${ song.getAlbum() },${ song.getGenre() },${ song.getDuration() }"
                                   onclick="addSong(this.id)">
                                <span class="glyphicon glyphicon-play"></span>
                            </a>
                            <br/>
                        </c:forEach>
                    </div>
                </div>
                <div class="tab-pane fade" id="C">
                    <div id="friendsList">
                        <c:forEach var="user" items="${ userDAO.listFriends(user.getUsername()) }">
                            <c:out value="${ user.getName()}"/>
                            <c:out value="${ user.getLastName()}"/>
                            <a href="http://localhost:8080/AccessFriendPageServlet?<c:out value="${ user.getName()}"/>"
                               class="btn btn-primary btn-sm">
                                Visit Page
                            </a>
                            <br/>
                        </c:forEach>
                    </div>
                </div>
                <div class="tab-pane fade" id="D">
                    <div id="listsList">
                        <c:forEach var="playlist" items="${ userDAO.listPlaylists(user.getUsername()) }">
                            <label> <c:out value="${ playlist.getPlaylistName()}"/> </label>
                            <a id="${ playlist.getPlaylistName() }" href="#" class="btn btn-primary btn-sm" onclick="loadPlaylist(this.id)">
                                <span class="glyphicon glyphicon-play"></span>
                            </a>
                            <a id="delete${ playlist.getPlaylistName() }" href="#" class="btn btn-primary btn-sm" onclick="deletePlaylist(this.id)">
                                <span class="glyphicon glyphicon-trash"></span>
                            </a>
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
