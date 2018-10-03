package app.dao;

import app.config.MyLog;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Paths;
import java.util.Scanner;

public class YoutubeAPI {


    public static String getVideoTitle(String videoId){

        StringBuilder sb = new StringBuilder();

        try {
            URL resource = YoutubeAPI.class.getResource("/Key.txt");
            File file = Paths.get(resource.toURI()).toFile();
            Scanner scan = new Scanner(file);
            String key = scan.nextLine();
            System.out.println(key);
            String api = "https://www.googleapis.com/youtube/v3/videos?part=snippet&id="+videoId+"&key="+key;

            URLConnection con = new URL(api).openConnection();

            InputStream inputStream = con.getInputStream();

            Scanner scanner = new Scanner(inputStream);


            while(scanner.hasNext()){

                sb.append(scanner.nextLine());
            }
        } catch (IOException e) {
            MyLog.logException(e);
        } catch (URISyntaxException x) {
            MyLog.logException(x);
        }


        JSONObject obj = new JSONObject(sb.toString());

        JSONObject getVideo = obj.getJSONArray("items").getJSONObject(0);

        JSONObject videoDetails = getVideo.getJSONObject("snippet");

        String title = videoDetails.getString("title");


        return title;

    }

    public static boolean downloadSong(String url) {
        String download_path="C:\\Users\\Alex Bartsch\\Documents\\MusicAppSongs";

        String[] command =
                {
                        "cmd",
                };
        Process p;
        try {
            p = Runtime.getRuntime().exec(command);
            new Thread(new SyncPipe(p.getErrorStream(), System.err)).start();
            new Thread(new SyncPipe(p.getInputStream(), System.out)).start();
            PrintWriter stdin = new PrintWriter(p.getOutputStream());
            stdin.println("cd \""+download_path+"\"");
            stdin.println("youtube-dl "+url);
            stdin.close();
            p.waitFor();
            MyLog.logMessage("Successfully downloaded song: " + url);
            return true;
        } catch (Exception e) {
            MyLog.logException(e);
            e.printStackTrace();
            return false;
        }
    }
}
