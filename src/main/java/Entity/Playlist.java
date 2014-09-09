package Entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by skylight on 11/07/14.
 */
@Entity
@Table(name = "PLAYLIST_TABLE")
public class Playlist {

    @Id
    @Column(name = "playlistName")
    private String playlistName;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "PLAYLIST_SONG_TABLE", joinColumns = { @JoinColumn(name = "playListName") },
            inverseJoinColumns = { @JoinColumn(name = "songId") })
    private Set<Song> songs = new HashSet<>(0);


    public Playlist(){}

    public String getPlaylistName() { return playlistName; }
    public void setPlaylistName(String playListName) { this.playlistName = playListName; }

    public Set<Song> getSongs() { return songs; }
    public void setSongs(Set<Song> songs) { this.songs = songs; }
    public void addSong(Song song) { songs.add(song); }
    public void removeSong(Song song) { songs.remove(song); }
}
