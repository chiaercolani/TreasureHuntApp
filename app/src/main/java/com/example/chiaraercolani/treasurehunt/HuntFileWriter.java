package com.example.chiaraercolani.treasurehunt;

import android.content.Context;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Vincent RICHAUD on 06/12/2016.
 */

public class HuntFileWriter  {

    public final static String SEPARATOR = "::";
    public final static String EXTENSION = ".hunt";
    public final static String DIRECTORY = "Hunts";

    private FileWriter fileWriter;
    private Hunt huntToSave;
    private Context context;


    public HuntFileWriter(Context context, Hunt huntToSave) {
        if(huntToSave != null && context!=null) {
            this.huntToSave = huntToSave;
            this.context = context;
        }
    }

    public void writeOnSD(String filename, String body) {
        try {
            File root = new File(context.getFilesDir(), DIRECTORY);
            if (!root.exists()) {
                root.mkdirs();
            }
            File file = new File(root, filename);
            FileWriter writer = new FileWriter(file, false);
            writer.write(body);
            writer.flush();
            writer.close();
            Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(){
        String fileContent = "";
        fileContent += huntToSave.getName() + SEPARATOR + huntToSave.getID() + "\n";
        ArrayList<Step> steps = huntToSave.getSteps();
        for(Step s : steps){
            fileContent += s.getName() + SEPARATOR;
            fileContent += s.getID() + SEPARATOR;
            fileContent += s.getLatitude() + SEPARATOR;
            fileContent += s.getLongitude() + SEPARATOR;
            fileContent += s.getQuestion() + SEPARATOR;
            fileContent += s.getGoodAnswer() + SEPARATOR;
            fileContent += s.getWrongAnswer1() + SEPARATOR;
            fileContent += s.getWrongAnswer2() + SEPARATOR;
            fileContent += s.getWrongAnswer3() + SEPARATOR;
            fileContent += "\n";
        }
        String fileName = huntToSave.getName() + "_" + huntToSave.getID() + EXTENSION;
        writeOnSD(fileName, fileContent);
    }
}
