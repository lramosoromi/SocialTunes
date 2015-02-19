package Servlet;

import Entity.Playlist;
import Entity.Song;
import Entity.User;
import EntityDAO.UserDAO;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by skylight on 19/02/2015.
 */
@WebServlet(name = "SearchServlet", urlPatterns = {"/SearchServlet"})
public class SearchServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UserDAO userDAO = (UserDAO)request.getSession().getAttribute("userDAO");
        User user = (User)request.getSession().getAttribute("user");
        String searchString = request.getParameter("search");

        ArrayList<User> users = userDAO.listUsers();
        ArrayList<User> friendsFound = new ArrayList<>();
        for (User aUser : users){
            if (searchString.equalsIgnoreCase(aUser.getUsername())
                    || searchString.equalsIgnoreCase(aUser.getName() + " " + aUser.getLastName())
                    || searchString.equalsIgnoreCase(aUser.getName())
                    || searchString.equalsIgnoreCase(aUser.getLastName())){
                friendsFound.add(aUser);
            }
        }
        ArrayList<Song> songsFound = new ArrayList<>();
        for (Song song : userDAO.listSongs(user.getUsername())) {
            if (searchString.equalsIgnoreCase(song.getTitle())
                    || song.getTitle().toLowerCase().contains(searchString.toLowerCase())){
                songsFound.add(song);
            }
        }
        ArrayList<Playlist> playlistsFound = new ArrayList<>();
        for (Playlist playlist : userDAO.listPlaylists(user.getUsername())) {
            if (searchString.equalsIgnoreCase(playlist.getPlaylistName())
                    || playlist.getPlaylistName().toLowerCase().contains(searchString.toLowerCase())) {
                playlistsFound.add(playlist);
            }
        }
        HttpSession session = request.getSession();
        userDAO.setSongsFound(songsFound);
        userDAO.setUsersFound(friendsFound);
        userDAO.setPlaylistsFound(playlistsFound);
        session.setAttribute("searchParameter", searchString);
        request.getRequestDispatcher("/userHomePage.jsp").forward(request,response);
    }
}
