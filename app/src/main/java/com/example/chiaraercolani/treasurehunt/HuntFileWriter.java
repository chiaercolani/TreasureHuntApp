package com.example.chiaraercolani.treasurehunt;

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
    private String fileContent;


    public HuntFileWriter(Hunt huntToSave) {
        if(huntToSave != null) {
            this.huntToSave = huntToSave;
            String fileName = huntToSave.getName() + "_" + huntToSave.getID() + ".hunt";
            try {
                fileWriter = new FileWriter(fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean write(){
        if(fileWriter!=null && huntToSave!=null){
            fileContent = "";
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
            try {
                fileWriter.write(fileContent);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
    }
}
