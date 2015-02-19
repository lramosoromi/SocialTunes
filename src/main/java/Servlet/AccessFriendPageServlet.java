package Servlet;

import Entity.User;
import EntityDAO.UserDAO;
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
@WebServlet(name = "AccessFriendPageServlet", urlPatterns = {"/AccessFriendPageServlet"})
public class AccessFriendPageServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UserDAO userDAO = (UserDAO) request.getSession().getAttribute("userDAO");
        String searchString = request.getQueryString();

        ArrayList<User> users = userDAO.listUsers();
        User friend = null;
        for (User user : users) {
            if (searchString.equalsIgnoreCase(user.getUsername()) ||
                    searchString.equalsIgnoreCase(user.getName() + " " + user.getLastName())) {
                friend = user;
            }
        }

        HttpSession session = request.getSession();
        if (friend == null) {
            session.setAttribute("friendError", "Friend not found");
            request.getRequestDispatcher("/userHomePage.jsp").forward(request, response);
        } else {
            session.removeAttribute("friendError");
            session.setAttribute("friend", friend);
            request.getRequestDispatcher("/friendHomePage.jsp").forward(request, response);
        }
    }
}