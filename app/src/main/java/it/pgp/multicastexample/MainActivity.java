package it.pgp.multicastexample;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import it.pgp.multicastexample.receiver.GenericReceiver;
import it.pgp.multicastexample.receiver.MulticastReceiver;
import it.pgp.multicastexample.receiver.UDPBroadcastReceiver;
import it.pgp.multicastexample.sender.GenericSender;
import it.pgp.multicastexample.sender.MulticastSender;
import it.pgp.multicastexample.sender.UDPBroadcastSender;

public class MainActivity extends AppCompatActivity {

    public GenericReceiver receiver;
    public GenericSender sender;
    public GenericReceiver UDPreceiver;
    public GenericSender UDPsender;

    public ListView received;
    public ArrayAdapter<String> receivedAdapter;

    public ListView sent;
    public ArrayAdapter<String> sentAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        received = findViewById(R.id.received);
        sent = findViewById(R.id.sent);

        receivedAdapter = new RecentsArrayAdapter(this, R.layout.listitem_received, new ArrayList<>());
        received.setAdapter(receivedAdapter);

        sentAdapter = new RecentsArrayAdapter(this, R.layout.listitem_sent, new ArrayList<>());
        sent.setAdapter(sentAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        findViewById(R.id.startIpv6Receiver).setEnabled(true);
        findViewById(R.id.startIpv6Sender).setEnabled(true);
        findViewById(R.id.startUdpReceiver).setEnabled(true);
        findViewById(R.id.startUdpSender).setEnabled(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(receiver != null) receiver.cleanup();
        if(sender != null) sender.interrupt();
        if(UDPreceiver != null) UDPreceiver.cleanup();
        if(UDPsender != null) UDPsender.interrupt();
    }

    public static boolean checkThreadState(Thread t) {
        return t != null && t.getState() != Thread.State.NEW && t.getState() != Thread.State.TERMINATED;
    }

    public void startIPv6Receiver() {
        if(checkThreadState(receiver)) {
            Toast.makeText(this, "IPv6 receiver already running", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            receiver = new MulticastReceiver(this, receivedAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        receiver.start();
        Toast.makeText(this, "Receiver started", Toast.LENGTH_SHORT).show();
    }

    public void startIPv6Sender() {
        if(checkThreadState(sender)) {
            Toast.makeText(this, "IPv6 sender already running", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            sender = new MulticastSender(this, sentAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        sender.start();
        Toast.makeText(this, "Sender started", Toast.LENGTH_SHORT).show();
    }

    public void startUDPReceiver() {
        if(checkThreadState(UDPreceiver)) {
            Toast.makeText(this, "UDP receiver already running", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            UDPreceiver = new UDPBroadcastReceiver(this, receivedAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        UDPreceiver.start();
        Toast.makeText(this, "UDP Receiver started", Toast.LENGTH_SHORT).show();
    }

    public void startUDPSender() {
        if(checkThreadState(UDPsender)) {
            Toast.makeText(this, "UDP sender already running", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            UDPsender = new UDPBroadcastSender(this, sentAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        UDPsender.start();
        Toast.makeText(this, "UDP Sender started", Toast.LENGTH_SHORT).show();
    }

    public void startThread(View v) {
        switch(v.getId()) {
            case R.id.startIpv6Sender:
                startIPv6Sender();
                break;
            case R.id.startIpv6Receiver:
                startIPv6Receiver();
                findViewById(R.id.startUdpReceiver).setEnabled(false); // cannot bind two UDP sockets on same port
                break;
            case R.id.startUdpSender:
                startUDPSender();
                break;
            case R.id.startUdpReceiver:
                startUDPReceiver();
                findViewById(R.id.startIpv6Receiver).setEnabled(false); // cannot bind two UDP sockets on same port
                break;
        }
        v.setEnabled(false);
    }
}
