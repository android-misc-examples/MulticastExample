package it.pgp.multicastexample.sender;

import android.util.Log;

import java.io.IOException;
import java.net.DatagramSocket;

/**
 * Web sources:
 * https://stackoverflow.com/questions/20059106/ipv6-multicast-example
 * https://fedidat.com/440-what-is-multicast-java/
 */

public abstract class GenericSender extends Thread {

    final DatagramSocket socket;

    protected GenericSender(DatagramSocket socket) {
        this.socket = socket;
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
