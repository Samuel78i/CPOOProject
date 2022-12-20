package com.example.projectgame.model;

import java.util.ArrayList;
import java.util.List;

public class User {
    private final String name;
    private final String password;


    private int score;
    private int numberOfGamesPlayed;
    private int indiceLastScore;
    private double moyenne;
    private int bestScore;
    private List<Opponent> opponents;
    private boolean waitingOnOpponents = false;


    public User(String n, String p) {
        name = n;
        password = p;
        opponents = new ArrayList<>();
        moyenne = 0;
    }

    public void addOpponents(Opponent o){
        opponents.add(o);
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public List<Opponent> getOpponents() {
        return opponents;
    }

    public boolean isWaitingOnOpponents() {
        return waitingOnOpponents;
    }

    public void setWaitingOnOpponents(boolean waitingOnOpponents) {
        this.waitingOnOpponents = waitingOnOpponents;
    }
}
