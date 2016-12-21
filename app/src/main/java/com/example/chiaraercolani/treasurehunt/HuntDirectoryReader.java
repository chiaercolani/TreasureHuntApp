package com.example.chiaraercolani.treasurehunt;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Vincent RICHAUD on 21/12/2016.
 */

public class HuntDirectoryReader {

    private ArrayList<File> huntFileList;
    private File huntDirectory;

    public HuntDirectoryReader(File huntDirectoryParent){
        huntFileList = new ArrayList<>();
        this.huntDirectory = new File(huntDirectoryParent, HuntFileWriter.DIRECTORY);
        if(!huntDirectory.exists()){
            huntDirectory.mkdirs();
        } else if (!huntDirectory.isDirectory()){
            huntDirectory.delete();
            huntDirectory.mkdirs();
        }
    }


    public ArrayList<File> getHuntFileList() {
        File[] files = huntDirectory.listFiles();

        for (File f : files) {
            if (!f.isDirectory() && f.getName().endsWith(HuntFileWriter.EXTENSION)){
                    huntFileList.add(f);
            }
        }
        return huntFileList;
    }
}
