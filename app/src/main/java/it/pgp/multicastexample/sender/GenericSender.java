package it.pgp.multicastexample.sender;

import android.app.Activity;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.io.IOException;
import java.net.DatagramSocket;
import java.security.SecureRandom;

/**
 * Web sources:
 * https://stackoverflow.com/questions/20059106/ipv6-multicast-example
 * https://fedidat.com/440-what-is-multicast-java/
 */

public abstract class GenericSender extends Thread {

    static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    SecureRandom rnd = new SecureRandom();

    String randomString(int len){
        StringBuilder sb = new StringBuilder( len );
        for( int i = 0; i < len; i++ )
            sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
        return sb.toString();
    }

    final DatagramSocket socket;
    final Activity activity;
    final ArrayAdapter<String> adapter;

    protected GenericSender(DatagramSocket socket, Activity activity, ArrayAdapter<String> adapter) {
        this.socket = socket;
        this.activity = activity;
        this.adapter = adapter;
    }

    public abstract void send() throws IOException;
    public abstract void cleanup();

    @Override
    public void run() {
        Log.d("GenericSender","Starting sender thread");
        try {
            for(;;) {
                send();
                sleep(1000);
            }
        }
        catch(IOException | InterruptedException e) {
            if(!(e instanceof InterruptedException))
                e.printStackTrace();
        }
        finally {
            cleanup();
            Log.d("GenericSender","Exiting receiver thread now");
        }
    }
}
