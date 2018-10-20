package app.model;

import app.config.MyLog;
import app.dao.DatabaseConnection;

import javax.sql.PooledConnection;
import java.sql.Connection;
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
        Connection connection = DatabaseConnection.getConnection();

        String sql = "Insert into music_database.group_queue (groupId, songId) values (?,?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, this.getGroupId());
            preparedStatement.setInt(2, this.getSongId());

            preparedStatement.executeUpdate();

            MyLog.logMessage("Success adding Song: " + this.getSongId() + " to group queue " + this.getGroupId());
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

    public boolean deleteSongDB() throws Exception{
        Connection connection = DatabaseConnection.getConnection();

        String sql = "Delete from music_database.group_queue where groupId=? AND songId=? ORDER BY orderId LIMIT 1";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, this.getGroupId());
            preparedStatement.setInt(2, this.getSongId());

            preparedStatement.executeUpdate();

            MyLog.logMessage("Success deleting Song: " + this.getSongId() + " from group queue " + this.getGroupId());
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

}
