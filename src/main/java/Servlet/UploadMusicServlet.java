package Servlet;

import Entity.Song;
import Entity.User;
import EntityDAO.SongDAO;
import EntityDAO.UserDAO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.sql.SQLException;

/**
 * Created by skylight on 18/04/14.
 */
@WebServlet(name = "UploadMusicServlet", urlPatterns = {"/UploadMusicServlet"})
@MultipartConfig
public class UploadMusicServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        SongDAO songDAO = (SongDAO)request.getSession().getAttribute("songDAO");
        UserDAO userDAO = (UserDAO)request.getSession().getAttribute("userDAO");
        File file;

        // Get file part using HttpServletRequest’s getPart() method
        Part filePart = request.getPart("file");
        // Extract file name from content-disposition header of file part
        String fileName = getFileName(filePart);

        System.out.println("***** fileName: " + fileName);
        file = part2file(filePart, fileName);

        try {
            Song song = songDAO.addSong(file);
            User user = (User) request.getSession().getAttribute("user");
            userDAO.addSong(user.getUsername(), song);
            request.removeAttribute("uploadSongErrorMessage");
            request.getRequestDispatcher("/userHomePage.jsp").forward(request, response);
        } catch (SQLException | TagException | ReadOnlyFileException | CannotReadException | InvalidAudioFrameException e) {
            request.setAttribute("uploadSongErrorMessage", "File type is not supported");
            request.getRequestDispatcher("/userHomePage.jsp").forward(request, response);
        }
    }

    // Extract file name from content-disposition header of file part
    private String getFileName(Part part) {
        final String partHeader = part.getHeader("content-disposition");
        System.out.println("***** partHeader: " + partHeader);
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf('=') + 1).trim()
                        .replace("\"", "");
            }
        }
        return null;
    }

    private File part2file(Part filePart, String fileName) throws IOException {

        String basePath = "C:" + File.separator + "temp" + File.separator;

        // Copy input file to destination path
        InputStream inputStream = null;
        OutputStream outputStream = null;
        File outputFilePath = null;
        try {
            outputFilePath = new File(basePath + fileName);
            System.out.println("***** outputFilePath: " + outputFilePath.getAbsolutePath());
            inputStream = filePart.getInputStream();
            outputStream = new FileOutputStream(outputFilePath);

            int read;
            final byte[] bytes = new byte[1024];
            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }

        } catch (FileNotFoundException fne) {
            fne.printStackTrace();
            System.out.println("File Upload Failed!!");
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return outputFilePath;
    }
}