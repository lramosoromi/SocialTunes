package Servlet;

import Entity.Song;
import Entity.User;
import EntityDAO.UserDAO;
import com.google.gson.Gson;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by skylight on 15/07/14.
 */
@WebServlet(name = "LoadPlaylistServlet", urlPatterns = "/LoadPlaylistServlet")
public class LoadPlaylistServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        UserDAO userDAO = (UserDAO)req.getSession().getAttribute("userDAO");
        User user = (User)req.getSession().getAttribute("user");
        String playlistName = req.getParameter("playlistName");
        ArrayList<String> songs = new ArrayList<>();

        for (Song song : userDAO.listSongsOfPlaylist(user.getUsername(), playlistName)){
            songs.add(song.getTitle() + "," + song.getArtist() + "," + song.getAlbum() + "," + song.getGenre() + "," + song.getDuration());
        }
        String json = new Gson().toJson(songs);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(json);
    }
}
