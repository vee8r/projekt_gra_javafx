package com.werka.gra.objects;


import com.werka.gra.scene.GameScene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import static com.werka.gra.HelloApplication.activeKeys;
import static com.werka.gra.objects.Invader.INVADER_SIZE;
import static com.werka.gra.scene.GameScene.WIDTH;


public class Player {

    public static final int PLAYER_SIZE = 30;
    private int x;
    private int y;
    private int lives;
    private int specialShoots =3;


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

    public int getY() {
        return y;
    }


    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public void drawPlayer(GameScene gameScene) {
        gameScene.getGraphicContext().setFill(Color.RED);
        gameScene.getGraphicContext().fillRect(x, y, PLAYER_SIZE, 20);
    }

    public void update() {

        if (isKeyPressed(KeyCode.LEFT)) {
            x -= 5;
        }

        if (isKeyPressed(KeyCode.RIGHT)) {
            x += 5;
        }

        if (x < 0) { // ograniczenie wychodzenia gracza poza ekran
            x = 0; // ustawia 0 zeby nie uciekal poza zakres
        } else if (x > WIDTH - PLAYER_SIZE) {
            x = WIDTH - PLAYER_SIZE; // ustawia szerokosc ekranu - szerokosc gracza zeby nie uciekal poza zakres
        }
    }

    private boolean isKeyPressed(KeyCode keyCode) {

        return activeKeys.contains(keyCode.toString());

    }

    public boolean intersects(Invader invader) { // sprawdzanie kolizji gracza z przeciwnikiem
        return x + PLAYER_SIZE > invader.getX() &&
                x < invader.getX() + INVADER_SIZE &&
                y + PLAYER_SIZE > invader.getY() &&
                y < invader.getY() + INVADER_SIZE;
    }

    public void decreaseLives() {
        this.lives--;
    }

    public void increaseLives() {
        this.lives++;
    }

    public void increaseSpecialShoots() {
        this.specialShoots++;
    }

    public void decreaseSpecialBullets() {
        this.specialShoots--;
    }

    public int getShoots() {
        return this.specialShoots;
    }
}
