package it.pgp.multicastexample;

import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MulticastReceiver extends Thread {
    protected MulticastSocket socket = null;
    protected byte[] buf = new byte[256];

    public void run() {
        try {
            socket = new MulticastSocket(11111);
            InetAddress group = InetAddress.getByName("FF02::1");
            socket.joinGroup(group);
            for(;;) {
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                String received = new String(
                        packet.getData(), 0, packet.getLength());
                Log.e("MULTICAST", received);
                MainActivity.instance.runOnUiThread(()-> MainActivity.instance.messageView.setText(received));

                if ("end".equals(received)) {
                    break;
                }
            }
            socket.leaveGroup(group);
            socket.close();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }
}
