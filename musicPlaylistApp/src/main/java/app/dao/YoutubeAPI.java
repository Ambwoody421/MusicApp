package app.dao;

import app.config.MyLog;
import app.model.Song;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;


public class YoutubeAPI {

    public static String getVideoTitle(String videoId, String key){

        StringBuilder sb = new StringBuilder();

        try {
            String api = "https://www.googleapis.com/youtube/v3/videos?part=snippet&id=" + videoId + "&key=" + key;

            URLConnection con = new URL(api).openConnection();

            InputStream inputStream = con.getInputStream();

            Scanner scanner = new Scanner(inputStream);


            while(scanner.hasNext()){

                sb.append(scanner.nextLine());
            }
        } catch (IOException e) {
            MyLog.logException(e);
        }


        JSONObject obj = new JSONObject(sb.toString());

        JSONObject getVideo = obj.getJSONArray("items").getJSONObject(0);

        JSONObject videoDetails = getVideo.getJSONObject("snippet");

        return videoDetails.getString("title");

    }

    public static boolean downloadSong(Song song, String baseFilePath) {

        String download_path = baseFilePath;

        // if artist directory does not exist, make one
        // Windows
        // File directory = new File(download_path + "\\" + song.getArtist().substring(0,1).toUpperCase() + "\\" + song.getArtist());

        File directory = new File(download_path + "/" + song.getArtist().substring(0,1).toUpperCase() + "/" + song.getArtist());
        if(!directory.exists()){
            if(!directory.mkdir()){
                return false;
            }
        }

        //check if song already exists
        // windows
        // File newSong = new File(baseFilePath + "\\" + song.getFilepath());

        File newSong = new File(baseFilePath + "/" + song.getFilepath());
        if(newSong.exists()) {
            return false;
        }

        /* windows
        String[] command =
                {
                        "cmd",
                };
                */
        // linux
        String[] command =
                {
                        "/bin/sh"
                };

        Process p;
        try {
            p = Runtime.getRuntime().exec(command);
            new Thread(new SyncPipe(p.getErrorStream(), System.err)).start();
            new Thread(new SyncPipe(p.getInputStream(), System.out)).start();
            PrintWriter stdin = new PrintWriter(p.getOutputStream());
            stdin.println("cd \""+directory+"\"");

             // windows
            // stdin.println("\"" + baseFilePath + "\\\"" + "youtube-dl.exe --extract-audio --audio-format mp3 "+song.getUrl());

            // Linux
            stdin.println("youtube-dl --extract-audio --audio-format mp3 "+song.getUrl());
            stdin.close();
            p.waitFor();

            //check to see if song was in fact downloaded
            if(!newSong.exists()){
                MyLog.logMessage("Something went wrong. Could not download song.");
                return false;
            }

            // if made it this far, song must have downloaded successfully
            MyLog.logMessage("Successfully downloaded song: " + song.getUrl());
            return true;
        } catch (Exception e) {
            MyLog.logException(e);
            e.printStackTrace();
            return false;
        }
    }
}
