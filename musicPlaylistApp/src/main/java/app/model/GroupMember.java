package app.model;

import app.config.MyLog;
import app.dao.DatabaseConnection;

import javax.sql.PooledConnection;
import java.sql.*;

public class GroupMember implements Comparable<GroupMember> {

    private Integer groupId;
    private Integer userId;
    private Integer userTypeId;
    private String userName;

    public GroupMember(){}

    public GroupMember(Integer groupId, Integer userId) {
        this.groupId = groupId;
        this.userId = userId;

    }

    public GroupMember(Integer groupId, Integer userId, Integer userTypeId, String userName) {
        this.groupId = groupId;
        this.userId = userId;
        this.userTypeId = userTypeId;
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserTypeId() {
        return userTypeId;
    }

    public void setUserTypeId(Integer userTypeId) {
        this.userTypeId = userTypeId;
    }

    public boolean addGroupMember() throws Exception{
        Connection connection = DatabaseConnection.getConnection();

        String sql = "Insert into music_database.users_in_group (group_id, user_id, user_type_id) values (?,?,?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, this.getGroupId());
            preparedStatement.setInt(2, this.getUserId());
            preparedStatement.setInt(3, this.getUserTypeId());

            preparedStatement.executeUpdate();

            MyLog.logMessage("Success Adding: " + this.getUserId() + " to the group: " + this.getGroupId());
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

    public boolean removeGroupMember() throws Exception{
        Connection connection = DatabaseConnection.getConnection();

        String sql = "Delete from music_database.users_in_group where group_id = ? AND user_id = ?;";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, this.getGroupId());
            preparedStatement.setInt(2, this.getUserId());

            preparedStatement.executeUpdate();

            MyLog.logMessage("Success Removing: " + this.getUserId() + " from the group: " + this.getGroupId());
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

    public boolean addGroupOwner(Connection connection) throws Exception{

        String sql = "Insert into music_database.users_in_group (group_id, user_id, user_type_id) values (?,?,?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, this.getGroupId());
            preparedStatement.setInt(2, this.getUserId());
            preparedStatement.setInt(3, this.getUserTypeId());

            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            MyLog.logException(e);
            connection.rollback();
            connection.close();
            throw new Exception(e);
        }

        MyLog.logMessage("Success Adding: " + this.getUserId() + " to the group: " + this.getGroupId());
        return true;
    }

    @Override
    public int compareTo(GroupMember o) {
        return this.getUserTypeId() - o.getUserTypeId();
    }
}
