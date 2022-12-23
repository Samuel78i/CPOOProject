package com.example.projectserver;

import java.io.Serializable;

public class Settings implements Serializable {
    private int speed;
    private String language;
    private int numberOfWords;
    private long time;

    public Settings(){
        speed = 7;
        language = "fr";
        numberOfWords = 15;
        time = 180;
    }

    public Settings(int s, String l, int n, int t){
        speed = s;
        language = l;
        numberOfWords = n;
        time = t;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getNumberOfWords() {
        return numberOfWords;
    }

    public void setNumberOfWords(int numberOfWords) {
        this.numberOfWords = numberOfWords;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
