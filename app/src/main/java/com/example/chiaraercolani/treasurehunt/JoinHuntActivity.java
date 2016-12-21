package com.example.chiaraercolani.treasurehunt;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class JoinHuntActivity extends AppCompatActivity {

    List<File> files;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_hunt);
        Toolbar toolbar = (Toolbar) findViewById(R.id.join_hunt_activity_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        files = getListFiles(getApplicationContext().getFilesDir());

        final ListView listview =(ListView) findViewById(R.id.join_hunt_list);
        files = getListFiles(this.getFilesDir());

        final ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, files);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(JoinHuntItemClickListener);




    }

    private AdapterView.OnItemClickListener JoinHuntItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent();
            intent.setClass(JoinHuntActivity.this, JoinedHuntStartActivity.class);
            intent.putExtra("filename", files.get(position).getAbsolutePath());
            startActivity(intent);
        }

    };

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


}
