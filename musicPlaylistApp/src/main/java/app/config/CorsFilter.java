package app.config;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.PostConstruct;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Component
public class CorsFilter extends OncePerRequestFilter {


    static String ip;

    @Value("${frontendConfigFilepath}")
    private String config;


    @PostConstruct
    public void setup(){
        try {
            ip = InetAddress.getLocalHost().getHostAddress();

            MyLog.logMessage("IP registered as: " + ip);


        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        try {
            JSONObject obj = new JSONObject();
            obj.put("ip", ip);
            MyLog.logMessage(obj.toString());
            PrintWriter writer = new PrintWriter(config);
            writer.println(obj.toString());
            writer.flush();
            writer.close();

            MyLog.logMessage("Wrote Config Successfully");

        } catch (FileNotFoundException e) {
            MyLog.logException(e);
        }
    }


    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response,
                                    final FilterChain filterChain) throws ServletException, IOException {
        response.addHeader("Access-Control-Allow-Origin", "http://" + ip + ":3000");
        response.addHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, PATCH, HEAD, OPTIONS");
        response.addHeader("Access-Control-Allow-Headers", "Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
        response.addHeader("Access-Control-Expose-Headers", "Access-Control-Allow-Origin, Access-Control-Allow-Credentials");
        response.addHeader("Access-Control-Allow-Credentials", "true");
        response.addIntHeader("Access-Control-Max-Age", 1200);
        filterChain.doFilter(request, response);
    }
}
