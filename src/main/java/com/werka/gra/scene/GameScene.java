package com.werka.gra.scene;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class GameScene {
    private int width;

    private int height;

    private int margin;

    private Canvas canvas;



    public GameScene() {
        this.width = 1024;
        this.height = 786;
        this.margin = 10;
        this.canvas=new Canvas(this.width,this.height);
    }

    public GameScene(int width, int height, int margin) {
        this.width = height;
        this.height = height;
        this.margin = margin;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getMargin() {
        return margin;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public GraphicsContext getGraphicContext(){
        return canvas.getGraphicsContext2D();
    }
}
