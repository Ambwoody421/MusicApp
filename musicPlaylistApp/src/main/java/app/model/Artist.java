package app.model;

import app.config.MyLog;
import app.dao.DatabaseConnection;

import javax.sql.PooledConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Artist {

    private Integer id;
    private String name;

    public Artist() {
    }

    public Artist(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static List<Artist> retrieveAllArtists() throws Exception{
        Connection connection = DatabaseConnection.getConnection();

        List<Artist> artists = new ArrayList<>();

        String sql = "Select id, artist from music_database.artists";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                artists.add(new Artist(rs.getInt(1), rs.getString(2)));
            }
            MyLog.logMessage("Retrieved All artists");

            return artists;


        } catch (SQLException e) {
            MyLog.logException(e);
            connection.close();
            throw new Exception(e);
        } finally {
            connection.close();
        }

    }

    public static List<Artist> retrieveArtistsByLetter(char c) throws Exception{
        Connection connection = DatabaseConnection.getConnection();

        String searchCriteria = c + "%%";

        List<Artist> artists = new ArrayList<>();

        String sql = "Select id, artist from music_database.artists Where artist Like ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, searchCriteria);

            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                artists.add(new Artist(rs.getInt(1), rs.getString(2)));
            }
            MyLog.logMessage("Retrieved All artists by letter");

            return artists;


        } catch (SQLException e) {
            MyLog.logException(e);
            connection.close();
            throw new Exception(e);
        } finally {
            connection.close();
        }

    }
}
