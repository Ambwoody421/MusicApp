package app.dao;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.SQLException;


@Component
public class DatabaseConnection {

    @Value("${db.user}")
	private String user ="root";
    @Value("${db.pass}")
    private String password ="toor";
    @Value("${db.server}")
    private String server ="localhost";
    @Value("${db.port}")
    private String port ="3306";
    private static BasicDataSource dataSource;

    public DatabaseConnection() {
    }

    @PostConstruct
    public void setConnection() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUsername(this.user);
        dataSource.setPassword(this.password);
        dataSource.setUrl("jdbc:mysql://"+this.server+":"+this.port+"/");
        dataSource.setInitialSize(3);
        dataSource.setMinIdle(3);
        dataSource.setDefaultAutoCommit(false);

		this.dataSource = dataSource;


    }
    
    public static Connection getConnection() {

        Connection con = null;

        try {
            con = dataSource.getConnection();

        } catch (SQLException s){
            s.printStackTrace();
        }

    	return con;
    }
    
    
}
