package app.model;

import javax.ws.rs.core.*;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class CookieFactory {


    public static NewCookie getCookie(Session session) {
        Cookie cookie = new Cookie("sessId",session.getSessionId(), "/", null);
        Calendar calendar = Calendar.getInstance();

        NewCookie c = new NewCookie(cookie,"",1800,new Date(calendar.getTimeInMillis() + TimeUnit.MINUTES.toMillis(30)),false, false);
        System.out.println("expires: " + c.getExpiry());
        return c;
    }


    public static NewCookie getCookie(String test) {

        NewCookie c = new NewCookie("sessId",test,"/","","",1800,false);
        return c;
    }
}
