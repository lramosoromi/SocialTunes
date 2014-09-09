<%--
  Created by IntelliJ IDEA.
  User: skylight
  Date: 15/07/14
  Time: 15:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title> Save Playlist </title>
    </head>
    <body>
    <form action="SavePlaylistServlet" method="POST">
        Playlist Name:
        <label> <input name="playlistName"/> </label>
        ${ nameError }
        <br/>
        <input type="Submit" value="Save"/>
    </form>
    </body>
</html>
