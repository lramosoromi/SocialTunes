package Servlet;

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
 * Created by skylight on 19/02/2015.
 */
@WebServlet(name = "AddSongServlet", urlPatterns = {"/AddSongServlet"})
public class AddSongServlet extends HttpServlet{

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UserDAO userDAO = (UserDAO)request.getSession().getAttribute("userDAO");
        SongDAO songDAO = (SongDAO)request.getSession().getAttribute("songDAO");
        User activeUser = (User)request.getSession().getAttribute("user");
        String songName = (String)request.getSession().getAttribute("songName");

        Song song = songDAO.getSong(songName);
        userDAO.addSong(activeUser.getUsername(), song);
        response.sendRedirect("/userHomePage.jsp");
    }
}
