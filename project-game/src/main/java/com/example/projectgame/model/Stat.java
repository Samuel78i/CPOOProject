package com.example.projectgame.model;

public class Stat {
    private float wordPerMinutes = 0;
    private float precision = 0;
    private float regularity = 0;

    public Stat(float w, float p, float r){
        wordPerMinutes = w;
        precision = p;
        regularity = r;
    }

    public float getWordPerMinutes() {
        return wordPerMinutes;
    }

    public void setWordPerMinutes(float wordPerMinutes) {
        this.wordPerMinutes = wordPerMinutes;
    }

    public float getPrecision() {
        return precision;
    }

    public void setPrecision(float precision) {
        this.precision = precision;
    }

    public float getRegularity() {
        return regularity;
    }

    public void setRegularity(float regularity) {
        this.regularity = regularity;
    }
}
