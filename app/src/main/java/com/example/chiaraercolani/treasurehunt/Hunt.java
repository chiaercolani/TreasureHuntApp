package com.example.chiaraercolani.treasurehunt;

import java.util.ArrayList;

/**
 * Created by Vincent RICHAUD on 08/11/2016.
 */

public class Hunt {

    private String name;
    private long ID;
    private ArrayList<Step> steps;

    public Hunt(String name, long id) {
        this.name = name;
        ID = id;
        steps = new ArrayList<Step>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public void addStepAtPosition(Step step, int index){
        steps.add(index, step);
    }

    public void addStepAtEnd(Step step){
        steps.add(step);
    }

    public Step getStep(int index){
        return steps.get(index);
    }


}
