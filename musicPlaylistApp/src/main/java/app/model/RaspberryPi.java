package app.model;

import java.io.IOException;

public class RaspberryPi {

    public static void shutdown() {
        try {
            Process p = Runtime.getRuntime().exec("sudo shutdown -h now");
            p.waitFor();
        } catch (InterruptedException i){
            i.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
