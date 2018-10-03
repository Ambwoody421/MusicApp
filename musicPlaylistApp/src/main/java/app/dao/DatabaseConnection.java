package app.dao;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

public class DatabaseConnection {

	//private static String user ="root";
   // private static String password ="toor";
   // private static String server ="localhost";
    private Connection con;


    public void setConnection() {

        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUser("root");
        dataSource.setPassword("toor");
        dataSource.setServerName("localhost");
		try {
			dataSource.setUseSSL(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
            this.con = dataSource.getConnection();
            this.con.setAutoCommit(false);
         
        } catch (SQLException s){
            s.printStackTrace();
        }


    }
    
    public Connection getConnection() {
    	
    	return this.con;
    }
    
    public void closeConnection() {
    	
    	try {
			this.con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//MyLog.logger.log(Level.SEVERE, e.getMessage(), e);
		}
    	
    }
    

    
    /*
    public static boolean createNewUser(User user) {

    	Connection con = DatabaseConnection.getConnection();

    	String sql = "Insert into music_database.users (name, password) values (?,?)";

    	try {
			PreparedStatement preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			preparedStatement.setString(1, user.getName());
			preparedStatement.setString(2, user.getPassword());

			preparedStatement.executeUpdate();

			ResultSet rs = preparedStatement.getGeneratedKeys();

            if (rs.next()) {
                user.setId(rs.getInt(1));
                con.close();
                return true;
            }



		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    	return false;

    }
    */
    
    
}
