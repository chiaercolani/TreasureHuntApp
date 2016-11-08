package com.example.chiaraercolani.treasurehunt;

/**
 * Created by Vincent RICHAUD on 08/11/2016.
 */

public class Step {

    private String name;
    private Double longitude;
    private Double Latitude;
    private long ID;
    private String question;
    private String answer;


    public Step(String name, Double longitude, Double latitude, long id, String question, String answer) {
        this.name = name;
        this.longitude = longitude;
        Latitude = latitude;
        ID = id;
        this.question = question;
        this.answer = answer;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return Latitude;
    }

    public void setLatitude(Double latitude) {
        Latitude = latitude;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
