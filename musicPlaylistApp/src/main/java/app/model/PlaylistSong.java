package app.model;

import app.config.MyLog;
import app.dao.DatabaseConnection;

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
        DatabaseConnection connection = new DatabaseConnection();
        connection.setConnection();

        String sql = "Insert into music_database.playlist_songs (playlistId, songId) values (?,?)";

        try {
            PreparedStatement preparedStatement = connection.getConnection().prepareStatement(sql);

            preparedStatement.setInt(1, this.getPlaylistId());
            preparedStatement.setInt(2, this.getSongId());

            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            MyLog.logException(e);
            connection.closeConnection();
            throw new Exception(e);
        }

        MyLog.logMessage("Success creating adding Song: " + this.getSongId() + " to playlist " + this.getPlaylistId());
        connection.getConnection().commit();
        connection.closeConnection();
        return true;

    }

    public boolean deleteSongDB() throws Exception{
        DatabaseConnection connection = new DatabaseConnection();
        connection.setConnection();

        String sql = "Delete music_database.playlist_songs where playlistId=? AND songId=?";

        try {
            PreparedStatement preparedStatement = connection.getConnection().prepareStatement(sql);

            preparedStatement.setInt(1, this.getPlaylistId());
            preparedStatement.setInt(2, this.getSongId());

            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            MyLog.logException(e);
            connection.closeConnection();
            throw new Exception(e);
        }

        MyLog.logMessage("Success creating adding Song: " + this.getSongId() + " to playlist " + this.getPlaylistId());
        connection.getConnection().commit();
        connection.closeConnection();
        return true;

    }
}
