package EntityDAO;

import Entity.Song;
import Hibernate.HibernateUtil;
import org.apache.commons.io.IOUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;

import java.io.*;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by skylight on 16/04/14.
 */
public class SongDAO {

    private ArrayList<Song> sortedSongs;


    public SongDAO(){
        listSongs();
    }

    public ArrayList getSortedSongs(){
        return sortedSongs;
    }
    /* Method to CREATE a song in the database */
    public Song addSong(File file) throws SQLException, IOException, TagException, ReadOnlyFileException,
            CannotReadException, InvalidAudioFrameException {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Song song = new Song();

        AudioFile audioFile = AudioFileIO.read(file);
        AudioHeader header = audioFile.getAudioHeader();
        Tag tag = audioFile.getTag();

        String artist = tag.getFirst(FieldKey.ARTIST);
        String title = tag.getFirst(FieldKey.TITLE);
        String album = tag.getFirst(FieldKey.ALBUM);
        String genre = tag.getFirst(FieldKey.GENRE);
        int duration = header.getTrackLength();

        if (album.equals("")){
            album = "Unknown";
        }
        InputStream inputStream = new FileInputStream(file);
        byte[] bytes = IOUtils.toByteArray(inputStream);

        System.out.println("Artist: " + artist);
        System.out.println("Title: " + title);
        System.out.println("Album: " + album);
        System.out.println("Genre: " + genre);
        System.out.println("Duration: " + duration);
        try{
            tx = session.beginTransaction();
            song.setTitle(title);
            song.setArtist(artist);
            song.setAlbum(album);
            song.setGenre(genre);
            song.setDuration(duration);
            Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);
            song.setData(blob);
            session.save(song);
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
        return song;
    }
    /* Method to LIST all the songs ordered by the appearances in the users lists */
    private void listSongs(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        ArrayList songs = null;
        try{
            tx = session.beginTransaction();
            songs = (ArrayList) session.createQuery("FROM Song").list();
            sortSongsByUsersAppearances(songs);
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
        sortedSongs = songs;
    }

    public void sortSongsByUsersAppearances(ArrayList<Song> songs){
        songs.sort(new Comparator<Song>() {
            @Override
            public int compare(Song song1, Song song2) {
                return ((Integer)song1.getUserSongs().size()).compareTo(song2.getUserSongs().size());
            }
        });
    }

    public List getTrendSongs(){

        if (sortedSongs.size() >= 9){
            return sortedSongs.subList(0, 9);
        }
        else {
            return sortedSongs.subList(0, sortedSongs.size());
        }
    }

    public List getTrendArtists(){
        ArrayList<String> trendArtistSongs = new ArrayList<>();

        int iterations = 0;
        while (iterations < sortedSongs.size() || trendArtistSongs.size() > 9){
            for (Song song : sortedSongs) {
                if (trendArtistSongs.size() == 0){
                    trendArtistSongs.add(song.getArtist());
                }
                else {
                    int itLimit = trendArtistSongs.size();
                    boolean artistAppears = false;
                    for(int i = 0; i < itLimit; i++){
                        if(song.getArtist().compareTo(trendArtistSongs.get(i)) == 0){
                            artistAppears = true;
                        }
                    }
                    if (!artistAppears){
                        trendArtistSongs.add(song.getArtist());
                    }
                }
                iterations ++;
            }
        }
        return trendArtistSongs;
    }

    public List getTrendGenre(){
        ArrayList<String> trendGenreSongs = new ArrayList<>();

        int iterations = 0;
        while (iterations < sortedSongs.size() || trendGenreSongs.size() > 9){
            for (Song song : sortedSongs) {
                if (trendGenreSongs.size() == 0){
                    trendGenreSongs.add(song.getGenre());
                }
                else {
                    int itLimit = trendGenreSongs.size();
                    boolean genreAppears = false;
                    for(int i = 0; i < itLimit; i++){
                        if(song.getGenre().compareTo(trendGenreSongs.get(i)) == 0){
                            genreAppears = true;
                        }
                    }
                    if (!genreAppears){
                        trendGenreSongs.add(song.getGenre());
                    }
                }
                iterations ++;
            }
        }
        return trendGenreSongs;
    }
    /* Method to GET a song from the records */
    public Song getSong(String name){
        Transaction tx = null;
        Song song = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            tx = session.beginTransaction();
            if (this.isSong(session, name)){
                song = (Song)session.get(Song.class, name);
            }
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
        return song;
    }
    /* Method to DELETE a song from the records */
    public void deleteSong(String name){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Song song;
        try{
            tx = session.beginTransaction();
            if (this.isSong(session, name)){
                song = this.getSong(name);
                session.delete(song);
            }
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
    }
    /* Method to CHECK is there is a song on the records */
    private boolean isSong(Session session, String name){
        Transaction tx = null;
        ArrayList<Song> songs;
        boolean isSong = false;
        try{
            tx = session.getTransaction();
            songs = (ArrayList<Song>) session.createQuery("FROM Song").list();
            for (Song song : songs){
                if (song.getTitle().compareTo(name) == 0){
                    isSong = true;
                }
            }
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }
        return isSong;
    }
    /* Method to convert a byte[] to a file */
    public void byteArrayToFile(byte[] byteArray, String outFilePath) throws IOException {
        FileOutputStream fos = new FileOutputStream(outFilePath);
        fos.write(byteArray);
        fos.close();
    }
    /* Method to convert from InputStream to byte[] */
    public byte[] inputStreamToByteArray(InputStream inStream) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[8192];
        int bytesRead;
        while ((bytesRead = inStream.read(buffer)) > 0) {
            baos.write(buffer, 0, bytesRead);
        }
        return baos.toByteArray();
    }
}