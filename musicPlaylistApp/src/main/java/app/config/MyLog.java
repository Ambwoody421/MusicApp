package app.config;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

@Component
public class MyLog {

    public static final Logger logger = Logger.getGlobal();
    public static FileHandler fh;

    public MyLog(){}

    @PostConstruct
    public void setUp() {

        System.out.println("Setting up logger");
        try

        {
            // This block configure the logger with handler and formatter
            fh = new FileHandler("MyLogFile.log", 1000000, 3, true);
            fh.setFormatter(new SimpleFormatter());
            logger.addHandler(fh);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void logException(Exception e){
        MyLog.logger.log(Level.WARNING, e.getMessage(), e);
    }

    public static void logMessage(String msg){
        MyLog.logger.log(Level.INFO, msg);
    }
}

