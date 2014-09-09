package Servlet;

import Entity.Playlist;
import Entity.Song;
import Entity.User;
import EntityDAO.SongDAO;
import EntityDAO.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by skylight on 11/07/14.
 */
@WebServlet(name = "SavePlaylistServlet", urlPatterns = {"/SavePlaylistServlet"})
public class SavePlaylistServlet extends HttpServlet{

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        UserDAO userDAO = (UserDAO)request.getSession().getAttribute("userDAO");
        Playlist playlist = new Playlist();
        String[] songs = (String[]) request.getSession().getAttribute("playlist");
        String playlistName = request.getParameter("playlistName");
        User user = (User) request.getSession().getAttribute("user");

        //ACA!! Nunca abro una session

        for (Playlist playlist1 : user.getPlaylists()){
            if (playlist1.getPlaylistName().equalsIgnoreCase(playlistName)){
                request.getSession().setAttribute("nameError", "Playlist name already exists for another playlist");
                response.sendRedirect(request.getHeader("Referer"));
            }
            else {
                request.getSession().removeAttribute("nameError");
                playlist.setPlaylistName(playlistName);
                if (songs.length != 0) {
                    for(String aSongName : songs){
                        Song song = userDAO.getSongOfUser(user.getUsername(), aSongName);
                        playlist.addSong(song);
                    }
                    userDAO.addPlaylist(user.getUsername(), playlist);
                }
            }
        }
        request.getRequestDispatcher("/userHomePage.jsp").forward(request, response);
    }
}
