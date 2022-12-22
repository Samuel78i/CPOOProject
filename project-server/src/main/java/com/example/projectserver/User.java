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
    private boolean waitingOnOpponents = false;

    private List<Stat> stats = new ArrayList<>();


    public User(String n, String p) {
        name = n;
        password = p;
        opponents = new ArrayList<>();
    }

    public User() {
        name = "";
        password = "";
        opponents = new ArrayList<>();
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

}