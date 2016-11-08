package com.example.chiaraercolani.treasurehunt;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class CreateHuntActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_hunt);

        ArrayList<String> stringArray = new ArrayList<String>();
        stringArray.add("hunt1");
        stringArray.add("hunt2");
        stringArray.add("hunt3");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, stringArray);
        ListView listView = (ListView) findViewById(R.id.on_building_hunt_list);
        listView.setAdapter(adapter);
    }
}
