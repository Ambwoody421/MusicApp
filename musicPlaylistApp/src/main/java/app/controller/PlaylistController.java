package app.controller;

import app.model.Playlist;
import app.model.PlaylistSong;
import app.model.Session;
import app.model.Song;
import org.springframework.web.bind.annotation.RequestBody;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("playlist")
@Produces(MediaType.APPLICATION_JSON)
public class PlaylistController {

    /**create a playlist
     * {groupId: required, name: required}
     *
     * @param playlist
     * @param sessionId
     * @return
     */
    @POST
    @Path("/createPlaylist")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createPlaylist(Playlist playlist, @CookieParam("sessId") String sessionId){


        try {
            //check if cookie was sent
            if (sessionId == null || Session.validateSession(sessionId) == null) {
                return Response.status(403).entity("Session expired").build();
            }

            if(playlist.insertGroupPlaylist()){
                return Response.status(200).entity(playlist).build();
            }
            else{
                throw new Exception("Could not create Playlist");
            }


        } catch (Exception e) {
            String s = "Failure";
            return Response.status(400).entity(s).build();
        }

    }

    /**add song to a playlist
     * {playlistId: required, songId: required}
     *
     *
     */
    @POST
    @Path("/addSong")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addSong(PlaylistSong playlistSong, @CookieParam("sessId") String sessionId){


        try {
            //check if cookie was sent
            if (sessionId == null || Session.validateSession(sessionId) == null) {
                return Response.status(403).entity("Session expired").build();
            }

            if(playlistSong.addSongDB()){
                return Response.status(200).entity(playlistSong).build();
            }
            else{
                throw new Exception("Could not add song to playlist");
            }


        } catch (Exception e) {
            String s = "Failure";
            return Response.status(400).entity(s).build();
        }

    }

    /**add song to a playlist
     * {playlistId: required, songId: required}
     *
     *
     */
    @POST
    @Path("/deleteSong")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteSong(PlaylistSong playlistSong, @CookieParam("sessId") String sessionId){


        try {
            //check if cookie was sent
            if (sessionId == null || Session.validateSession(sessionId) == null) {
                return Response.status(403).entity("Session expired").build();
            }

            if(playlistSong.addSongDB()){
                return Response.status(200).entity(playlistSong).build();
            }
            else{
                throw new Exception("Could not add song to playlist");
            }


        } catch (Exception e) {
            String s = "Failure";
            return Response.status(400).entity(s).build();
        }

    }




}
