package it.pgp.multicastexample.sender;

import android.app.Activity;
import android.widget.ArrayAdapter;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPBroadcastSender extends GenericSender {

    InetAddress address;

    public UDPBroadcastSender(Activity activity, ArrayAdapter<String> adapter) throws IOException {
        super(new DatagramSocket(), activity, adapter);
        socket.setBroadcast(true);
        address = InetAddress.getByName("255.255.255.255");
    }

    @Override
    public void send() throws IOException {
        String s = "UDP"+randomString(30);
        byte[] b = s.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(b, b.length, address, 11111);
        socket.send(sendPacket);
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
