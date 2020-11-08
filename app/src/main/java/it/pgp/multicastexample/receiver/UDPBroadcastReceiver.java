package it.pgp.multicastexample.receiver;

import android.app.Activity;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

public class UDPBroadcastReceiver extends GenericReceiver {

    public UDPBroadcastReceiver(Activity activity, ArrayAdapter<String> adapter) throws SocketException {
        super(new DatagramSocket(11111),activity,adapter);
    }

    @Override
    public void receive() throws IOException {
        DatagramPacket data = new DatagramPacket(new byte[256], 256);
        socket.receive(data);
        String received = new String(data.getData(), data.getOffset(), data.getLength(), StandardCharsets.UTF_8);
        Log.e(getClass().getName(),received);
        activity.runOnUiThread(()-> {
            adapter.add(received);
            adapter.notifyDataSetChanged();
        });
    }

    @Override
    public void cleanup() {
        socket.close();
    }
}
