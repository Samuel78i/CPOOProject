package com.example.projectserver;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public final class User implements Serializable {
    private final String name;
    private final String password;
    private int score;
    private int numberOfGamesPlayed;
    private int indiceLastScore;
    private double moyenne;
    private int bestScore;
    private List<Opponent> opponents;

    public User(String n, String p) {
        name = n;
        password = p;
        opponents = new ArrayList<>();
        moyenne = 0;
    }

    public User() {
        name = "";
        password = "";
        opponents = new ArrayList<>();
        moyenne = 0;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public int getNumberOfGamesPlayed() {
        return numberOfGamesPlayed;
    }

    public void setNumberOfGamesPlayed(int numberOfGamesPlayed) {
        this.numberOfGamesPlayed = numberOfGamesPlayed;
    }

    public int getIndiceLastScore() {
        return indiceLastScore;
    }

    public void setIndiceLastScore(int indiceLastScore) {
        this.indiceLastScore = indiceLastScore;
    }

    public double getMoyenne() {
        return moyenne;
    }

    public void setMoyenne(double moyenne) {
        this.moyenne = moyenne;
    }

    public int getBestScore() {
        return bestScore;
    }

    public void setBestScore(int bestScore) {
        this.bestScore = bestScore;
    }

    public List<Opponent> getOpponents() {
        return opponents;
    }

    public void setOpponents(List<Opponent> opponents) {
        this.opponents = opponents;
    }


    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

}