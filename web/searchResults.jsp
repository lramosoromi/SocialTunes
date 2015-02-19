<%--
  Created by IntelliJ IDEA.
  User: skylight
  Date: 19/02/2015
  Time: 02:05 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- Modal -->
<div class="modal fade" id="searchResultsModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">Search Results</h4>
      </div>
      <div class="modal-body">
        <label id="introSearch">Search results for: </label>
        <label id="searchParameter">${ searchParameter }</label>
        <div id="songsFound">
          <h6>Songs:</h6>
          <c:forEach var="song" items="${ userDAO.getSongsFound() }" varStatus="index">
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
        <div id="friendsFound">
          <h6>Friends:</h6>
          <c:forEach var="user" items="${ userDAO.getUsersFound() }">
            <c:out value="${ user.getName()}"/>
            <c:out value="${ user.getLastName()}"/>
            <a href="http://localhost:8080/AccessFriendPageServlet?<c:out value="${ user.getName()}"/>" class="btn btn-primary btn-sm">
              Visit Page
            </a>
            <br/>
          </c:forEach>
        </div>
        <div id="playlistsFound">
          <h6>Playlists:</h6>
          <c:forEach var="playlist" items="${ userDAO.getPlaylistsFound() }">
            <label> <c:out value="${ playlist.getPlaylistName()}"/> </label>
            <a id="${ playlist.getPlaylistName() }" href="#" class="btn btn-primary btn-sm" onclick="loadPlaylist(this.id)">
              <span class="glyphicon glyphicon-play"></span>
            </a>
            <br/>
          </c:forEach>
        </div>
      </div>
    </div>
  </div>
</div>
