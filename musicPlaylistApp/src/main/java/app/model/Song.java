package app.model;

import app.config.MyLog;
import app.dao.DatabaseConnection;

import javax.sql.PooledConnection;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Song {

    private Integer id;
    private String title;
    private String artist;
    private String filepath;
    private String url;

    public Song() {
    }

    public Song(Integer id, String title, String artist,  String filepath) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.filepath = filepath;
    }

    public Song(Integer id, String title, String filepath) {
        this.id = id;
        this.title = title;
        this.filepath = filepath;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {

        // C:\Users\Alex Bartsch\Documents\MusicAppSongs\Test.mp3

        this.filepath = filepath;
    }

    public boolean insertNewSong() throws Exception{
        Connection connection = DatabaseConnection.getConnection();

        String sql = "Insert into music_database.songs (artist, title, filepath) values ((Select id From music_database.artists Where artist=?), ?, ?);";
        String sqlArtist = "INSERT INTO music_database.artists (artist) SELECT * FROM (SELECT ?) AS tmp WHERE NOT EXISTS (SELECT artist FROM music_database.artists WHERE artist = ?) LIMIT 1";
        try {
            // insert Artist if they are new
            PreparedStatement preparedStatementArtist = connection.prepareStatement(sqlArtist);
            preparedStatementArtist.setString(1, this.getArtist());
            preparedStatementArtist.setString(2, this.getArtist());
            preparedStatementArtist.executeUpdate();

            // insert new song
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, this.getArtist());
            preparedStatement.setString(2, this.getTitle());
            preparedStatement.setString(3, this.getFilepath());
            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            MyLog.logException(e);
            connection.rollback();
            connection.close();
            throw new Exception(e);
        }

        MyLog.logMessage("Success creating Song: " + this.getTitle());
        connection.commit();
        connection.close();
        return true;
    }

    public boolean retrieveSongByTitle() throws Exception{
        Connection connection = DatabaseConnection.getConnection();

        String sql = "Select filepath from music_database.songs where title = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, this.getTitle());

            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                this.setFilepath(rs.getString(1));
            }


        } catch (SQLException e) {
            MyLog.logException(e);
            connection.close();
            throw new Exception(e);
        }

        MyLog.logMessage("Retrieved song: " + this.getTitle());
        connection.close();
        return true;
    }

    public static List<Song> retrieveAllSongs() throws Exception{
        Connection connection = DatabaseConnection.getConnection();

        List<Song> songs = new ArrayList<>();

        String sql = "Select id, title, artist, filepath from music_database.songs";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                songs.add(new Song(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4)));
            }


        } catch (SQLException e) {
            MyLog.logException(e);
            connection.close();
            throw new Exception(e);
        }

        MyLog.logMessage("Retrieved All songs");
        connection.close();
        return songs;
    }

    public static List<Song> retrieveSongsByArtist(Integer artist) throws Exception{
        Connection connection = DatabaseConnection.getConnection();

        List<Song> songs = new ArrayList<>();

        String sql = "Select id, title, filepath from music_database.songs Where artist = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1,artist);

            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                songs.add(new Song(rs.getInt(1), rs.getString(2), rs.getString(3)));
            }


        } catch (SQLException e) {
            MyLog.logException(e);
            connection.close();
            throw new Exception(e);
        }

        MyLog.logMessage("Retrieved All songs for artist: " + artist);
        connection.close();
        return songs;
    }

    public File retrieveSongAudio(String baseFilePath){

        //Windows
        // File file = new File(baseFilePath + "/" + this.getFilepath());
        // linux
        File file = new File(baseFilePath + "/" + this.getFilepath());

        return file;

    }
}
