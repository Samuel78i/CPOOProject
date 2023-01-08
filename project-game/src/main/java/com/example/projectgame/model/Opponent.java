package com.example.projectgame.model;

import java.util.ArrayList;
import java.util.List;

public class Opponent {
    private String name;
    private List<Integer> score;
    private boolean turn;

    public Opponent(){
        name = "";
        score = null;
    }

    public Opponent(String name){
        this.name = name;
        score = new ArrayList<>();
    }

    public boolean isTurn() {
        return turn;
    }

    public String getName() {
        return name;
    }
}
