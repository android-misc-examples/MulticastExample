package it.pgp.multicastexample;

import android.app.Activity;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MulticastReceiver extends GenericReceiver {

    private final Activity activity;
    private final ArrayAdapter<String> adapter;
    InetAddress group;

    public MulticastReceiver(Activity activity, ArrayAdapter<String> adapter) throws IOException {
        super(new MulticastSocket(11111));
        this.activity = activity;
        this.adapter = adapter;
        group = InetAddress.getByName("FF02::1");
        ((MulticastSocket)socket).joinGroup(group);
    }

    @Override
    public void receive() throws IOException {
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        socket.receive(packet);
        String received = new String(
                packet.getData(), 0, packet.getLength());
        Log.e("MULTICAST", received);
        activity.runOnUiThread(()-> {
            adapter.add(received);
            adapter.notifyDataSetChanged();
        });
    }

    @Override
    public void cleanup() {
        try {
            ((MulticastSocket)socket).leaveGroup(group);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        socket.close();
    }
}
