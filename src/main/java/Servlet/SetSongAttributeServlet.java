package Servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by skylight on 19/02/2015.
 */
@WebServlet(name = "SetSongAttributeServlet", urlPatterns = {"/SetSongAttributeServlet"})
public class SetSongAttributeServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String songName = request.getParameter("songTitle");

        HttpSession session = request.getSession();
        session.setAttribute("songName", songName);
    }
}
