package com.example.projectserver;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Opponent implements Serializable {
    private String name;
    private List<Integer> score;
    private boolean turn;

    public Opponent(String name){
        this.name = name;
        score = new ArrayList<>();
    }


    public Opponent(){
        name = "";
        score = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getScore() {
        return score;
    }

    public void setScore(List<Integer> score) {
        this.score = score;
    }

    public void addScore(int score){
        this.score.add(score);
    }

    public boolean isTurn() {
        return turn;
    }

    public void setTurn(boolean turn) {
        this.turn = turn;
    }
}