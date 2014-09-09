<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="user" scope="session" class="Entity.User"/>
<jsp:useBean id="userDAO" scope="session" class="EntityDAO.UserDAO"/>
<jsp:useBean id="songDAO" scope="session" class="EntityDAO.SongDAO"/>
<%--
  Created by IntelliJ IDEA.
  User: skylight
  Date: 07/04/14
  Time: 12:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title> User Home Page </title>
        <link type="text/css" href="${pageContext.request.contextPath}/css/player.css" rel="stylesheet" />
        <link type="text/css" href="css/tabs.css" rel="stylesheet">
        <script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>
        <script type="text/javascript" src="js/userHomePage.js"></script>
    </head>
    <body>
        Welcome: ${ user.getName() } ${ user.getLastName() }
        <br/>
        <a href="http://localhost:8080/LogoutServlet"> Logout </a>
        <br/>
        <a href="userSettings.jsp"> Settings </a>
        <br/>
        <div id="cwrap">
            <div id="nowPlay">
                <h3 id="npAction">Paused:</h3>
                <div id="npTitle"></div>
            </div>
            <div id="audiowrap">
                <div id="audio0">
                    <audio id="audio1" controls="controls">
                        Your browser does not support the HTML5 Audio Tag.
                    </audio>
                </div>
                <div id="extraControls">
                    <button id="btnPrev" class="ctrlbtn">|&lt;&lt; Prev Track</button> <button id="btnNext" class="ctrlbtn">Next Track &gt;&gt;|</button>
                </div>
            </div>
            <div id="plwrap">
                <ul id="plUL">
                </ul>
            </div>
            <div id="playlistControls">
                <button id="btnSave" onclick="savePlaylist()">Save Playlist</button>
                <button id="btnErase">Erase Playlist</button>
            </div>
        </div>
        <br/>
        <br/>
        <form action="SearchFriendServlet" method="post">
            Search friend:
            <label> <input name="search"> </label>
            <input type="submit" value="Search">
            <br/>
            ${ friendError }
        </form>
        <form enctype = "multipart/form-data" action = "UploadMusicServlet" method = "POST">
            Upload File:
            <input type = "file" name = "file">
            <input type = "submit" value = "Upload Music">
        </form>
        <div class="tabs">
            <div class="tab">
                <input type="radio" id="tab-1" name="tab-group-1" checked>
                <label for="tab-1">Trends</label>
                <div class="content">
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
            </div>
            <div class="tab">
                <input type="radio" id="tab-2" name="tab-group-1">
                <label for="tab-2">Songs</label>
                <div class="content">
                    <div id="songs">
                        <c:forEach var="song" items="${userDAO.listSongs(user.getUsername())}">
                            Title:
                            <c:out value="${ song.getTitle() }" />
                            Artist:
                            <c:out value="${ song.getArtist() }" />
                            Album:
                            <c:out value="${ song.getAlbum() }" />
                            Genre:
                            <c:out value="${ song.getGenre() }"/>
                            <input type="button" value="Play" id="${ song.getTitle() }" onclick="addSong(this.id)"/>
                            <br/>
                        </c:forEach>
                    </div>
                </div>
            </div>
            <div class="tab">
                <input type="radio" id="tab-3" name="tab-group-1">
                <label for="tab-3">Friends</label>
                <div class="content">
                    <div id="friends">
                        <c:forEach var="user" items="${ userDAO.listFriends(user.getUsername()) }">
                            <c:out value="${ user.getName()}"/>
                            <c:out value="${ user.getLastName()}"/>
                            <br/>
                        </c:forEach>
                    </div>
                </div>
            </div>
            <div class="tab">
                <input type="radio" id="tab-4" name="tab-group-1">
                <label for="tab-4">Lists</label>
                <div class="content">
                    <div id="lists">
                        <c:forEach var="playlist" items="${ userDAO.listPlaylists(user.getUsername()) }">
                            <a id="${ playlist.getPlaylistName() }" href="#" onclick="loadPlaylist(this.id)">
                                <c:out value="${ playlist.getPlaylistName()}"/>
                            </a>
                            <br/>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>
<%--
        Trends:
        <br/>
        <div id="trends">
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
        <br/>
        Songs:
        <br/>
        <div id="songs">
            <c:forEach var="song" items="${userDAO.listSongs(user.getUsername())}">
                Title:
                <c:out value="${ song.getTitle() }" />
                Artist:
                <c:out value="${ song.getArtist() }" />
                Album:
                <c:out value="${ song.getAlbum() }" />
                Genre:
                <c:out value="${ song.getGenre() }"/>
                <input type="button" value="Play" id="${ song.getTitle() }" onclick="addSong(this.id)"/>
                <br/>
            </c:forEach>
        </div>
        <br/>
        Friends:
        <div id="friends">
            <c:forEach var="user" items="${ userDAO.listFriends(user.getUsername()) }">
                <c:out value="${ user.getName()}"/>
                <c:out value="${ user.getLastName()}"/>
                <br/>
            </c:forEach>
        </div>
        <br/>
        Lists:
        <div id="lists">
            <c:forEach var="playlist" items="${ userDAO.listPlaylists(user.getUsername()) }">
                <a id="${ playlist.getPlaylistName() }" href="#" onclick="loadPlaylist(this.id)">
                    <c:out value="${ playlist.getPlaylistName()}"/>
                </a>
                <br/>
            </c:forEach>
        </div>
--%>
    </body>
</html>
