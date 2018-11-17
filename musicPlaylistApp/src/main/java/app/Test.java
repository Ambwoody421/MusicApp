package app;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class Test {

    public static void main(String[] args){

        Enumeration e = null;
        try {
            e = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e1) {
            e1.printStackTrace();
        }
        while(e.hasMoreElements())
        {
            NetworkInterface n = (NetworkInterface) e.nextElement();
            Enumeration ee = n.getInetAddresses();
            while (ee.hasMoreElements())
            {

                InetAddress i = (InetAddress) ee.nextElement();

                if(i.getHostAddress().startsWith("192")){
                    System.out.println(i.getHostAddress());
                }

            }
        }
    }
}
