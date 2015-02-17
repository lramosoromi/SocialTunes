<%--
  Created by IntelliJ IDEA.
  User: skylight
  Date: 15/07/14
  Time: 15:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- Modal -->
<div class="modal fade" id="savePlaylistModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">Save Playlist</h4>
            </div>
            <div class="modal-body">
                <form action="SavePlaylistServlet" method="POST">
                    Playlist Name:
                    <label> <input name="playlistName"/> </label>
                    <label id="playlistNameError" style="color: red"> ${ playlistNameErrorMessage } </label>
                    <br/>
                    <input type="Submit" value="Save"/>
                </form>
            </div>
        </div>
    </div>
</div>