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
import java.util.Iterator;

/**
 * Created by skylight on 08/04/14.
 */
@WebServlet(name = "CreateNewUserServlet", urlPatterns = {"/CreateNewUserServlet"})
public class CreateNewUserServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UserDAO userDAO = new UserDAO();
        boolean error = false;

        String name = request.getParameter("name");
        String last_name = request.getParameter("last_name");
        String email = request.getParameter("email");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        if (name.equals("") || last_name.equals("") || email.equals("") || username.equals("") || password.equals("") ||
                confirmPassword.equals("")){
            request.getSession().setAttribute("emptyInputErrorMessage", "All the fields should be completed");
            request.getSession().removeAttribute("emailErrorMessage");
            request.getSession().removeAttribute("usernameErrorMessage");
            request.getSession().removeAttribute("passwordErrorMessage");
            request.getSession().removeAttribute("passwordEqualErrorMessage");
            error = true;
        }
        ArrayList users = userDAO.listUsers();
        for (Object user1 : users) {
            User user = (User) user1;
            if (user.getEmail().equals(email)){
                request.getSession().setAttribute("emailErrorMessage", "The email is already associated to another user");
                request.getSession().removeAttribute("emptyInputErrorMessage");
                request.getSession().removeAttribute("usernameErrorMessage");
                request.getSession().removeAttribute("passwordErrorMessage");
                request.getSession().removeAttribute("passwordEqualErrorMessage");
                error = true;
            }
            else if (user.getUsername().equals(username)){
                request.getSession().setAttribute("usernameErrorMessage", "The username si already used by another user");
                request.getSession().removeAttribute("emptyInputErrorMessage");
                request.getSession().removeAttribute("emailErrorMessage");
                request.getSession().removeAttribute("passwordErrorMessage");
                request.getSession().removeAttribute("passwordEqualErrorMessage");
                error = true;
            }
            else if (user.getPassword().equals(password)){
                request.getSession().setAttribute("passwordErrorMessage", "The password si already used by another user");
                request.getSession().removeAttribute("emptyInputErrorMessage");
                request.getSession().removeAttribute("emailErrorMessage");
                request.getSession().removeAttribute("usernameErrorMessage");
                request.getSession().removeAttribute("passwordEqualErrorMessage");
                error = true;
            }
        }
        if (!password.equals(confirmPassword)){
            request.getSession().setAttribute("passwordEqualErrorMessage", "The passwords dose not coincide");
            request.getSession().removeAttribute("emptyInputErrorMessage");
            request.getSession().removeAttribute("emailErrorMessage");
            request.getSession().removeAttribute("usernameErrorMessage");
            request.getSession().removeAttribute("passwordErrorMessage");
            error = true;
        }
        if (!error){
            User user = userDAO.addUser(username, password, name, last_name, email);
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            request.getRequestDispatcher("/userHomePage.jsp").forward(request, response);
        }
        else {
            response.sendRedirect(request.getHeader("Referer"));
        }
    }
}
