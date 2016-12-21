package com.example.chiaraercolani.treasurehunt;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Vincent RICHAUD on 06/12/2016.
 */

public class HuntFileWriter  {

    public final static String separator = "::";

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
            File root = new File(context.getFilesDir(), "Hunts");
            if (!root.exists()) {
                root.mkdirs();
            }
            File file = new File(root, filename);
            FileWriter writer = new FileWriter(file);
            writer.append(body);
            writer.flush();
            writer.close();
            Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(){
        String fileContent = "";
        fileContent += huntToSave.getName() + separator + huntToSave.getID() + "\n";
        ArrayList<Step> steps = huntToSave.getSteps();
        for(Step s : steps){
            fileContent += s.getName() + separator;
            fileContent += s.getID() + separator;
            fileContent += s.getLatitude() + separator;
            fileContent += s.getLongitude() + separator;
            fileContent += s.getQuestion() + separator;
            fileContent += s.getGoodAnswer() + separator;
            fileContent += s.getWrongAnswer1() + separator;
            fileContent += s.getWrongAnswer2() + separator;
            fileContent += s.getWrongAnswer3() + separator;
            fileContent += "\n";
        }
        String fileName = huntToSave.getName() + "_" + huntToSave.getID() + ".txt";
        writeOnSD(fileName, fileContent);
        writeOnSD("huntsample.txt", "fqerg");
    }
}
