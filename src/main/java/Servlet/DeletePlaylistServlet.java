package Servlet;

import Entity.Playlist;
import Entity.Song;
import Entity.User;
import EntityDAO.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by skylight on 17/02/2015.
 */
@WebServlet(name = "DeletePlaylistServlet", urlPatterns = {"/DeletePlaylistServlet"})
public class DeletePlaylistServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        UserDAO userDAO = (UserDAO)request.getSession().getAttribute("userDAO");
        Playlist playlist = new Playlist();
        String[] songs = (String[]) request.getSession().getAttribute("playlist");
        String playlistName = request.getParameter("playlistName");
        User user = (User) request.getSession().getAttribute("user");
        boolean error = false;

        if (user.getPlaylists().isEmpty()) {
            error = false;
        }
        else {
            for (Playlist playlist1 : user.getPlaylists()){
                if (playlist1.getPlaylistName().equalsIgnoreCase(playlistName)){
                    request.getSession().setAttribute("playlistNameErrorMessage", "Playlist name already exists for another playlist");
                    error = true;
                }
            }
        }
        if (!error) {
            request.getSession().removeAttribute("playlistNameErrorMessage");
            playlist.setPlaylistName(playlistName);
            if (songs.length != 0) {
                for(String aSongName : songs){
                    Song song = userDAO.getSongOfUser(user.getUsername(), aSongName);
                    playlist.addSong(song);
                }
                userDAO.addPlaylist(user.getUsername(), playlist);
            }
            request.getRequestDispatcher("/userHomePage.jsp").forward(request, response);
        }
        else {
            request.getRequestDispatcher("/userHomePage.jsp").forward(request, response);
        }
    }
}
