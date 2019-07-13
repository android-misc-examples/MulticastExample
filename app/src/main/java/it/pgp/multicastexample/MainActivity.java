package it.pgp.multicastexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public TextView messageView;
    public static MainActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        setContentView(R.layout.activity_main);
        messageView = findViewById(R.id.messageView);
//        new MulticastReceiver().start();
        new UDPBroadcastReceiver().start();
    }
}
