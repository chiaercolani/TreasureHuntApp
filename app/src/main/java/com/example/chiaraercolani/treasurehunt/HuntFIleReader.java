package com.example.chiaraercolani.treasurehunt;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by chiaraercolani on 13/12/16.
 */

public class HuntFileReader {

    private FileReader fileReader;


    public HuntFileReader (String fileName){

        // This will reference one line at a time
        String line = null;
        ArrayList<Step> steps = new ArrayList<>();

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            line = bufferedReader.readLine();
            String[] parts_hunt =  line.split("::");
            Hunt hunt = new Hunt(parts_hunt[0],Long.parseLong(parts_hunt[1]));

            while((line = bufferedReader.readLine()) != null){
                String[] parts =  line.split("::");
                Step step = new Step(parts[0],Double.parseDouble(parts[1]),Double.parseDouble(parts[2]),Long.parseLong(parts[3]),parts[4],parts[5],parts[6],parts[7],parts[8]);
                steps.add(step);
            }

            // Always close files.
            bufferedReader.close();
        }
        catch(IOException ex) {
            System.out.println(
                    "Error reading file '" + fileName + "'");

        }
    }
}

