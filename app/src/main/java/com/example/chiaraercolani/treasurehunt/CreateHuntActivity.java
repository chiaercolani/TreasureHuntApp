package com.example.chiaraercolani.treasurehunt;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CreateHuntActivity extends AppCompatActivity {

    private final static int PICK_STEP_POSITION_ACTIVITY_REQUEST_CODE = 1;

    private Hunt onBuildingHunt;
    private DialogFragment newStepDialog;

    private EditText latitudeEditText;
    private EditText longitudeEditText;
    private EditText nameEditText;
    private EditText questionEditText;
    private EditText goodAnswerEditText;
    private EditText badAnswer1EditText;
    private EditText badAnswer2EditText;
    private EditText badAnswer3EditText;
    private Button newStepOkButton;
    private Button newStepCancelButton;

    private ArrayList<Step> steps;
    private ListView stepsListView;

    private StepArrayAdapter stepArrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_hunt);

        Toolbar toolbar = (Toolbar) findViewById(R.id.create_hunt_activity_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String huntName;
        if(getIntent().getBooleanExtra(OnBuildingHuntActivity.IS_THIS_HUNT_NEW_EXTRA, true)) {
            huntName = getIntent().getStringExtra(OnBuildingHuntActivity.NEW_HUNT_NAME_EXTRA);
            onBuildingHunt = new Hunt(huntName, System.currentTimeMillis());
            steps = new ArrayList<>();
        } else {
            String huntFileName = getIntent().getStringExtra(OnBuildingHuntActivity.HUNT_FILE_PATH_EXTRA);
            HuntFileReader huntFileReader = new HuntFileReader(huntFileName);
            huntName = huntFileReader.getHuntName();
            steps = huntFileReader.getSteps();
            Toast.makeText(this, huntFileReader.getHuntName(), Toast.LENGTH_SHORT).show();
            onBuildingHunt = new Hunt(huntName, huntFileReader.getHuntID());
        }

        getSupportActionBar().setTitle(huntName);

        stepArrayAdapter = new StepArrayAdapter(this, R.layout.steps_list_item, steps);
        stepsListView = (ListView) findViewById(R.id.steps_list);
        stepsListView.setAdapter(stepArrayAdapter);
        registerForContextMenu(stepsListView);
        stepArrayAdapter.notifyDataSetChanged();

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if(v.getId() == R.id.steps_list){
            getMenuInflater().inflate(R.menu.edit_item_menu, menu);
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
        }
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.edit_item :
                //TODO edit item
                return true;
            case R.id.delete_item:
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
                steps.remove(info.position);
                stepArrayAdapter.notifyDataSetChanged();
                Toast.makeText(this, "Step deleted", Toast.LENGTH_SHORT).show();
                return true;
        }
        return false;
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
                newStepDialog = new CreateNewStepDialogFragment();
                newStepDialog.show(getSupportFragmentManager(), "create new step");
                getSupportFragmentManager().executePendingTransactions();
                setListeners(newStepDialog.getDialog());
                return true;
            case android.R.id.home :
                saveCurrentHunt();
                NavUtils.navigateUpFromSameTask(this);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case PICK_STEP_POSITION_ACTIVITY_REQUEST_CODE:
                Double latitude = 0d;
                Double longitude = 0d;
                if(resultCode == PickStepPositionActivity.RESULT_CODE_STEP_PICKED){
                    latitude = data.getDoubleExtra(PickStepPositionActivity.EXTRA_LATITUDE, 0);
                    longitude = data.getDoubleExtra(PickStepPositionActivity.EXTRA_LONGITUDE, 0);
                } else if(resultCode == PickStepPositionActivity.RESULT_CODE_CANCELED){
                    latitude = 0d;
                    longitude = 0d;
                }
                System.out.println(String.valueOf(latitude));
                System.out.println(longitude);
                latitudeEditText.setText(Double.toString(latitude));
                longitudeEditText.setText(String.valueOf(longitude));

        }
    }

    public static class CreateNewStepDialogFragment extends DialogFragment {

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            LayoutInflater inflater = getActivity().getLayoutInflater();

            View view = inflater.inflate(R.layout.add_step_dialog, null);
            ((ImageButton)view.findViewById(R.id.pick_step_position_button)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), PickStepPositionActivity.class);
                    getActivity().startActivityForResult(intent, PICK_STEP_POSITION_ACTIVITY_REQUEST_CODE);

                }
            });

            builder.setView(view);
            return builder.create();
        }

    }

    private boolean createNewStep(){

        String name = nameEditText.getText().toString();
        Double latitude = Double.valueOf(latitudeEditText.getText().toString());
        Double longitude = Double.valueOf(longitudeEditText.getText().toString());
        long id = System.currentTimeMillis();
        String question = questionEditText.getText().toString();
        String goodAnswer = goodAnswerEditText.getText().toString();
        String badAnswer1 = badAnswer1EditText.getText().toString();
        String badAnswer2 = badAnswer2EditText.getText().toString();
        String badAnswer3 = badAnswer3EditText.getText().toString();

        Step step = new Step(name, latitude, longitude, id, question, goodAnswer, badAnswer1, badAnswer2, badAnswer3);

        steps.add(step);
        stepArrayAdapter.notifyDataSetChanged();
        return true;
    }

    private TextWatcher editTextListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
             if(latitudeEditText.getText().toString().equals("") || Double.valueOf(latitudeEditText.getText().toString())==0){
                latitudeEditText.setError("Invalid");
             } else {
                 latitudeEditText.setError(null);
             }
            if(longitudeEditText.getText().toString().equals("") || Double.valueOf(longitudeEditText.getText().toString())==0){
                longitudeEditText.setError("Invalid");
            } else {
                longitudeEditText.setError(null);
            }
            if(nameEditText.getText().toString().isEmpty() || nameEditText.getText().toString().contains(HuntFileWriter.SEPARATOR)){
                nameEditText.setError("Invalid");
            } else {
                nameEditText.setError(null);
            }
            if(questionEditText.getText().toString().isEmpty() || questionEditText.getText().toString().contains(HuntFileWriter.SEPARATOR)){
                questionEditText.setError("Invalid");
            } else {
                questionEditText.setError(null);
            }
            if(goodAnswerEditText.getText().toString().isEmpty() || goodAnswerEditText.getText().toString().contains(HuntFileWriter.SEPARATOR)){
                goodAnswerEditText.setError("Invalid");
            } else {
                goodAnswerEditText.setError(null);
            }
            if(badAnswer1EditText.getText().toString().isEmpty() || badAnswer1EditText.getText().toString().contains(HuntFileWriter.SEPARATOR)){
                badAnswer1EditText.setError("Invalid");
            } else {
                badAnswer1EditText.setError(null);
            }
            if(badAnswer2EditText.getText().toString().contains(HuntFileWriter.SEPARATOR)){
                badAnswer2EditText.setError("Invalid");
            } else {
                badAnswer2EditText.setError(null);
            }
            if(badAnswer3EditText.getText().toString().contains(HuntFileWriter.SEPARATOR)){
                badAnswer3EditText.setError("Invalid");
            } else {
                badAnswer3EditText.setError(null);
            }
            if(latitudeEditText.getError()==null
                    && longitudeEditText.getError()==null
                    && nameEditText.getError()==null
                    && questionEditText.getError()==null
                    && goodAnswerEditText.getError()==null
                    && badAnswer1EditText.getError()==null
                    && badAnswer2EditText.getError()==null
                    && badAnswer3EditText.getError()==null) {
                newStepOkButton.setEnabled(true);
            } else {
                newStepOkButton.setEnabled(false);
            }

        }
    };

    public void setListeners(Dialog dialog){
        nameEditText = (EditText)dialog.findViewById(R.id.new_step_name);
        latitudeEditText = (EditText)dialog.findViewById(R.id.new_step_latitude);
        longitudeEditText = (EditText)dialog.findViewById(R.id.new_step_longitude);
        questionEditText = (EditText)dialog.findViewById(R.id.new_step_question);
        goodAnswerEditText = (EditText)dialog.findViewById(R.id.new_step_correct_answer);
        badAnswer1EditText = (EditText)dialog.findViewById(R.id.new_step_bad_answer_1);
        badAnswer2EditText = (EditText)dialog.findViewById(R.id.new_step_bad_answer_2);
        badAnswer3EditText = (EditText)dialog.findViewById(R.id.new_step_bad_answer_3);
        newStepOkButton = (Button)dialog.findViewById(R.id.new_step_ok_button);
        newStepCancelButton = (Button)dialog.findViewById(R.id.new_step_cancel_button);

        newStepOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(createNewStep()){
                    newStepDialog.dismiss();
                }
            }
        });
        newStepCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newStepDialog.dismiss();
            }
        });

        nameEditText.addTextChangedListener(editTextListener);
        latitudeEditText.addTextChangedListener(editTextListener);
        longitudeEditText.addTextChangedListener(editTextListener);
        questionEditText.addTextChangedListener(editTextListener);
        goodAnswerEditText.addTextChangedListener(editTextListener);
        badAnswer1EditText.addTextChangedListener(editTextListener);
        badAnswer2EditText.addTextChangedListener(editTextListener);
        badAnswer3EditText.addTextChangedListener(editTextListener);
    }

    private class StepArrayAdapter extends ArrayAdapter {

        private Context context;

        public StepArrayAdapter(Context context, int resource, List<Step> objects) {
            super(context, resource, objects);
            this.context = context;
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent){
            Step step = steps.get(position);

            View v = convertView;
            if (v==null){
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(R.layout.steps_list_item, null);
            }

            ((TextView) v.findViewById(R.id.steps_list_item_number)).setText(String.valueOf(position));
            ((TextView) v.findViewById(R.id.steps_list_item_name)).setText(step.getName());
            ((TextView) v.findViewById(R.id.steps_list_item_latitude)).setText(step.getLatitude().toString());
            ((TextView) v.findViewById(R.id.steps_list_item_longitude)).setText(step.getLongitude().toString());

            return v;
        }
    };


    @Override
    public void onBackPressed() {
        saveCurrentHunt();
        super.onBackPressed();
    }

    private void saveCurrentHunt(){
        if(!steps.isEmpty()) {
            onBuildingHunt.addSteps(steps);
            HuntFileWriter writer = new HuntFileWriter(getApplicationContext(), onBuildingHunt);
            writer.write();
        }
    }
}
