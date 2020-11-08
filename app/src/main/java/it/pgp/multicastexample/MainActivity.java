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
import it.pgp.multicastexample.sender.GenericSender;
import it.pgp.multicastexample.sender.MulticastSender;

public class MainActivity extends AppCompatActivity {

    public GenericReceiver receiver;
    public GenericSender sender;

    public ListView received;
    public ArrayAdapter<String> receivedAdapter;

    public ListView sent;
    public ArrayAdapter<String> sentAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        instance = this;
        setContentView(R.layout.activity_main);
        received = findViewById(R.id.received);
        sent = findViewById(R.id.sent);

        receivedAdapter = new RecentsArrayAdapter(this, android.R.layout.simple_list_item_1, new ArrayList<>());
        received.setAdapter(receivedAdapter);

        sentAdapter = new RecentsArrayAdapter(this, android.R.layout.simple_list_item_1, new ArrayList<>());
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

    public void startReceiver(View view) {
        if(receiver != null) receiver.interrupt();
        try {
//            receiver = new UDPBroadcastReceiver();
            receiver = new MulticastReceiver(this, receivedAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        receiver.start();
        Toast.makeText(this, "Receiver started", Toast.LENGTH_SHORT).show();
    }

    public void startSender(View view) {
        if(sender != null) sender.interrupt();
        try {
            sender = new MulticastSender(this, sentAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        sender.start();
        Toast.makeText(this, "Sender started", Toast.LENGTH_SHORT).show();
    }
}
