package app.controller;

import app.model.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


@Path("group")
@Produces(MediaType.APPLICATION_JSON)
public class GroupController {

    /** Create a new Group
     * {name: required}
     * */
    @POST
    @Path("/createGroup")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createGroup(Group group, @CookieParam("sessId") String sessionId) {

        try {
            //check if cookie was sent
            if(sessionId == null){
                return Response.status(403).entity("Session expired").build();
            }

            //validate session is still good in database. Retrieve user id
            Integer userId = Session.validateSession(sessionId);

            if(userId != null) {
                //create group in db with user id as owner
                if(group.insertNewGroup(userId)){
                    return Response.status(200).entity(group).build();
                }
                else{
                    throw new Exception("Could not create Group");
                }

            }
            else{
                throw new Exception("Could not create Group");
            }

        } catch (Exception e) {
            String s = "Failure";
            return Response.status(400).entity(s).build();
        }
    }

    /** Add member to group
     * {groupId: required, userId: required, userTypeId: required}
     * */
    @POST
    @Path("/addMember")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addMember(GroupMember groupMember, @CookieParam("sessId") String sessionId) {

        try {
            //check if cookie was sent
            if(sessionId == null){
                return Response.status(403).entity("Session expired").build();
            }

            //validate session is still good in database. Retrieve user id
            Integer userId = Session.validateSession(sessionId);

            if(userId != null) {
                //create group in db with user id as owner
                if(groupMember.addGroupMember()){
                    return Response.status(200).entity(groupMember).build();
                }
                else{
                    throw new Exception("Could not add Member");
                }

            }
            else{
                throw new Exception("Could not add Member");
            }

        } catch (Exception e) {
            String s = "Failure";
            return Response.status(400).entity(s).build();
        }
    }

    //Retrieves all members and their info of a particular group
    /** Gets all members of the group
     * {id: required}
     * */
    @POST
    @Path("/allMembers")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getAllMembers(Group group, @CookieParam("sessId") String sessionId){

        try {
            //check if cookie was sent
            if(sessionId == null){
                return Response.status(403).entity("Session expired").build();
            }

            Integer userId = Session.validateSession(sessionId);

            if(userId != null){

                List<GroupMember> members = group.seeAllMembers();

                return Response.status(200).entity(members).build();
            }
            else{
                throw new Exception("Could not retrieve all members for session: " + sessionId);
            }

        } catch (Exception e) {
            String s = "Failure";
            return Response.status(400).entity(s).build();
        }
    }

    /**
     * {groupId: required, userId: required} removes a user
     * */
    @POST
    @Path("/removeMember")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response removeUserFromGroup(GroupMember groupMember, @CookieParam("sessId") String sessionId){

        try {
            //check if cookie was sent
            if(sessionId == null){
                return Response.status(403).entity("Session expired").build();
            }

            Integer userId = Session.validateSession(sessionId);

            if(userId != null){

                if(groupMember.removeGroupMember()){
                    return Response.status(200).entity("Success").build();
                }
                else{
                    String s = "Failure";
                    return Response.status(400).entity(s).build();
                }
            }
            else{
                throw new Exception("Could not remove a user from group for session: " + sessionId);
            }

        } catch (Exception e) {
            String s = "Failure";
            return Response.status(400).entity(s).build();
        }
    }

    /**get all playlists of a group
     * {id: required}
     *
     *
     */
    @POST
    @Path("/getAllPlaylists")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response seeAllPlaylists(Group group, @CookieParam("sessId") String sessionId){


        try {
            //check if cookie was sent
            if (sessionId == null || Session.validateSession(sessionId) == null) {
                return Response.status(403).entity("Session expired").build();
            }

                List<Playlist> playlists = group.seeAllPlaylists();

                return Response.status(200).entity(playlists).build();

        } catch (Exception e) {
            String s = "Failure";
            return Response.status(400).entity(s).build();
        }

    }

    /**get all queue songs of a group
     * {id: required}
     *
     *
     */
    @POST
    @Path("/getAllQueueSongs")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response seeAllQueueSongs(Group group, @CookieParam("sessId") String sessionId){


        try {
            //check if cookie was sent
            if (sessionId == null || Session.validateSession(sessionId) == null) {
                return Response.status(403).entity("Session expired").build();
            }

            List<Song> songs = group.seeAllSongs();

            return Response.status(200).entity(songs).build();

        } catch (Exception e) {
            String s = "Failure";
            return Response.status(400).entity(s).build();
        }

    }

    /**get all queue songs of a group
     * {id: required}
     *
     *
     */
    @POST
    @Path("/getNextQueueSong")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response seeNextQueueSong(Group group, @CookieParam("sessId") String sessionId){


        try {
            //check if cookie was sent
            if (sessionId == null || Session.validateSession(sessionId) == null) {
                return Response.status(403).entity("Session expired").build();
            }

            Song song = group.nextQueueSong();

            return Response.status(200).entity(song).build();

        } catch (Exception e) {
            String s = "Failure";
            return Response.status(400).entity(s).build();
        }

    }

}
