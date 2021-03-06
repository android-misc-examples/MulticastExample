package it.pgp.multicastexample.sender;

import android.app.Activity;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.security.SecureRandom;

public class MulticastSender extends GenericSender {

    InetAddress group;

    public MulticastSender(Activity activity, ArrayAdapter<String> adapter) throws IOException {
        super(new DatagramSocket(), activity, adapter);
        group = InetAddress.getByName("FF02::1");
    }

    @Override
    public void send() throws IOException {
        String s = "IPv6"+randomString(30);
        byte[] b = s.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(b, b.length, group, 11111);
        socket.send(sendPacket);
        Log.d("MulticastSender", "sent: "+s);
        activity.runOnUiThread(()-> {
            adapter.add(s);
            adapter.notifyDataSetChanged();
        });
    }

    @Override
    public void cleanup() {
        socket.close();
    }
}
