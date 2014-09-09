package Servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by skylight on 15/07/14.
 */
@WebServlet(name = "SetPlaylistAttributeServlet", urlPatterns = {"/SetPlaylistAttributeServlet"})
public class SetPlaylistAttributeServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String songs = request.getParameter("songs");

        String[] arrayOfSongs = songs.split(",");
        HttpSession session = request.getSession();
        session.setAttribute("playlist", arrayOfSongs);
    }
}
