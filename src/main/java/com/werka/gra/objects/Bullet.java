package com.werka.gra.objects;

import com.werka.gra.scene.GameScene;
import javafx.scene.paint.Color;

import static com.werka.gra.objects.Invader.INVADER_SIZE;
import static com.werka.gra.scene.GameScene.HEIGHT;
import static com.werka.gra.scene.GameScene.WIDTH;

public class Bullet {


    private double x;
    private double y;
    private double speed;

    public Bullet(double x, double y, double speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
    }

    public void update() {
        y += speed;
    }

    public void drawBullet(GameScene gameScene){
        gameScene.getGraphicContext().setFill(Color.BLACK);
        gameScene.getGraphicContext().fillOval(x, y, 5,10);
    }

    public boolean isOutOfBounds() {
        return y < 0 || y > HEIGHT;
    }

    public boolean intersects(Invader invader) {
        return x > invader.getX() &&
                x < invader.getX() + INVADER_SIZE &&
                y > invader.getY() &&
                y < invader.getY() + INVADER_SIZE;
    }

}
