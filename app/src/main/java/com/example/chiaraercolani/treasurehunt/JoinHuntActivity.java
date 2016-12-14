package com.example.chiaraercolani.treasurehunt;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class JoinHuntActivity extends AppCompatActivity {

    private List<File> files;
    private HuntFileArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_hunt);
        Toolbar toolbar = (Toolbar) findViewById(R.id.join_hunt_activity_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        files = getListFiles(new File("/sdcard/Android/data/com.example.chiaraercolani.treasurehunt"));

        final ListView listview =(ListView) findViewById(R.id.join_hunt_list);

        adapter = new HuntFileArrayAdapter(this, R.layout.hunt_file_list_item, files);
        listview.setAdapter(adapter);
        adapter.notifyDataSetChanged();



    }


    private List<File> getListFiles(File parentDir) {
        ArrayList<File> inFiles = new ArrayList<File>();
        File[] files = parentDir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                inFiles.addAll(getListFiles(file));
            } else {
                if(file.getName().endsWith(".txt")){
                    inFiles.add(file);
                }
            }
        }
        return inFiles;
    }

    private class HuntFileArrayAdapter extends ArrayAdapter {

        private Context context;

        public HuntFileArrayAdapter(Context context, int resource, List<File> objects) {
            super(context, resource, objects);
            this.context = context;
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent){
            File file = files.get(position);

            View v = convertView;
            if (v==null){
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(R.layout.hunt_file_list_item, null);
            }

            ((TextView) v.findViewById(R.id.hunt_file_list_item_name)).setText(file.getName());

            return v;
        }
    };

}
