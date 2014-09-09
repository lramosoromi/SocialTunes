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
 * Created by skylight on 16/07/14.
 */
@WebServlet(name = "AddReproductionServlet", urlPatterns = "/AddReproductionServlet")
public class AddReproductionServlet extends HttpServlet{

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UserDAO userDAO = (UserDAO)request.getSession().getAttribute("userDAO");
        User user = (User)request.getSession().getAttribute("user");
        String songTitle = request.getParameter("songTitle");

        userDAO.addReproduction(user.getUsername(), songTitle);
    }
}
