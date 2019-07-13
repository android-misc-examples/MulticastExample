package it.pgp.multicastexample;

import android.util.Log;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.charset.StandardCharsets;

public class UDPBroadcastReceiver extends Thread {
    private final int port;

    public UDPBroadcastReceiver(int port )
    {
        this.port = port;
    }

    public UDPBroadcastReceiver() {
        this.port = 11111;
    }

    @Override
    public void run()
    {
        try {
            DatagramSocket dsock = new DatagramSocket(port);
            for(;;) {
                DatagramPacket data = new DatagramPacket(new byte[256], 256);
                dsock.receive(data);
                String received = new String(data.getData(), data.getOffset(), data.getLength(), StandardCharsets.UTF_8);
                Log.e(getClass().getName(),received);
                MainActivity.instance.runOnUiThread(()-> MainActivity.instance.messageView.setText(received + " " + System.currentTimeMillis()));
            }
        }
        catch( Exception ex ) {
            ex.printStackTrace();
        }
    }
}
