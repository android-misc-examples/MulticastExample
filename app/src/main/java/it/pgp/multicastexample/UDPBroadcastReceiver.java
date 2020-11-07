package it.pgp.multicastexample;

import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

public class UDPBroadcastReceiver extends GenericReceiver {

    protected UDPBroadcastReceiver() throws SocketException {
        super(new DatagramSocket(11111));
    }

    @Override
    public void receive() throws IOException {
        DatagramPacket data = new DatagramPacket(new byte[256], 256);
        socket.receive(data);
        String received = new String(data.getData(), data.getOffset(), data.getLength(), StandardCharsets.UTF_8);
        Log.e(getClass().getName(),received);
//        MainActivity.instance.runOnUiThread(()-> MainActivity.instance.messageView.setText(received + " " + System.currentTimeMillis()));
    }

    @Override
    public void cleanup() {
        socket.close();
    }
}
