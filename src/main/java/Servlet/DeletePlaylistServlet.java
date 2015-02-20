package Servlet;

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
        String playlistName = request.getParameter("playlistName");
        User user = (User) request.getSession().getAttribute("user");

        userDAO.deletePlaylist(user.getUsername(), playlistName);
        response.setContentType("text/html");
        response.addHeader("Refresh", "1");
        response.sendRedirect("/userHomePage.jsp");
    }
}
