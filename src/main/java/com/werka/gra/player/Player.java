package com.werka.gra.player;


public class Player {
    private int x;
    private int y;
    private int lives;

    public Player() {
        this.x = 0;
        this.y = 0;
        this.lives = 3;
    }

    public Player(int x, int y, int lives) {
        this.x = x;
        this.y = y;
        this.lives = lives;
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

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }
}
