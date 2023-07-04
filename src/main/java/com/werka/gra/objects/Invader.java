package com.werka.gra.objects;

import com.werka.gra.scene.GameScene;
import javafx.scene.paint.Color;

import static com.werka.gra.scene.GameScene.WIDTH;

public class Invader {

    public static final int INVADER_SIZE = 30;
    private double x;
    private double y;
    private int size;
    private int speed;

    public Invader(double x, double y, int size, int speed){
        this.x = x;
        this.y = y;
        this.size = size;
        this.speed =speed;
    }

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }

    public int getSize() {
        return size;
    }

    public int getSpeed() {
        return speed;
    }

    // obsluga zmiany pozycji
    public void update() {
        x += speed; // przesuniecie na osi x o wartosc speed
        if (x <= 0 || x >= WIDTH - size) { // jesli wyjdzie poza ekran to zmien kierunek i przesun w dol
            speed = -speed; // zmiana kierunku
            y += size / 2; // przesuniecie w dol po zmianie kierunku
        }
    }

    public void drawInvader(GameScene gameScene){
        gameScene.getGraphicContext().setFill(Color.BLUE);
        gameScene.getGraphicContext().fillRect(x, y, INVADER_SIZE, 20);
    }


}
