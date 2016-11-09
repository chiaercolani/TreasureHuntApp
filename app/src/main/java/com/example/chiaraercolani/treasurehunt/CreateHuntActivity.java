package com.example.chiaraercolani.treasurehunt;


import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

public class CreateHuntActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_hunt);

        Toolbar toolbar = (Toolbar) findViewById(R.id.create_hunt_activity_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        ArrayList<String> stringArray = new ArrayList<String>();
//        stringArray.add("hunt1");
//        stringArray.add("hunt2");
//        stringArray.add("hunt3");
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, stringArray);
//        ListView listView = (ListView) findViewById(R.id.on_building_hunt_list);
//        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.create_hunt_otpion_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.create_new_hunt :
                DialogFragment dialogFragment = new CreateNewHuntDialogFragment();
                dialogFragment.show(getSupportFragmentManager(), "create new hunt");
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void createHunt(String name){
        Long ID = System.currentTimeMillis(); //TODO replace it by the database key
        Hunt hunt = new Hunt(name, ID);
    }

    public static class CreateNewHuntDialogFragment extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            LayoutInflater inflater = getActivity().getLayoutInflater();

            builder.setView(inflater.inflate(R.layout.create_hunt_dialog, null));

            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    EditText huntNameEditText = (EditText) getView().findViewById(R.id.new_hunt_name);
                    String newHuntName = huntNameEditText.getText().toString();
                    //TODO start the activity to complete the hiunt

                }
            });
            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    CreateNewHuntDialogFragment.this.getDialog().cancel();
                }
            });

            return builder.create();
        }
    }

}

