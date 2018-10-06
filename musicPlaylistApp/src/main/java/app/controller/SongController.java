package app.controller;

import app.dao.YoutubeAPI;
import app.model.Artist;
import app.model.Session;
import app.model.Song;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.io.File;
import java.util.List;

@Path("song")
public class SongController {

    /**Adds new song in the database via a youtube video url and a custom artist field
     *
     * {url: required, artist: required}
     */
    @POST
    @Path("/addNewSong")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addSong(Song song, @CookieParam("sessId") String sessionId){

        System.out.println(song.getUrl());
        System.out.println(song.getArtist());

        try {
            //check if cookie was sent
            if(sessionId == null ||  Session.validateSession(sessionId) == null){
                return Response.status(403).entity("Session expired").build();
            }

            String[] temp = song.getUrl().split("=");
            String videoId = temp[temp.length-1];

            String title = YoutubeAPI.getVideoTitle(videoId);
            song.setTitle(title);
            song.setFilepath(title + "-" + videoId + ".mp4");

            if(!YoutubeAPI.downloadSong(song.getUrl())) {
                throw new Exception("Could not download new song");
            }

            if(song.insertNewSong()){
                return Response.status(200).entity(song).build();
            }
            else{
                throw new Exception("Could not create new song");
            }

        } catch (Exception e) {
            String s = "Failure";
            return Response.status(400).entity(s).build();
        }

    }

    /**Retrieve song by Title
     *
     * {title: required}
     */
    @POST
    @Path("/getSong")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveSong(Song song, @CookieParam("sessId") String sessionId){


        try {
            //check if cookie was sent
            if(sessionId == null ||  Session.validateSession(sessionId) == null){
                return Response.status(403).entity("Session expired").build();
            }

            song.retrieveSongByTitle();

            return Response.status(200).entity(song).build();

        } catch (Exception e) {
            String s = "Failure";
            return Response.status(400).entity(s).build();
        }
    }

    /**Retrieve song by Title
     *
     * {filepath: required}
     */
    @POST
    @Path("/getSongAudio")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response retrieveSongBlob(Song song, @CookieParam("sessId") String sessionId){

        try {

            //check if cookie was sent
            if(sessionId == null ||  Session.validateSession(sessionId) == null){
                return Response.status(403).entity("Session expired").build();
            }

            System.out.println(song.getFilepath());


            File audioFile = song.retrieveSongAudio();

            return Response.ok(audioFile, "application/octet-stream").build();

        } catch (Exception e) {
            String s = "Failure";
            return Response.status(400).entity(s).build();
        }
    }

    /**Retrieve all songs
     *
     *
     */
    @GET
    @Path("/getAllSongs")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllSong(@CookieParam("sessId") String sessionId){


        try {
            //check if cookie was sent
            if(sessionId == null ||  Session.validateSession(sessionId) == null){
                return Response.status(403).entity("Session expired").build();
            }

            List<Song> songList = Song.retrieveAllSongs();

            return Response.status(200).entity(songList).build();

        } catch (Exception e) {
            String s = "Failure";
            return Response.status(400).entity(s).build();
        }
    }

    /**Retrieve all songs by Artist
     *
     *
     */
    @GET
    @Path("/getAllSongsByArtist")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveSongsByArtist(@CookieParam("sessId") String sessionId, @QueryParam("artist") Integer artist){


        try {
            //check if cookie was sent
            if(sessionId == null ||  Session.validateSession(sessionId) == null){
                return Response.status(403).entity("Session expired").build();
            }

            List<Song> songList = Song.retrieveSongsByArtist(artist);

            return Response.status(200).entity(songList).build();

        } catch (Exception e) {
            String s = "Failure";
            return Response.status(400).entity(s).build();
        }
    }

    /**Retrieve all Artists
     *
     *
     */
    @GET
    @Path("/getAllArtists")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllArtists(@CookieParam("sessId") String sessionId){

        try {
            //check if cookie was sent
            if(sessionId == null ||  Session.validateSession(sessionId) == null){
                return Response.status(403).entity("Session expired").build();
            }

            List<Artist> allArtists = Artist.retrieveAllArtists();

            return Response.status(200).entity(allArtists).build();

        } catch (Exception e) {
            String s = "Failure";
            return Response.status(400).entity(s).build();
        }
    }

}
