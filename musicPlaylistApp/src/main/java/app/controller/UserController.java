package app.controller;

import app.model.CookieFactory;
import app.model.Group;
import app.model.Session;
import app.model.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import java.util.List;


@Path("user")
public class UserController {

    //Creates a new user in the database
    /**{name: required, password: required}
     *
     * Returns UserId
     * */
    @POST
    @Path("/createUser")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response createUser(User user) {

        try {
            //create new User into Database
            if(user.insertNewUser()) {

                Session session = new Session(user.getId());

                //insert Session in database
                session.createNewSession();

                NewCookie cookie = CookieFactory.getCookie(session);

                return Response.ok().cookie(cookie).entity("Success").build();

            }
            else{
                throw new Exception("Could not create User");
            }


        } catch (Exception e) {
            return Response.status(200).entity(e.getMessage()).build();
        }
    }

    //Used to validate that the user name and password exists. Used for login purposes.
    /** Used to Login
     * {name: required, password: required}
     *
     * Returns UserId
     * */
    @POST
    @Path("/validateUser")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response validateUser(User user){

        try {
            //lookup user credentials for match
            if(user.validateUser()) {

                Session session = new Session(user.getId());

                //insert new session in database
                session.createNewSession();

                NewCookie cookie = CookieFactory.getCookie(session);
                NewCookie userIdCookie = CookieFactory.getUserIdCookie(user.getId());

                return Response.ok().cookie(cookie).cookie(userIdCookie).entity("Successfully Logged In").build();
            }
            else{
                throw new Exception("Could not validate User");
            }

        } catch (Exception e) {
            String s = "Failure";
            return Response.status(200).entity(s).build();
        }

    }


    //Retrieves all the groups that the particular user is owner of
    /**
     * Returns Id and group name for all groups that the user is an owner of
     * */
    @GET
    @Path("/getOwnedGroups")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOwnedGroups(@CookieParam("sessId") String sessionId) {

        try {
            //check if cookie was sent
            if(sessionId == null){
                System.out.println("Sess Id empty");
                return Response.status(403).entity("Session expired").build();
            }

            Integer userId = Session.validateSession(sessionId);

            if(userId != null){

                List<Group> allGroups = new User(userId).seeOwnedGroups();

                return Response.status(200).entity(allGroups).build();
            }
            else{
                throw new Exception("Could not retrieve all groups for session: " + sessionId);
            }

        } catch (Exception e) {
            String s = "Failure";
            return Response.status(400).entity(s).build();
        }
    }

    /**
     * Returns Id and group name for all groups that the user is a member of
     * */
    @GET
    @Path("/getMemberGroups")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMemberGroups(@CookieParam("sessId") String sessionId){

        try {
            //check if cookie was sent
            if(sessionId == null){
                return Response.status(403).entity("Session expired").build();
            }

            Integer userId = Session.validateSession(sessionId);

            if(userId != null){

                List<Group> allGroups = new User(userId).seeMemberGroups();

                return Response.status(200).entity(allGroups).build();
            }
            else{
                throw new Exception("Could not retrieve all groups for session: " + sessionId);
            }

        } catch (Exception e) {
            String s = "Failure";
            return Response.status(400).entity(s).build();
        }
    }

    /**
     * Returns Id and names of all users
     * */
    @GET
    @Path("/seeAllUsers")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsers(@CookieParam("sessId") String sessionId){

        try {
            //check if cookie was sent
            if(sessionId == null){
                return Response.status(403).entity("Session expired").build();
            }

            Integer userId = Session.validateSession(sessionId);

            if(userId != null){

                List<User> allUsers = User.seeAllUsers();

                return Response.status(200).entity(allUsers).build();
            }
            else{
                throw new Exception("Could not retrieve all users for session: " + sessionId);
            }

        } catch (Exception e) {
            String s = "Failure";
            return Response.status(400).entity(s).build();
        }
    }



}
