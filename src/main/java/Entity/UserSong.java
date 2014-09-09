package Entity;

import javax.persistence.*;

/**
 * Created by skylight on 16/07/14.
 */
@Entity
@Table (name = "USER_SONG_TABLE")
public class UserSong {

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    @JoinColumn(name = "username")
    private User user;

    @ManyToOne
    @JoinColumn(name = "songId")
    private Song song;

    @Column(name = "reproductions")
    private int reproductions;

    public UserSong() { }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Song getSong() { return song; }
    public void setSong(Song song) { this.song = song; }

    public int getReproductions() { return reproductions; }
    public void setReproductions(int reproductions) { this.reproductions = reproductions; }
    public void addReproduction() { this.reproductions = this.reproductions + 1; }
}
