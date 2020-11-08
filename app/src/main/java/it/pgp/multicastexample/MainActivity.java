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
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(receiver != null) receiver.interrupt();
        if(sender != null) sender.interrupt();
    }

    public void startIPv6Receiver() {
        if(receiver != null) receiver.interrupt();
        try {
            receiver = new MulticastReceiver(this, receivedAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        receiver.start();
        Toast.makeText(this, "Receiver started", Toast.LENGTH_SHORT).show();
    }

    public void startIPv6Sender() {
        if(sender != null) sender.interrupt();
        try {
            sender = new MulticastSender(this, sentAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        sender.start();
        Toast.makeText(this, "Sender started", Toast.LENGTH_SHORT).show();
    }

    public void startUDPReceiver() {
        if(UDPreceiver != null) UDPreceiver.interrupt();
        try {
            UDPreceiver = new UDPBroadcastReceiver(this, receivedAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        UDPreceiver.start();
        Toast.makeText(this, "UDP Receiver started", Toast.LENGTH_SHORT).show();
    }

    public void startUDPSender() {
        if(UDPsender != null) UDPsender.interrupt();
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
                break;
            case R.id.startUdpSender:
                startUDPSender();
                break;
            case R.id.startUdpReceiver:
                startUDPReceiver();
                break;
        }
    }
}
