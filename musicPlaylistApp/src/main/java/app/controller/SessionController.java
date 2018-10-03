package app.controller;

import app.model.CookieFactory;
import app.model.Session;
import app.model.Test;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

@Path("session")
@Produces(MediaType.APPLICATION_JSON)
public class SessionController {

    /** creates a new User session
     * {userId: required}
     */
    @POST
    @Path("/createSession")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createSession(Session session) {

        try {
            //Intialize a new session
            session.setCurrentTimestamp();
            session.setSessionId();
            session.createNewSession();

            return Response.status(200).entity(session).build();

        } catch (Exception e) {
            String s = "Failure";
            return Response.status(400).entity(s).build();
        }
    }

    /** Validates a session exists within last 30 minutes
     * {userId: required, sessionId: required}
     */
    @POST
    @Path("/validateSession")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response validateSession(Session session) {

        try {
            //validates a session
            //session.validateSession();

            return Response.status(200).entity(session).build();

        } catch (Exception e) {
            String s = "Failure";
            return Response.status(400).entity(s).build();
        }
    }

    @POST
    @Path("/cookie")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createCookie() {


        NewCookie c = CookieFactory.getCookie("test");

        System.out.println(c.getExpiry());
        return Response.ok().entity("Hello").cookie(c).build();

    }

    @POST
    @Path("/test")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response hello(@CookieParam("sessId") String id) {


        System.out.println("Cookie says id is: " + id);
        System.out.println("Cookie says id is: " + id);
        return Response.ok().entity("Hello").build();
    }

    @GET
    @Path("/blah")
    public Response blah(){

        return Response.ok().entity(new Test("This is working")).build();
    }


}
