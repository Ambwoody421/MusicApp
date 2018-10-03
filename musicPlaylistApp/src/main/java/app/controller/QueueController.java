package app.controller;

import app.model.Group;
import app.model.QueueSong;
import app.model.Session;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("queue")
@Produces(MediaType.APPLICATION_JSON)
public class QueueController {

    /**
     * Add song to a group queue
     * {groupId: required, songId: required}
     *
     * @return
     */
    @POST
    @Path("/addSong")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addQueueSong(QueueSong queueSong, @CookieParam("sessId") String sessionId) {


        try {
            //check if cookie was sent
            if (sessionId == null || Session.validateSession(sessionId) == null) {
                return Response.status(403).entity("Session expired").build();
            }

            if (queueSong.addSongDB()) {
                return Response.status(200).entity(queueSong).build();
            } else {
                throw new Exception("Could not create Playlist");
            }


        } catch (Exception e) {
            String s = "Failure";
            return Response.status(400).entity(s).build();
        }

    }

    /**
     * Delete a song from group queue
     * {groupId: required, songId: required}
     *
     * @return
     */
    @POST
    @Path("/deleteSong")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteQueueSong(QueueSong queueSong, @CookieParam("sessId") String sessionId) {


        try {
            //check if cookie was sent
            if (sessionId == null || Session.validateSession(sessionId) == null) {
                return Response.status(403).entity("Session expired").build();
            }

            if (queueSong.deleteSongDB()) {
                return Response.status(200).entity(queueSong).build();
            } else {
                throw new Exception("Could not delete queue song");
            }


        } catch (Exception e) {
            String s = "Failure";
            return Response.status(400).entity(s).build();
        }

    }

}
