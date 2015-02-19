package Entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by skylight on 06/04/14.
 */

@Entity
@Table (name = "USER_TABLE")
public class User {

    @Id
    @Column (name = "username")
    private String username;

    @Column (name = "password")
    private String password;

    @Column (name = "name")
    private String name;

    @Column (name = "lastName")
    private String lastName;

    @Column (name = "email")
    private String email;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    private Set<UserSong> userSongs = new HashSet<>(0);

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "USER_LASTREPSONGS_TABLE", joinColumns = { @JoinColumn(name = "username") },
            inverseJoinColumns = { @JoinColumn(name = "userSongId") })
    private Set<UserSong> lastReproducedSongs = new HashSet<>(10);

    @ManyToMany
    @JoinTable(name="USER_FRIENDS_TABLE", joinColumns={@JoinColumn(name="username")},
            inverseJoinColumns={@JoinColumn(name="friendUsername")})
    private Set<User> friends = new HashSet<>(0);

    @ManyToMany(mappedBy="friends")
    private Set<User> friendsOf = new HashSet<>(0);

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "USER_PLAYLIST", joinColumns = { @JoinColumn(name = "username") },
            inverseJoinColumns = { @JoinColumn(name = "playlistName") })
    private Set<Playlist> playlists = new HashSet<>(0);


    public User(){}

    public String getUsername() { return username; }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) { this.password = password; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastname) { this.lastName = lastname; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Set<UserSong> getUserSongs() { return userSongs; }
    public void setUserSongs(Set<UserSong> userSongs) { this.userSongs = userSongs; }
    public void addUserSong(UserSong userSong) { userSongs.add(userSong); }

    public Set<UserSong> getLastReproducedSongs() { return lastReproducedSongs; }
    public void setLastReproducedSongs(Set<UserSong> lastReproducedSongs) { this.lastReproducedSongs = lastReproducedSongs; }
    public void addLastReproducedSong(UserSong userSong) { lastReproducedSongs.add(userSong); }

    public Set<User> getFriends() { return friends; }
    public void setFriends(Set<User> friends) { this.friends = friends; }
    public void addFriend(User friend) { friends.add(friend); }

    public Set<User> getFriendsOf() { return friendsOf; }
    public void setFriendsOf(Set<User> friendsOf) { this.friendsOf = friendsOf; }

    public Set<Playlist> getPlaylists() { return playlists; }
    public void setPlaylists(Set<Playlist> playLists) { this.playlists = playLists; }
    public void addPlaylist(Playlist playlist) { playlists.add(playlist); }
    public void deleteAllPlaylists() {
        for (Playlist playlist : playlists) {
            playlist.erase();
        }
        playlists.clear();
    }
}