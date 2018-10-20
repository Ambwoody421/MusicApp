package app.model;

import app.config.MyLog;
import app.dao.DatabaseConnection;

import javax.sql.PooledConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Group {

    private Integer id;
    private String name;

    public Group() {
    }

    public Group(Integer id) {
        this.id = id;
    }

    public Group(Integer id, String name) {
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


    public boolean insertNewGroup(Integer ownerId) throws Exception{
        Connection connection = DatabaseConnection.getConnection();

        String sql = "Insert into music_database.music_group (name) values (?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, this.getName());

            preparedStatement.executeUpdate();

            ResultSet rs = preparedStatement.getGeneratedKeys();

            if (rs.next()) {
                this.setId(rs.getInt(1));
            }

            //Initalize owner of group object
            GroupMember owner = new GroupMember();
            owner.setGroupId(this.getId());
            owner.setUserId(ownerId);
            owner.setUserTypeId(1);

            //add group owner to database
            if(owner.addGroupOwner(connection)){
                MyLog.logMessage("Success creating Group: " + this.getName());
                connection.commit();
                connection.close();
                return true;
            }
            //if owner could not be inserted, rollback db changes
            else{
                connection.rollback();
                throw new Exception("Could not create Group: " + this.getName());
            }

        } catch (SQLException e) {
            MyLog.logException(e);
            connection.rollback();
            connection.close();
            throw new Exception(e);
        } finally {
            connection.close();
        }


    }

    public boolean deleteGroup() throws Exception{
        Connection connection = DatabaseConnection.getConnection();

        String sql = "Delete From music_database.music_group where id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, this.getId());

            preparedStatement.executeUpdate();

            MyLog.logMessage("Successfully deleted group: " + this.getId());
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

    public List<GroupMember> seeAllMembers() throws Exception{
        List<GroupMember> allUsers = new ArrayList<>();

        Connection connection = DatabaseConnection.getConnection();

        String sql = "Select a.user_id, b.name, a.user_type_id from music_database.users_in_group a join music_database.users b on a.user_id = b.id where a.group_id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, this.getId());

            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                Integer userId = rs.getInt(1);
                String name = rs.getString(2);
                Integer typeId = rs.getInt(3);

                allUsers.add(new GroupMember(this.getId(), userId, typeId, name));

            }

            MyLog.logMessage("Gathered all members in group: " + this.getId());
            Collections.sort(allUsers);

            return allUsers;

        } catch (SQLException e) {
            MyLog.logException(e);
            connection.close();
            throw new Exception(e);
        } finally {
            connection.close();
        }


    }

    public List<Playlist> seeAllPlaylists() throws Exception{
        List<Playlist> playlists = new ArrayList<>();

        Connection connection = DatabaseConnection.getConnection();

        String sql = "Select playlistId, groupId, playlistName from music_database.group_playlists WHERE groupId = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, this.getId());

            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                Integer playlistId = rs.getInt(1);
                Integer groupId = rs.getInt(2);
                String playlistName = rs.getString(3);

                playlists.add(new Playlist(playlistId, groupId, playlistName));

            }

            Collections.sort(playlists);

            MyLog.logMessage("Gathered all playlists in group: " + this.getId());
            return playlists;

        } catch (SQLException e) {
            MyLog.logException(e);
            connection.close();
            throw new Exception(e);
        } finally {
            connection.close();
        }

    }

    public List<Song> seeAllSongs() throws Exception{
        List<Song> songs = new ArrayList<>();

        Connection connection = DatabaseConnection.getConnection();

        String sql = "SELECT a.orderId, b.id, b.title, c.artist, b.filepath FROM music_database.group_queue a join music_database.songs b on a.songId=b.id join music_database.artists c on b.artist=c.id WHERE groupId = ? ORDER BY a.orderId ASC";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, this.getId());

            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                Integer id = rs.getInt(2);
                String title = rs.getString(3);
                String artist = rs.getString(4);
                String filepath = rs.getString(5);

                songs.add(new Song(id, title, artist, filepath));

            }

            MyLog.logMessage("Gathered all songs from group queue: " + this.getId());

            return songs;

        } catch (SQLException e) {
            MyLog.logException(e);
            connection.close();
            throw new Exception(e);
        } finally {
            connection.close();
        }

    }

    public Song nextQueueSong() throws Exception{

        Song song = null;

        Connection connection = DatabaseConnection.getConnection();

        String sql = "SELECT a.orderId, b.id, b.title, b.artist, b.filepath FROM music_database.group_queue a join music_database.songs b on a.songId=b.id WHERE groupId = ? ORDER BY a.orderId ASC LIMIT 1";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, this.getId());

            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                Integer id = rs.getInt(2);
                String title = rs.getString(3);
                String artist = rs.getString(4);
                String filepath = rs.getString(5);

                song = new Song(id, title, artist, filepath);

            }

            MyLog.logMessage("Gathered all songs from group queue: " + this.getId());

            return song;

        } catch (SQLException e) {
            MyLog.logException(e);
            connection.close();
            throw new Exception(e);
        } finally {
            connection.close();

        }

    }
}
