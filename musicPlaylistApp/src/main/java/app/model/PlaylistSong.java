package app.model;

import app.config.MyLog;
import app.dao.DatabaseConnection;

import javax.sql.PooledConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PlaylistSong {

    private Integer playlistId;
    private Integer songId;

    public PlaylistSong() {
    }

    public Integer getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(Integer playlistId) {
        this.playlistId = playlistId;
    }

    public Integer getSongId() {
        return songId;
    }

    public void setSongId(Integer songId) {
        this.songId = songId;
    }

    public boolean addSongDB() throws Exception{
        Connection connection = DatabaseConnection.getConnection();

        String sql = "Insert into music_database.playlist_songs (playlistId, songId) values (?,?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, this.getPlaylistId());
            preparedStatement.setInt(2, this.getSongId());

            preparedStatement.executeUpdate();

            MyLog.logMessage("Success creating adding Song: " + this.getSongId() + " to playlist " + this.getPlaylistId());
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

        String sql = "Delete music_database.playlist_songs where playlistId=? AND songId=?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, this.getPlaylistId());
            preparedStatement.setInt(2, this.getSongId());

            preparedStatement.executeUpdate();

            MyLog.logMessage("Success creating adding Song: " + this.getSongId() + " to playlist " + this.getPlaylistId());
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
