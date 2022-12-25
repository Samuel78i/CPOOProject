package com.example.projectgame.model;

public class TextColors {
    private int x;
    private int y;
    private String colors;

    public TextColors(int x, int y, String c){
        this.x = x;
        this.y = y;
        colors = c;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getColors() {
        return colors;
    }

    public void setColors(String colors) {
        this.colors = colors;
    }
}
