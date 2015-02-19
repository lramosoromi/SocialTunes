package Servlet;

import Entity.Playlist;
import Entity.User;
import EntityDAO.UserDAO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by skylight on 17/02/2015.
 */
@WebServlet(name = "DeletePlaylistServlet", urlPatterns = {"/DeletePlaylistServlet"})
public class DeletePlaylistServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        UserDAO userDAO = (UserDAO)request.getSession().getAttribute("userDAO");
        String playlistName = request.getParameter("playlistName");
        User user = (User) request.getSession().getAttribute("user");
        Set<Playlist> playlistSet = new HashSet<>(0);

        ArrayList<Playlist> playlists = userDAO.listPlaylists(user.getUsername());
        if (!playlists.isEmpty()) {
            for (Playlist playlist : playlists) {
                if (!playlist.getPlaylistName().equals(playlistName)) {
                    playlistSet.add(playlist);
                }
            }
            userDAO.deleteAllPlaylist(user.getUsername());
            userDAO.setPlaylistSet(user.getUsername(), playlistSet);
        }
    }
}
