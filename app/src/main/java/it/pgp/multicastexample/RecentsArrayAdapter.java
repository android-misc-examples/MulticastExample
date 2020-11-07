package it.pgp.multicastexample;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

public class RecentsArrayAdapter extends ArrayAdapter<String> {

    final ArrayList<String> objects;

    public RecentsArrayAdapter(@NonNull Context context, int resource, @NonNull List<String> objects) {
        super(context, resource, objects);
        this.objects = (ArrayList<String>) objects;
    }

    @Override
    public void add(@Nullable String object) {
        if(objects.size()==5) {
            objects.remove(0);
        }
        super.add(object);
    }
}
