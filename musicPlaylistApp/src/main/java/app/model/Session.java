package app.model;

import app.config.MyLog;
import app.dao.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class Session {

    private String sessionId;
    private Integer userId;
    private long timestamp;

    public Session(Integer id) {

        setSessionId();
        setCurrentTimestamp();
        this.userId = id;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId() {
        this.sessionId = UUID.randomUUID().toString();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setCurrentTimestamp() {
        this.timestamp = System.currentTimeMillis() / 1000L;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public boolean createNewSession() throws Exception{
        DatabaseConnection connection = new DatabaseConnection();
        connection.setConnection();


        String sql = "Insert into music_database.user_session (session_id, user_id, unixTimestamp) values (?,?,?)";

        try {
            PreparedStatement preparedStatement = connection.getConnection().prepareStatement(sql);

            preparedStatement.setString(1, this.sessionId);
            preparedStatement.setInt(2, this.userId);
            preparedStatement.setLong(3, this.getTimestamp());

            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            MyLog.logException(e);
            connection.closeConnection();
            return false;
        }

        MyLog.logMessage("Session: " + this.getSessionId() + "  created for user: " + this.getUserId());
        connection.getConnection().commit();
        connection.closeConnection();
        return true;
    }

    public static Integer validateSession(String sessionId) throws Exception{

        DatabaseConnection connection = new DatabaseConnection();
        connection.setConnection();

        String sql = "Select user_id, unixTimestamp from music_database.user_session where session_id = ?";

        try {
            PreparedStatement preparedStatement = connection.getConnection().prepareStatement(sql);

            preparedStatement.setString(1, sessionId);

            ResultSet rs = preparedStatement.executeQuery();

            if(rs.next()){

                //check if timestamp is 30 minutes old
                long currentTime = System.currentTimeMillis() / 1000L;
                if((currentTime - rs.getLong(2))/60 < 30){

                    Integer userId = rs.getInt(1);

                    connection.closeConnection();
                    //return User Id
                    return userId;
                }
                else{
                    MyLog.logMessage("Session: " + sessionId + " is expired.");
                    connection.closeConnection();
                    throw new Exception("Session: " + sessionId + " is expired.");
                }
            }


        } catch (SQLException e) {
            MyLog.logException(e);
            connection.closeConnection();
            return null;
        }

        MyLog.logMessage("Failed validating a Session: " + sessionId);
        connection.closeConnection();
        throw new Exception("Failed validating a Session: " + sessionId);
    }

}

