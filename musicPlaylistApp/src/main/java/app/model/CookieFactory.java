package app.model;

import javax.ws.rs.core.*;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class CookieFactory {


    public static NewCookie getCookie(Session session) {
        Cookie cookie = new Cookie("sessId",session.getSessionId(), "/", null);
        Calendar calendar = Calendar.getInstance();

        NewCookie c = new NewCookie(cookie,"",7200,new Date(calendar.getTimeInMillis() + TimeUnit.MINUTES.toMillis(120)),false, true);
        System.out.println("expires: " + c.getExpiry());
        return c;
    }


    public static NewCookie getUserIdCookie(Integer id) {

        String userId = id.toString();
        NewCookie c = new NewCookie("userId",userId,"/","","",120,false);
        return c;
    }
}
