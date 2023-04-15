package com.werka.gra.scene;

public class GameScene {
    private int width;

    private int height;

    private int margin;


    public GameScene() {
        this.width = 1024;
        this.height = 786;
        this.margin = 10;
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
}
