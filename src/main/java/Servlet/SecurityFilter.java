package Servlet;

import Entity.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by skylight on 07/04/14.
 */
public class SecurityFilter implements Filter {

    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void destroy(){
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException
    {
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpServletRequest req = (HttpServletRequest) request;
        String servletPath = req.getServletPath();

        //Allow acces to index page
        if (servletPath.equals("/index.jsp")){
            chain.doFilter(req, resp);
            return;
        }
        if (servletPath.equals("/LoginServlet")){
            chain.doFilter(req, resp);
            return;
        }
        // Allow access to create user functionality.
        if (servletPath.equals("/createUser.jsp")){
            chain.doFilter(req, resp);
            return;
        }
        if (servletPath.equals("/CreateNewUserServlet")){
            chain.doFilter(req, resp);
            return;
        }

        // All other functionality requires authentication.
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if (user != null){
            // User is logged in.
            chain.doFilter(req, resp);
            return;
        }

        // Request is not authorized.
        resp.sendRedirect("/index.jsp");
    }
}