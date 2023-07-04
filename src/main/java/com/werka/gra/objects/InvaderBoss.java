package com.werka.gra.objects;

import com.werka.gra.scene.GameScene;
import javafx.scene.paint.Color;

import static com.werka.gra.scene.GameScene.WIDTH;

public class InvaderBoss extends Invader {

    private double dy=0;
    private int yspeed = 3;

    private int lives;

    public InvaderBoss(double x, double y, int size, int speed) {
        super(x, y, size, speed);
        this.lives = 5;
    }

    public int getLives() {
        return lives;
    }

    public void decreaseLives() {
        this.lives--;
    }

    // obsluga zmiany pozycji

    @Override
    public void update() {
        super.update(); // wywolanie metody z klasy macierzystej metode do aktualizacji pozycji
        if (dy >= 30 || dy <= 0) {
            yspeed = -yspeed; // zmiana kierunku lotu bosa
        }
        dy += yspeed; // przesuniecie bosa w gore i w dol
    }

    @Override
    public void drawInvader(GameScene gameScene) {
        gameScene.getGraphicContext().setFill(Color.BLUE);
        gameScene.getGraphicContext().fillRect(getX(), getY() + dy, getSize(), 40); // dodanie dy do y w celu animacji lotu bosa w gore i w dol
    }


}
