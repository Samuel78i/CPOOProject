package com.example.projectgame.model;

import java.util.ArrayList;
import java.util.List;

public class User {
    private final String name;
    private final String password;
    private Settings settings;
    private List<Opponent> opponents;
    private boolean opponentLost = false;
    private boolean waitingOnOpponents = false;

    private final List<Stat> stats = new ArrayList<>();
    private int malus = 0;


    public User(String n, String p) {
        name = n;
        password = p;
        opponents = new ArrayList<>();
        settings = new Settings();
    }

    public void addOpponents(Opponent opponent) {
        if(!opponentsExistAlready(opponent.getName())) {
            opponents.add(opponent);
        }
    }

    public boolean opponentsExistAlready(String opponentName) {
        for(Opponent o: opponents){
            if(o.getName().equals(opponentName)){
                return false;
            }
        }
        return true;
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

    public void setOpponentLost(boolean opponentLost) {
        this.opponentLost = opponentLost;
    }

    public boolean isOpponentLost() {
        return opponentLost;
    }

    public List<Stat> getStats(){
        return stats;
    }

    public Stat getLastStat(){
        if(stats.isEmpty()){
            return new Stat();
        }
        return stats.get(stats.size() - 1);
    }


    public void setStat(float wpm, float precision, int regularity) {
        Stat s = new Stat(wpm, precision, regularity);
        stats.add(s);
    }

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    public void resetSetting() {
        settings = new Settings();
    }

    public int getMalus() {
        return malus;
    }

}
