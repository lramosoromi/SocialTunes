package Servlet;

import Entity.User;
import EntityDAO.SongDAO;
import EntityDAO.UserDAO;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by skylight on 07/04/14.
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws javax.servlet.ServletException if a servlet-specific error occurs
     * @throws java.io.IOException if an I/O error occurs
     */

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UserDAO userDAO = new UserDAO();
        SongDAO songDAO = new SongDAO();

        //Assign variables from the request parameters
        String loginFormUsername = request.getParameter("username");
        String loginFormPassword = request.getParameter("password");
        User user = userDAO.getUser(loginFormUsername);

        if (user == null){
            request.getSession().removeAttribute("passwordErrorMessage");
            request.getSession().setAttribute("usernameErrorMessage", "Username is non existent");
        // get back to the referer page using redirect
            response.sendRedirect(request.getHeader("Referer"));
        }
        else if (!loginFormPassword.equals(user.getPassword())){
            request.getSession().removeAttribute("usernameErrorMessage");
            request.getSession().setAttribute("passwordErrorMessage", "Password is incorrect for this user");
        // get back to the referer page using redirect
            response.sendRedirect(request.getHeader("Referer"));
        }
        else {
            HttpSession session = request.getSession();
            session.removeAttribute("usernameErrorMessage");
            session.removeAttribute("passwordErrorMessage");
            session.setAttribute("user", user);
            session.setAttribute("userDAO", userDAO);
            session.setAttribute("songDAO", songDAO);
            request.getRequestDispatcher("/userHomePage.jsp").forward(request, response);
        }
    }
}