package com.werka.gra.player;


import com.werka.gra.scene.GameScene;
import javafx.scene.paint.Color;

public class Player {
    private int x;
    private int y;
    private int lives;

    private GameScene gameScene;

    public Player(GameScene gameScene) {
        this.x = 0;
        this.y = 0;
        this.lives = 3;
        this.gameScene = gameScene;
    }

    public Player(int x, int y, int lives,GameScene gameScene) {
        this.x = x;
        this.y = y;
        this.lives = lives;
        this.gameScene = gameScene;

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

    public void drawPlayer(){
        gameScene.getGraphicContext().setFill(Color.RED);
        gameScene.getGraphicContext().fillRect(x, y, 20, 20);

    }
}
