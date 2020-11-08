package it.pgp.multicastexample.receiver;

import android.app.Activity;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.io.IOException;
import java.net.DatagramSocket;

public abstract class GenericReceiver extends Thread {

    final DatagramSocket socket;
    final byte[] buf = new byte[256];
    final Activity activity;
    final ArrayAdapter<String> adapter;

    protected GenericReceiver(DatagramSocket socket, Activity activity, ArrayAdapter<String> adapter) {
        this.socket = socket;
        this.activity = activity;
        this.adapter = adapter;
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
