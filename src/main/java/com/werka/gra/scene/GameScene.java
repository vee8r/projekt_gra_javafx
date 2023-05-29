package com.werka.gra.scene;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class GameScene {

    public static int WIDTH;

    public static int HEIGHT;

    public static int MARGIN;

    private Canvas canvas;


    public GameScene() {
        this.WIDTH = 1024;
        this.HEIGHT = 600;
        this.MARGIN = 10;
        this.canvas=new Canvas(this.WIDTH,this.HEIGHT);
    }

    public GameScene(int width, int height, int margin) {
        this.WIDTH = height;
        this.HEIGHT = height;
        this.MARGIN = margin;
    }

    public int getWidth() {
        return WIDTH;
    }

    public int getHeight() {
        return HEIGHT;
    }

    public int getMargin() {
        return MARGIN;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public GraphicsContext getGraphicContext(){
        return canvas.getGraphicsContext2D();
    }
}
