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
import java.lang.reflect.Array;
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

        HuntDirectoryReader huntDirectoryReader = new HuntDirectoryReader(getApplicationContext().getFilesDir());
        files = huntDirectoryReader.getHuntFileList();

        final ListView listview =(ListView) findViewById(R.id.join_hunt_list);

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

}
