package app.model;

import app.config.MyLog;
import app.dao.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
        DatabaseConnection connection = new DatabaseConnection();
        connection.setConnection();

        String sql = "Insert into music_database.music_group (name) values (?)";

        try {
            PreparedStatement preparedStatement = connection.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

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
                connection.getConnection().commit();
                connection.closeConnection();
                return true;
            }
            //if owner could not be inserted, rollback db changes
            else{
                connection.getConnection().rollback();
                throw new Exception("Could not create Group: " + this.getName());
            }

        } catch (SQLException e) {
            MyLog.logException(e);
            connection.closeConnection();
            throw new Exception(e);
        }


    }

    public List<GroupMember> seeAllMembers() throws Exception{
        List<GroupMember> allUsers = new ArrayList<>();

        DatabaseConnection connection = new DatabaseConnection();
        connection.setConnection();

        String sql = "Select a.user_id, b.name, a.user_type_id from music_database.users_in_group a join music_database.users b on a.user_id = b.id where a.group_id = ?";

        try {
            PreparedStatement preparedStatement = connection.getConnection().prepareStatement(sql);

            preparedStatement.setInt(1, this.getId());

            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                Integer userId = rs.getInt(1);
                String name = rs.getString(2);
                Integer typeId = rs.getInt(3);

                allUsers.add(new GroupMember(this.getId(), userId, typeId, name));

            }


        } catch (SQLException e) {
            MyLog.logException(e);
            connection.closeConnection();
            throw new Exception(e);
        }

        Collections.sort(allUsers);

        MyLog.logMessage("Gathered all members in group: " + this.getId());
        connection.closeConnection();
        return allUsers;
    }

    public List<Playlist> seeAllPlaylists() throws Exception{
        List<Playlist> playlists = new ArrayList<>();

        DatabaseConnection connection = new DatabaseConnection();
        connection.setConnection();

        String sql = "Select playlistId, groupId, playlistName from music_database.group_playlists WHERE groupId = ?";

        try {
            PreparedStatement preparedStatement = connection.getConnection().prepareStatement(sql);

            preparedStatement.setInt(1, this.getId());

            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                Integer playlistId = rs.getInt(1);
                Integer groupId = rs.getInt(2);
                String playlistName = rs.getString(3);

                playlists.add(new Playlist(playlistId, groupId, playlistName));

            }


        } catch (SQLException e) {
            MyLog.logException(e);
            connection.closeConnection();
            throw new Exception(e);
        }

        Collections.sort(playlists);

        MyLog.logMessage("Gathered all playlists in group: " + this.getId());
        connection.closeConnection();
        return playlists;
    }

    public List<Song> seeAllSongs() throws Exception{
        List<Song> songs = new ArrayList<>();

        DatabaseConnection connection = new DatabaseConnection();
        connection.setConnection();

        String sql = "SELECT a.orderId, b.id, b.title, b.artist, b.filepath FROM music_database.group_queue a join music_database.songs b on a.songId=b.id WHERE groupId = ? ORDER BY a.orderId ASC";

        try {
            PreparedStatement preparedStatement = connection.getConnection().prepareStatement(sql);

            preparedStatement.setInt(1, this.getId());

            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                Integer id = rs.getInt(2);
                String title = rs.getString(3);
                String artist = rs.getString(4);
                String filepath = rs.getString(5);

                songs.add(new Song(id, title, artist, filepath));

            }


        } catch (SQLException e) {
            MyLog.logException(e);
            connection.closeConnection();
            throw new Exception(e);
        }

        MyLog.logMessage("Gathered all songs from group queue: " + this.getId());
        connection.closeConnection();
        return songs;
    }

    public Song nextQueueSong() throws Exception{

        Song song = null;

        DatabaseConnection connection = new DatabaseConnection();
        connection.setConnection();

        String sql = "SELECT a.orderId, b.id, b.title, b.artist, b.filepath FROM music_database.group_queue a join music_database.songs b on a.songId=b.id WHERE groupId = ? ORDER BY a.orderId ASC LIMIT 1";

        try {
            PreparedStatement preparedStatement = connection.getConnection().prepareStatement(sql);

            preparedStatement.setInt(1, this.getId());

            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                Integer id = rs.getInt(2);
                String title = rs.getString(3);
                String artist = rs.getString(4);
                String filepath = rs.getString(5);

                song = new Song(id, title, artist, filepath);

            }


        } catch (SQLException e) {
            MyLog.logException(e);
            connection.closeConnection();
            throw new Exception(e);
        } finally {
            MyLog.logMessage("Gathered all songs from group queue: " + this.getId());
            connection.closeConnection();

        }

        return song;

    }
}
