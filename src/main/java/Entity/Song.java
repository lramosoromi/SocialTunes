package Entity;

import javax.persistence.*;
import java.sql.Blob;
import java.util.HashSet;
import java.util.Set;


/**
 * Created by skylight on 16/04/14.
 */
@Entity
@Table (name = "SONG_TABLE")
public class Song {

    @Id
    @Column (name = "title")
    private String title;

    @Column (name = "artist")
    private String artist;

    @Column (name = "album")
    private String album;

    @Column (name = "genre")
    private String genre;

    @Column (name = "duration")
    private int duration;

    @Column (name = "data", length = 200 * 1024 * 1024)
    @Lob
    private Blob data;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "song")
    private Set<UserSong> userSongs = new HashSet<>(0);

    public Song() { }

    public String getTitle() { return title; }
    public void setTitle(String name) { this.title = name; }

    public String getArtist() { return artist; }
    public void setArtist(String artist) { this.artist = artist; }

    public String getAlbum() { return album; }
    public void setAlbum(String album) { this.album = album; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public String getDuration() {
        int songDurationHours = ( duration / 3600 ) % 24;
        int songDurationMinutes = ( duration / 60 ) % 60;
        int songDurationSeconds = duration % 60;
        String durationHours = String.valueOf(songDurationHours);
        String durationMinutes = String.valueOf(songDurationMinutes);
        String durationSeconds = String.valueOf(songDurationSeconds);

        if (songDurationHours <= 9) durationHours = "0" + songDurationHours;
        if (songDurationMinutes <= 9) durationMinutes = "0" + songDurationMinutes;
        if (songDurationSeconds <= 9) durationSeconds = "0" + songDurationSeconds;

        return (durationHours + ":" + durationMinutes + ":" + durationSeconds);
    }
    public void setDuration(int duration) { this.duration = duration; }

    public Blob getData() { return data; }
    public void setData(Blob data) { this.data = data; }

    public Set<UserSong> getUserSongs() { return userSongs; }
    public void setUserSongs(Set<UserSong> userSongs) { this.userSongs = userSongs; }
    public void addUserSong(UserSong userSong) { userSongs.add(userSong); }
}
