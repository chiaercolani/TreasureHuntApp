package com.example.chiaraercolani.treasurehunt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class StartMenuActivity extends AppCompatActivity {

    private final static int CREATE_ACTIVITY=0;
    private final static int JOIN_ACTIVITY=1;
    private final static int SETTINGS_ACTIVITY=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_menu);
    }

    @Override
    public void onBackPressed(){
        finish();
        System.exit(0);
    }

    public void buttonClicked(View view){
        Intent intent;
        switch(view.getId()){
            case R.id.btn_create:
                intent = new Intent(this,OnBuildingHuntActivity.class);
                startActivityForResult(intent,CREATE_ACTIVITY);
                break;
            case R.id.btn_join:
                intent = new Intent(this,JoinHuntActivity.class);
                startActivityForResult(intent,JOIN_ACTIVITY);
                break;
            case R.id.btn_settings:
                intent = new Intent(this,SettingsActivity.class);
                startActivityForResult(intent,SETTINGS_ACTIVITY);
                break;
        }


    }
}
