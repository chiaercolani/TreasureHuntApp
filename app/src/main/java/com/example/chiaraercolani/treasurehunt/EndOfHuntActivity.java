package com.example.chiaraercolani.treasurehunt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class EndOfHuntActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_of_hunt);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.setClass(EndOfHuntActivity.this, StartMenuActivity.class);
        startActivity(intent);
    }
}
