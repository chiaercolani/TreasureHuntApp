package com.example.chiaraercolani.treasurehunt;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

/**
 * Created by chiaraercolani on 21/12/16.
 */

public class DisplayQuestionDialog extends DialogFragment {

    TextView textView;
    View view;

    AlertDialog.Builder builder;

    Button button1;
    Button button2;
    Button button3;
    Button button4;

    JoinedHuntStartActivity.MyDialogCloseListener closeListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        view =inflater.inflate(R.layout.question_dialog, null);
        builder.setView(view);

        textView = (TextView)view.findViewById(R.id.question_asked);

        return builder.create();
    }


    public void setStep(Step step){
        textView.setText(step.getQuestion());

        button1 = (Button)view.findViewById(R.id.question_dialog_button_1);
        button2 = (Button)view.findViewById(R.id.question_dialog_button_2);
        button3 = (Button)view.findViewById(R.id.question_dialog_button_3);
        button4 = (Button)view.findViewById(R.id.question_dialog_button_4);

        button1.setEnabled(true);
        button2.setEnabled(true);
        button3.setEnabled(true);
        button4.setEnabled(true);
        button1.setVisibility(View.VISIBLE);
        button2.setVisibility(View.VISIBLE);
        button3.setVisibility(View.VISIBLE);
        button4.setVisibility(View.VISIBLE);

        Random random = new Random(System.currentTimeMillis());

        if (step.getWrongAnswer3().isEmpty()) {
            if(step.getWrongAnswer2().isEmpty()){
                button4.setEnabled(false);
                button4.setVisibility(View.INVISIBLE);
                button3.setEnabled(false);
                button3.setVisibility(View.INVISIBLE);
                if (random.nextInt(2) == 0) {
                    button1.setText(step.getGoodAnswer());
                    button2.setText(step.getWrongAnswer1());
                }else{
                    button2.setText(step.getGoodAnswer());
                    button1.setText(step.getWrongAnswer1());
                }
            }else {
                button4.setEnabled(false);
                button4.setVisibility(View.INVISIBLE);
                switch (random.nextInt(3)) {
                    case  0:
                        button1.setText(step.getGoodAnswer());
                        button2.setText(step.getWrongAnswer1());
                        button3.setText(step.getWrongAnswer2());
                        break;
                    case 1 :
                        button2.setText(step.getGoodAnswer());
                        button3.setText(step.getWrongAnswer1());
                        button1.setText(step.getWrongAnswer2());
                        break;
                    case 2 :
                        button3.setText(step.getGoodAnswer());
                        button1.setText(step.getWrongAnswer1());
                        button2.setText(step.getWrongAnswer2());
                        break;
                }
            }
        }else{
            switch (random.nextInt(4)) {
                case  0:
                    button1.setText(step.getGoodAnswer());
                    button2.setText(step.getWrongAnswer1());
                    button3.setText(step.getWrongAnswer2());
                    button4.setText(step.getWrongAnswer3());
                    break;
                case 1 :
                    button2.setText(step.getGoodAnswer());
                    button3.setText(step.getWrongAnswer1());
                    button1.setText(step.getWrongAnswer3());
                    button4.setText(step.getWrongAnswer2());
                    break;
                case 2 :
                    button3.setText(step.getGoodAnswer());
                    button1.setText(step.getWrongAnswer2());
                    button2.setText(step.getWrongAnswer3());
                    button4.setText(step.getWrongAnswer1());
                    break;
                case 3 :
                    button4.setText(step.getGoodAnswer());
                    button1.setText(step.getWrongAnswer2());
                    button2.setText(step.getWrongAnswer3());
                    button3.setText(step.getWrongAnswer1());
                    break;
            }
        }

    }

    public void getAnswer (final Step step){
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (button1.getText()==step.getGoodAnswer()){
                    //Toast.makeText(v.getContext(), "BRAVO!", Toast.LENGTH_SHORT).show();
                    getDialog().dismiss();

                }else{
                    Toast.makeText(v.getContext(), "Wrong Answer!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (button2.getText()==step.getGoodAnswer()){
                    //Toast.makeText(v.getContext(), "BRAVO!", Toast.LENGTH_SHORT).show();
                    getDialog().dismiss();
                }else{
                    Toast.makeText(v.getContext(), "Wrong Answer!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (button3.getText()==step.getGoodAnswer()){
                    //Toast.makeText(v.getContext(), "BRAVO!", Toast.LENGTH_SHORT).show();
                    getDialog().dismiss();
                }else{
                    Toast.makeText(v.getContext(), "Wrong Answer!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (button4.getText()==step.getGoodAnswer()){
                    //Toast.makeText(v.getContext(), "BRAVO!", Toast.LENGTH_SHORT).show();
                    getDialog().dismiss();
                }else{
                    Toast.makeText(v.getContext(), "Wrong Answer!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void DismissListner(JoinedHuntStartActivity.MyDialogCloseListener closeListener){
        this.closeListener = closeListener;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if(closeListener!=null){
            closeListener.handleDialogClose(null);
        }

    }


}
