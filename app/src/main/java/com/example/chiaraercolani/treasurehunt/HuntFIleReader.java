package com.example.chiaraercolani.treasurehunt;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by chiaraercolani on 13/12/16.
 */

public class HuntFileReader {

    private FileReader fileReader;
    private ArrayList<Step> steps;

    public HuntFileReader (String fileName){

        try {
            fileReader = new FileReader(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        steps = new ArrayList<>();
    }

    public ArrayList<Step> getSteps(){

        // This will reference one line at a time
        String line = null;

        try {

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            line = bufferedReader.readLine();
            String[] parts_hunt = line.split("::");
            Hunt hunt = new Hunt(parts_hunt[0], Long.parseLong(parts_hunt[1]));

            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split("::");
                Step step = null;
                switch (parts.length) {
                    case 7:
                        step = new Step(parts[0], Double.parseDouble(parts[2]), Double.parseDouble(parts[3]), Long.parseLong(parts[1]), parts[4], parts[5], parts[6], "", "");
                        break;
                    case 8:
                        step = new Step(parts[0], Double.parseDouble(parts[2]), Double.parseDouble(parts[3]), Long.parseLong(parts[1]), parts[4], parts[5], parts[6], parts[7], "");
                        break;
                    case 9:
                        step = new Step(parts[0], Double.parseDouble(parts[2]), Double.parseDouble(parts[3]), Long.parseLong(parts[1]), parts[4], parts[5], parts[6], parts[7], parts[8]);
                        break;
                }
                if (null != step) {
                    steps.add(step);
                }
            }

            // Always close files.
            bufferedReader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return steps;
    }

}

