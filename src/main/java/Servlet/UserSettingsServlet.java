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
 * Created by skylight on 14/05/14.
 */
@WebServlet(name = "UserSettingsServlet", urlPatterns = "/UserSettingsServlet")
public class UserSettingsServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UserDAO userDAO = (UserDAO)request.getSession().getAttribute("userDAO");
        User user = (User) request.getSession().getAttribute("user");
        boolean error = false;

        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        ArrayList users = userDAO.listUsers();
        for (Object user1 : users) {
            User aUser = (User) user1;
            if (aUser.getEmail().equals(email)){
                request.getSession().setAttribute("newEmailErrorMessage", "The email is already associated to another user");
                request.getSession().removeAttribute("newPasswordErrorMessage");
                request.getSession().removeAttribute("newPasswordEqualErrorMessage");

                error = true;
            }
            else if (aUser.getPassword().equals(password)){
                request.getSession().setAttribute("newPasswordErrorMessage", "The password si already used by another user");
                request.getSession().removeAttribute("newEmailErrorMessage");
                request.getSession().removeAttribute("newPasswordEqualErrorMessage");
                error = true;
            }
        }
        if (!password.equals(confirmPassword)){
            request.getSession().setAttribute("newPasswordEqualErrorMessage", "The passwords dose not coincide");
            request.getSession().removeAttribute("newEmailErrorMessage");
            request.getSession().removeAttribute("newPasswordErrorMessage");
            error = true;
        }
        if (!error){
            userDAO.updateUserPassword(user.getUsername(), password);
            userDAO.updateUserEmail(user.getUsername(), email);
            request.getRequestDispatcher("/userHomePage.jsp").forward(request, response);
        }
        else {
            request.getRequestDispatcher("/userHomePage.jsp").forward(request, response);
        }
    }
}