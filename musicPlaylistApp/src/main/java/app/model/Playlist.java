package app.model;

import app.config.MyLog;
import app.dao.DatabaseConnection;

import javax.sql.PooledConnection;
import java.sql.*;
import java.util.List;

public class Playlist implements Comparable<Playlist> {

    private Integer id;
    private Integer groupId;
    private String name;

    public Playlist() {
    }

    public Playlist(Integer groupId, String name) {
        this.groupId = groupId;
        this.name = name;
    }

    public Playlist(Integer id, Integer groupId, String name) {
        this.id = id;
        this.groupId = groupId;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean insertGroupPlaylist() throws Exception{
        Connection connection = DatabaseConnection.getConnection();

        String sql = "Insert into music_database.group_playlists (groupId, playlistName) values (?,?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, this.getGroupId());
            preparedStatement.setString(2, this.getName());

            preparedStatement.executeUpdate();

            ResultSet rs = preparedStatement.getGeneratedKeys();

            if (rs.next()) {
                this.setId(rs.getInt(1));
            }

            MyLog.logMessage("Success creating Playlist: " + this.getName() + " for group: " + this.getGroupId());
            connection.commit();

            return true;

        } catch (SQLException e) {
            MyLog.logException(e);
            connection.rollback();
            connection.close();
            throw new Exception(e);
        } finally {
            connection.close();
        }


    }

    @Override
    public int compareTo(Playlist o) {
        return this.getName().compareTo(o.getName());
    }
}
