package com.werka.gra.objects;

import com.werka.gra.scene.GameScene;
import javafx.scene.paint.Color;

import static com.werka.gra.objects.Invader.INVADER_SIZE;
import static com.werka.gra.scene.GameScene.HEIGHT;

public class InvaderBullet {


    private double x;
    private double y;
    private double speed;

    public InvaderBullet(double x, double y, double speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
    }

    public void update() {
        y += speed;
    }

    public void drawBullet(GameScene gameScene){
        gameScene.getGraphicContext().setFill(Color.RED);
        gameScene.getGraphicContext().fillOval(x, y, 5,10);
    }

    public boolean isOutOfBounds() {
        return y < 0 || y > HEIGHT;
    }

    public boolean intersects(Player player) {
        return x > player.getX() &&
                x < player.getX() + Player.PLAYER_SIZE &&
                y > player.getY() &&
                y < player.getY() + Player.PLAYER_SIZE;
    }

}
