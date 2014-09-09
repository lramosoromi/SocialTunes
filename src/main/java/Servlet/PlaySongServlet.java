package Servlet;

import Entity.Song;
import Entity.User;
import EntityDAO.SongDAO;
import EntityDAO.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSessionBindingEvent;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;

/**
 * Created by skylight on 11/06/14.
 */
@WebServlet(name = "PLaySongServlet", urlPatterns = {"/PlaySongServlet"})
public class PlaySongServlet extends HttpServlet {


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        UserDAO userDAO = (UserDAO)request.getSession().getAttribute("userDAO");
        User user = (User)request.getSession().getAttribute("user");
        String songTitle = (String) request.getSession().getAttribute("songTitle");

        if (songTitle != null){
            Song song = userDAO.getSongOfUser(user.getUsername(), songTitle);

            int blobLength = 0;
            try {
                blobLength = (int) song.getData().length();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            byte[] blobAsBytes = new byte[0];
            try {
                blobAsBytes = song.getData().getBytes(1, blobLength);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            //set response headers
            response.setHeader("Content-Transfer-Encoding", "binary");
            response.setHeader("Content-Type", "audio/mpeg");
            response.setHeader("Expires", "0");
            response.setHeader("Cache-Control", "public, must-revalidate, max-age=0");
            response.setHeader("Content-Length", String.valueOf(blobAsBytes.length));
            response.setHeader("Content-Disposition", "inline; filename=" + songTitle + ".mp3");
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Content-Range", "bytes " + 0 + "-" + (blobAsBytes.length-1) + "/" + blobAsBytes.length);
            response.setHeader("Accept-Ranges", "bytes");
            response.setStatus(206);

            try (OutputStream outputStream = response.getOutputStream()) {
                do {
                    if (blobAsBytes.length == -1)
                        break;
                    outputStream.write(blobAsBytes, 0, blobAsBytes.length);
                    outputStream.flush();
                } while (true);
            } catch (Exception ignored) {
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String songTitle = req.getParameter("songTitle");
        if (songTitle != null){
            req.getSession().setAttribute("songTitle", songTitle);
        }
    }
}