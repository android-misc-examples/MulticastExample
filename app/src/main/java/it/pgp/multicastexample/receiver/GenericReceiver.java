package it.pgp.multicastexample.receiver;

import android.util.Log;

import java.io.IOException;
import java.net.DatagramSocket;

public abstract class GenericReceiver extends Thread {

    final DatagramSocket socket;
    final byte[] buf = new byte[256];

    protected GenericReceiver(DatagramSocket socket) {
        this.socket = socket;
    }

    public abstract void receive() throws IOException;
    public abstract void cleanup();

    @Override
    public void run() {
        Log.d("GenericReceiver","Starting receiver thread");
        try {
            for(;;) {
                receive();
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        finally {
            cleanup();
            Log.d("GenericReceiver","Exiting receiver thread now");
        }
    }
}
