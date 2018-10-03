package app.model;

import app.config.MyLog;
import app.dao.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class QueueSong {

    private Integer groupId;
    private Integer songId;

    public QueueSong() {
    }


    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getSongId() {
        return songId;
    }

    public void setSongId(Integer songId) {
        this.songId = songId;
    }

    public boolean addSongDB() throws Exception{
        DatabaseConnection connection = new DatabaseConnection();
        connection.setConnection();

        String sql = "Insert into music_database.group_queue (groupId, songId) values (?,?)";

        try {
            PreparedStatement preparedStatement = connection.getConnection().prepareStatement(sql);

            preparedStatement.setInt(1, this.getGroupId());
            preparedStatement.setInt(2, this.getSongId());

            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            MyLog.logException(e);
            connection.closeConnection();
            throw new Exception(e);
        }

        MyLog.logMessage("Success adding Song: " + this.getSongId() + " to group queue " + this.getGroupId());
        connection.getConnection().commit();
        connection.closeConnection();
        return true;

    }

    public boolean deleteSongDB() throws Exception{
        DatabaseConnection connection = new DatabaseConnection();
        connection.setConnection();

        String sql = "Delete from music_database.group_queue where groupId=? AND songId=? ORDER BY orderId LIMIT 1";

        try {
            PreparedStatement preparedStatement = connection.getConnection().prepareStatement(sql);

            preparedStatement.setInt(1, this.getGroupId());
            preparedStatement.setInt(2, this.getSongId());

            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            MyLog.logException(e);
            connection.closeConnection();
            throw new Exception(e);
        }

        MyLog.logMessage("Success deleting Song: " + this.getSongId() + " from group queue " + this.getGroupId());
        connection.getConnection().commit();
        connection.closeConnection();
        return true;

    }

}
