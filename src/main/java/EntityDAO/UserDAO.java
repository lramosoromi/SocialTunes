package EntityDAO;

import Entity.Playlist;
import Entity.Song;
import Entity.User;
import Entity.UserSong;
import Hibernate.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.*;

/**
 * Created by skylight on 07/04/14.
 */
public class UserDAO {

    /* Method to CREATE a user in the database */
    public User addUser(String username, String password, String name, String lastname, String email){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        User user = new User();
        try{
            tx = session.beginTransaction();
            user.setUsername(username);
            user.setPassword(password);
            user.setName(name);
            user.setLastName(lastname);
            user.setEmail(email);
            session.save(user);
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
        return user;
    }
    /* Method to add a SONG to the user */
    public void addSong(String username, Song song){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        UserSong userSong = new UserSong();
        User user;
        try{
            tx = session.beginTransaction();
            if (this.isUser(session, username)){
                user = (User)session.get(User.class, username);
                user.addUserSong(userSong);
                userSong.setUser(user);
                song.addUserSong(userSong);
                userSong.setSong(song);
                session.save(userSong);
            }
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
    }
    /* Method to add a FRIEND to the user */
    public void addFriend(String username, User friend){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        User user;
        try{
            tx = session.beginTransaction();
            if (this.isUser(session, username)){
                user = (User)session.get(User.class, username);
                user.addFriend(friend);
            }
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
    }
    /* Method to add a PLAYLIST to the user */
    public void addPlaylist(String username, Playlist playlist){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        User user;
        try{
            tx = session.beginTransaction();
            if (this.isUser(session, username)){
                user = (User)session.get(User.class, username);
                user.addPlaylist(playlist);
            }
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
    }
    public void addReproduction(String username, String songTitle){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        User user = null;
        try{
            tx = session.beginTransaction();
            if (this.isUser(session, username)){
                user = (User)session.get(User.class, username);
            }
            assert user != null;
            boolean alreadyListen = false;
            for (UserSong userSong : user.getLastReproducedSongs()){
                if (userSong.getSong().getTitle().compareTo(songTitle) == 0){
                    alreadyListen = true;
                }
            }
            if (!alreadyListen){
                UserSong userSong = null;
                for (UserSong aUserSong : user.getUserSongs()){
                    if (aUserSong.getSong().getTitle().compareTo(songTitle) == 0){
                        userSong = aUserSong;
                    }
                }
                user.addLastReproducedSong(userSong);
            }
            for (UserSong userSong : user.getUserSongs()){
                if (userSong.getSong().getTitle().compareTo(songTitle) == 0){
                    userSong.addReproduction();
                }
            }
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
    }
    /* Method to list the last reproduced songs of a user */
    public ArrayList listLastReproducedSongs(String username){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        ArrayList<Song> songs = new ArrayList<>();
        User user = null;
        try{
            tx = session.beginTransaction();
            if (this.isUser(session, username)){
                user = (User)session.get(User.class, username);
            }
            assert user != null;
            for (UserSong userSong : user.getLastReproducedSongs()){
                songs.add(userSong.getSong());
            }
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
        return songs;
    }
    /* Method to return the recommended songs for a user */
    public ArrayList getRecommendedSongs(String username){
        SongDAO songDAO = new SongDAO();
        boolean songAdded;
        ArrayList<Song> recommendedSongs = new ArrayList<>();
        ArrayList<Song> userSongs = this.listSongs(username);
        ArrayList<String> userTrendArtist = (ArrayList<String>)this.getTrendArtists(username);
        ArrayList<String> userTrendGenre = (ArrayList<String>)this.getTrendGenre(username);
        this.sortSongsByReproductions(userSongs, username);
        ArrayList<Song> allSongs = songDAO.getSortedSongs();
        for (Song aSongFromAllRecords : allSongs){
            songAdded = false;
            if (!this.containsSong(userSongs, aSongFromAllRecords)){
                for (String aTrendArtist : userTrendArtist){
                    if (aSongFromAllRecords.getArtist().compareTo(aTrendArtist) == 0){
                        recommendedSongs.add(aSongFromAllRecords);
                        songAdded = true;
                        break;
                    }
                }
                for (String aTrendGenre : userTrendGenre){
                    if (aSongFromAllRecords.getGenre().compareTo(aTrendGenre) == 0 && !songAdded){
                        recommendedSongs.add(aSongFromAllRecords);
                        songAdded = true;
                        break;
                    }
                }
                if (userTrendArtist.size() <= 3 && userTrendGenre.size() <= 3 && !songAdded){
                    recommendedSongs.add(aSongFromAllRecords);
                }
            }
            if (recommendedSongs.size() >= 10){
                return recommendedSongs;
            }
        }
        return recommendedSongs;
    }
    /* Method to LIST all the users */
    public ArrayList<User> listUsers( ){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        ArrayList<User> users = null;
        try{
            tx = session.beginTransaction();
            users = (ArrayList<User>) session.createQuery("FROM User").list();
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
        return users;
    }
    /* Method to LIST all the SONGS of a user ordered by the reproductions */
    public ArrayList<Song> listSongs(String username){
        Session session = HibernateUtil.getSessionFactory().openSession();
        ArrayList<Song> songs = new ArrayList<>();
        Transaction tx = null;
        User user = null;
        try{
            tx = session.beginTransaction();
            if (this.isUser(session, username)){
                user = (User)session.get(User.class, username);
            }
            assert user != null;
            ArrayList<UserSong> userSongs = new ArrayList<>(user.getUserSongs());
            for (UserSong userSong : userSongs){
                songs.add(userSong.getSong());
            }
            this.sortSongsByReproductions(songs, username);
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
        return songs;
    }

    public void sortSongsByReproductions(ArrayList<Song> songs, final String username){
        songs.sort(new Comparator<Song>() {
            @Override
            public int compare(Song song1, Song song2) {
                int reproductionsSong1 = 0;
                int reproductionsSong2 = 0;
                for (UserSong userSong : song1.getUserSongs()){
                    if (userSong.getUser().getUsername().compareTo(username) == 0){
                        reproductionsSong1 = userSong.getReproductions();
                    }
                }
                for (UserSong userSong : song2.getUserSongs()){
                    if (userSong.getUser().getUsername().compareTo(username) == 0){
                        reproductionsSong2 = userSong.getReproductions();
                    }
                }
                return ((Integer)reproductionsSong1).compareTo(reproductionsSong2);
            }
        });
    }
    /* Method to LIST all the FRIENDS of a user */
    public ArrayList<User> listFriends(String username){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        ArrayList<User> friends = null;
        User user = null;
        try{
            tx = session.beginTransaction();
            if (this.isUser(session, username)){
                user = (User)session.get(User.class, username);
            }
            assert user != null;
            friends = new ArrayList<>(user.getFriends());
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
        return friends;
    }
    /* Method to LIST all the PLAYLISTS of a user */
    public ArrayList<Playlist> listPlaylists(String username){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        ArrayList<Playlist> playlists = null;
        User user = null;
        try{
            tx = session.beginTransaction();
            if (this.isUser(session, username)){
                user = (User)session.get(User.class, username);
            }
            assert user != null;
            playlists = new ArrayList<>(user.getPlaylists());
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
        return playlists;
    }
    public ArrayList<Song> listSongsOfPlaylist(String username, String playlistName){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        ArrayList<Song> songs = new ArrayList<>();
        User user = null;
        try{
            tx = session.beginTransaction();
            if (this.isUser(session, username)){
                user = (User)session.get(User.class, username);
            }
            assert user != null;
            ArrayList<Playlist> playlists = new ArrayList<>(user.getPlaylists());
            for (Playlist playlist1 : playlists){
                if (playlist1.getPlaylistName().compareTo(playlistName) == 0){
                    songs.addAll(playlist1.getSongs());
                }
            }
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
        return songs;
    }
    /* Method to GET a user from the records */
    public User getUser(String username){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        User user = null;
        try{
            tx = session.beginTransaction();
            if (this.isUser(session, username)){
                user = (User)session.get(User.class, username);
            }
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();

        }
        return user;
    }
    /* Method to GET a song from the user records */
    public Song getSongOfUser(String username, String songTitle){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        User user = null;
        Song song = null;
        try{
            tx = session.beginTransaction();
            if (this.isUser(session, username)){
                user = (User)session.get(User.class, username);
            }
            for (UserSong userSong : user.getUserSongs()){
                if (userSong.getSong().getTitle().compareTo(songTitle) == 0){
                    song = userSong.getSong();
                }
            }
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
        return song;
    }
    /* Method to UPDATE password for a user */
    public void updateUserPassword(String username, String password){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        User user;
        try{
            tx = session.beginTransaction();
            user = (User)session.get(User.class, username);
            user.setPassword(password);
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
    }
    /* Method to UPDATE email for a user */
    public void updateUserEmail(String username, String email){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        User user;
        try{
            tx = session.beginTransaction();
            user = (User)session.get(User.class, username);
            user.setPassword(email);
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
    }
    /* Method to DELETE a user from the records */
    public void deleteUser(String username){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        User user;
        try{
            tx = session.beginTransaction();
            if (this.isUser(session, username)){
                user = (User)session.get(User.class, username);
                session.delete(user);
            }
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
    }
    /*Method to CHECK if there is a user on the records */
    private boolean isUser(Session session, String username){
        Transaction tx = null;
        ArrayList<User> users;
        boolean isUser = false;
        try{
            tx = session.getTransaction();
            users = (ArrayList<User>) session.createQuery("FROM User").list();
            for (User user : users){
                if (user.getUsername().compareTo(username) == 0){
                    isUser = true;
                }
            }
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }
        return isUser;
    }
    private List getTrendArtists(String username){
        ArrayList<String> trendArtistSongs = new ArrayList<>();

        int iterations = 0;
        while (iterations < this.listSongs(username).size() || trendArtistSongs.size() > 9){
            for (Song song : this.listSongs(username)) {
                if (trendArtistSongs.size() == 0){
                    trendArtistSongs.add(song.getArtist());
                }
                else {
                    int itLimit = trendArtistSongs.size();
                    for(int i = 0; i < itLimit; i++){
                        if(song.getArtist().compareTo(trendArtistSongs.get(i)) != 0){
                            trendArtistSongs.add(song.getArtist());
                        }
                    }
                }
                iterations ++;
            }
        }
        return trendArtistSongs;
    }

    private List getTrendGenre(String username){
        ArrayList<String> trendGenreSongs = new ArrayList<>();

        int iterations = 0;
        while (iterations < this.listSongs(username).size() || trendGenreSongs.size() > 9){
            for (Song song : this.listSongs(username)) {
                if (trendGenreSongs.size() == 0){
                    trendGenreSongs.add(song.getGenre());
                }
                else {
                    int itLimit = trendGenreSongs.size();
                    for(int i = 0; i < itLimit; i++){
                        if(song.getGenre().compareTo(trendGenreSongs.get(i)) != 0){
                            trendGenreSongs.add(song.getGenre());
                        }
                    }
                }
                iterations ++;
            }
        }
        return trendGenreSongs;
    }

    private boolean containsSong(ArrayList<Song> songs, Song aSong){
        boolean contains = false;
        for (Song song : songs){
            if (aSong.getTitle().compareTo(song.getTitle()) == 0){
                contains = true;
            }
        }
        return contains;
    }
}
