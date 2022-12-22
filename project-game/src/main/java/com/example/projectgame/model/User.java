package com.example.projectgame.model;

import java.util.ArrayList;
import java.util.List;

public class User {
    private final String name;
    private final String password;
    private List<Opponent> opponents;
    private boolean waitingOnOpponents = false;

    private List<Stat> stats = new ArrayList<>();


    public User(String n, String p) {
        name = n;
        password = p;
        opponents = new ArrayList<>();
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

    public List<Stat> getStats(){
        return stats;
    }

    public Stat getLastStat(){
        return stats.get(stats.size() - 1);
    }

    public void setStat(float wpm, float precision, int regularity) {
        Stat s = new Stat(wpm, precision, regularity);
        stats.add(s);
    }
}
