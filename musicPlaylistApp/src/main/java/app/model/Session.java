package app.model;

import app.config.MyLog;
import app.dao.DatabaseConnection;

import javax.sql.PooledConnection;
import java.sql.*;
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
        Connection connection = DatabaseConnection.getConnection();


        String sql = "Insert into music_database.user_session (session_id, user_id, unixTimestamp) values (?,?,?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, this.sessionId);
            preparedStatement.setInt(2, this.userId);
            preparedStatement.setLong(3, this.getTimestamp());

            preparedStatement.executeUpdate();

            MyLog.logMessage("Session: " + this.getSessionId() + "  created for user: " + this.getUserId());
            connection.commit();

            return true;

        } catch (SQLException e) {
            MyLog.logException(e);
            connection.rollback();
            connection.close();
            return false;
        } finally {
            connection.close();
        }


    }

    public static Integer validateSession(String sessionId) throws Exception{

        Connection connection = DatabaseConnection.getConnection();

        String sql = "Select user_id, unixTimestamp from music_database.user_session where session_id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, sessionId);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {

                Integer userId = rs.getInt(1);


                //return User Id
                return userId;

            }

            return null;


        } catch (SQLException e) {
            MyLog.logException(e);
            MyLog.logMessage("Failed validating a Session: " + sessionId);
            connection.close();
            return null;
        } finally {
            connection.close();
        }

    }

}

