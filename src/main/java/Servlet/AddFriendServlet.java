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
 * Created by skylight on 07/06/14.
 */
@WebServlet(name = "AddFriendServlet", urlPatterns = {"/AddFriendServlet"})
public class AddFriendServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UserDAO userDAO = (UserDAO)request.getSession().getAttribute("userDAO");
        User activeUser = (User)request.getSession().getAttribute("user");
        User friend = (User)request.getSession().getAttribute("friend");

        userDAO.addFriend(activeUser.getUsername(), friend);
        request.getRequestDispatcher("/userHomePage.jsp").forward(request, response);
    }
}
