package com.example.projectserver;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public final class User implements Serializable {
    private final String name;
    private final String password;
    private List<Opponent> opponents;
    private boolean opponentLost = false;
    private Settings settings;
    private boolean waitingOnOpponents = false;
    private int malus = 0;

    private List<Stat> stats = new ArrayList<>();


    public User(String n, String p) {
        name = n;
        password = p;
        opponents = new ArrayList<>();
        settings = new Settings();
    }

    public User() {
        name = "";
        password = "";
        opponents = new ArrayList<>();
        settings = new Settings();
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

    public void setOpponents(List<Opponent> opponents) {
        this.opponents = opponents;
    }

    public List<Stat> getStats() {
        return stats;
    }

    public void setStats(List<Stat> stats) {
        this.stats = stats;
    }

    public boolean isWaitingOnOpponents() {
        return waitingOnOpponents;
    }

    public void setWaitingOnOpponents(boolean waitingOnOpponents) {
        this.waitingOnOpponents = waitingOnOpponents;
    }


    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    public void addAMalus() {
        malus++;
    }

    public int getMalus() {
        return malus;
    }

    public void setMalus(int malus) {
        this.malus = malus;
    }

    public void setOpponentLost(boolean b) {
        opponentLost = b;
    }

    public boolean isOpponentLost() {
        return opponentLost;
    }

    public boolean opponentsExistAlready(String opponentName) {
        for(Opponent o: opponents){
            if(o.getName().equals(opponentName)){
                return true;
            }
        }
        return false;
    }

    public void addOpponents(Opponent opponent) {
        if(!opponentsExistAlready(opponent.getName())) {
            opponents.add(opponent);
        }
    }
}