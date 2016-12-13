package com.example.chiaraercolani.treasurehunt;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class CreateHuntActivity extends AppCompatActivity {

    private final static int PICK_STEP_POSITION_ACTIVITY_REQUEST_CODE = 0;
    private Hunt onBuildingHunt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_hunt);

        Toolbar toolbar = (Toolbar) findViewById(R.id.create_hunt_activity_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String huntName = getIntent().getStringExtra(OnBuildingHuntActivity.HUNT_NAME_EXTRA);
        onBuildingHunt = new Hunt(huntName, System.currentTimeMillis());
        getSupportActionBar().setTitle(huntName);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.create_hunt_option_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.create_new_step :
                DialogFragment dialogFragment = new CreateNewStepDialogFragment();
                dialogFragment.show(getSupportFragmentManager(), "create new step");
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case PICK_STEP_POSITION_ACTIVITY_REQUEST_CODE:
                //data.getAction()
        }
    }

    public static class CreateNewStepDialogFragment extends DialogFragment {

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            LayoutInflater inflater = getActivity().getLayoutInflater();

            View view = inflater.inflate(R.layout.add_step_dialog, null);
            ((Button)view.findViewById(R.id.pick_step_position_button)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), PickStepPositionActivity.class);
                    startActivityForResult(intent, PICK_STEP_POSITION_ACTIVITY_REQUEST_CODE);
                }
            });

            builder.setView(view);


            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    CreateNewStepDialogFragment.this.getDialog().cancel();
                }
            });

            return builder.create();
        }

    }

}
