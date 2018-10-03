package app.model;

import app.config.MyLog;
import app.dao.DatabaseConnection;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

public class User {

    private Integer id;
    private String name;
    private String password;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User() {
    }

    public User(Integer id) {
        this.id = id;
    }

    public User(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public boolean insertNewUser() throws Exception{
        DatabaseConnection connection = new DatabaseConnection();
        connection.setConnection();

        String sql = "Insert into music_database.users (name, password) values (?,?)";

        try {
            PreparedStatement preparedStatement = connection.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, this.getName());
            preparedStatement.setString(2, this.getPassword());

            preparedStatement.executeUpdate();

            ResultSet rs = preparedStatement.getGeneratedKeys();

            if (rs.next()) {
                this.setId(rs.getInt(1));
                this.setPassword(null);
            }

        } catch (SQLException e) {
            MyLog.logException(e);
            connection.closeConnection();
            return false;
        }

        MyLog.logMessage("Success creating User: " + this.getName());
        connection.getConnection().commit();
        connection.closeConnection();
        return true;
    }

    public boolean validateUser() throws Exception{
        DatabaseConnection connection = new DatabaseConnection();
        connection.setConnection();

        String sql = "Select id, name, password from music_database.users where name = ? AND password = ?";

        try {
            PreparedStatement preparedStatement = connection.getConnection().prepareStatement(sql);

            preparedStatement.setString(1, this.getName());
            preparedStatement.setString(2, this.getPassword());

            ResultSet rs = preparedStatement.executeQuery();

            if(rs.next()){
                Integer id = rs.getInt(1);
                String name = rs.getString(2);
                String password = rs.getString(3);

                if(this.getName().compareTo(name) == 0 && this.getPassword().compareTo(password) == 0){

                    MyLog.logMessage("Success validating User: " + this.getName());
                    this.setId(id);
                    this.setPassword(null);
                    connection.closeConnection();
                    return true;
                }
            }


        } catch (SQLException e) {
            MyLog.logException(e);
            connection.closeConnection();
            return false;
        }

        MyLog.logMessage("Failed validating a User: " + this.getName());
        connection.closeConnection();
        throw new Exception("Failed validating a User: " + this.getName());
    }

    public static List<User> seeAllUsers() throws Exception{

        List<User> users = new ArrayList<>();

        DatabaseConnection connection = new DatabaseConnection();
        connection.setConnection();

        String sql = "Select id, name from music_database.users";

        try {
            PreparedStatement preparedStatement = connection.getConnection().prepareStatement(sql);


            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                Integer id = rs.getInt(1);
                String name = rs.getString(2);

                users.add(new User(id, name));
            }


        } catch (SQLException e) {
            MyLog.logMessage("Failed getting all users");
            MyLog.logException(e);
            connection.closeConnection();
            return users;
        }

        connection.closeConnection();
        return users;
    }

    public List<Group> seeOwnedGroups() throws Exception{
        List<Group> allGroups = new ArrayList<>();

        DatabaseConnection connection = new DatabaseConnection();
        connection.setConnection();

        String sql = "Select a.id, a.name from music_database.music_group a join music_database.users_in_group b on a.id = b.group_id join music_database.user_type_reference c on b.user_type_id=c.id where b.user_id = ? AND c.user_type = ?";

        try {
            PreparedStatement preparedStatement = connection.getConnection().prepareStatement(sql);

            preparedStatement.setInt(1, this.getId());
            preparedStatement.setString(2, "OWNER");

            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                Integer id = rs.getInt(1);
                String name = rs.getString(2);

                allGroups.add(new Group(id, name));

            }


        } catch (SQLException e) {
            MyLog.logException(e);
            connection.closeConnection();
            throw new Exception(e);
        }

        MyLog.logMessage("Gathered all groups owned by User: " + this.getId());
        connection.closeConnection();
        return allGroups;
    }

    public List<Group> seeMemberGroups() throws Exception{
        List<Group> allGroups = new ArrayList<>();

        DatabaseConnection connection = new DatabaseConnection();
        connection.setConnection();

        String sql = "Select a.id, a.name from music_database.music_group a join music_database.users_in_group b on a.id = b.group_id join music_database.user_type_reference c on b.user_type_id=c.id where b.user_id = ? AND c.user_type = ?";

        try {
            PreparedStatement preparedStatement = connection.getConnection().prepareStatement(sql);

            preparedStatement.setInt(1, this.getId());
            preparedStatement.setString(2, "MEMBER");

            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                Integer id = rs.getInt(1);
                String name = rs.getString(2);

                allGroups.add(new Group(id, name));

            }


        } catch (SQLException e) {
            MyLog.logException(e);
            connection.closeConnection();
            throw new Exception(e);
        }

        MyLog.logMessage("Gathered all groups owned by User: " + this.getId());
        connection.closeConnection();
        return allGroups;
    }
}
