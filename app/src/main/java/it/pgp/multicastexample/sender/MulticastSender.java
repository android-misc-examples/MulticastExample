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

    private final Activity activity;
    private final ArrayAdapter<String> adapter;
    InetAddress group;

    static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    SecureRandom rnd = new SecureRandom();

    String randomString(int len){
        StringBuilder sb = new StringBuilder( len );
        for( int i = 0; i < len; i++ )
            sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
        return sb.toString();
    }

    public MulticastSender(Activity activity, ArrayAdapter<String> adapter) throws IOException {
        super(new DatagramSocket());
        this.activity = activity;
        this.adapter = adapter;
        group = InetAddress.getByName("FF02::1");
    }

    @Override
    public void send() throws IOException {
        String s = randomString(30);
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
