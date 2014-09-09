import Entity.Song;
import EntityDAO.SongDAO;
import javazoom.jl.player.Player;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;

/**
 * Created by skylight on 08/06/14.
 */
public class PlaySong {

    public static void main(String[] args)
            throws SQLException, IOException, LineUnavailableException, UnsupportedAudioFileException {
        SongDAO songDAO = new SongDAO();
        Song song = songDAO.getSong("Here Without You");
        Blob blob = song.getData();

        int blobLength = (int) blob.length();
        byte[] blobAsBytes = blob.getBytes(1, blobLength);
        InputStream inS = new ByteArrayInputStream(blobAsBytes);

        try{
            Player playMP3 = new Player(inS);
            playMP3.play();
        }
        catch(Exception exc){
            exc.printStackTrace();
            System.out.println("Failed to play the file.");
        }
    }
}
