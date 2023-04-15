package com.werka.gra;

import com.werka.gra.player.Player;
import com.werka.gra.scene.GameScene;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        int speed = 10;

        GameScene gameScene = new GameScene();

        Player player = new Player(0, 380, 3);

        Canvas canvas = new Canvas(gameScene.getWidth(), gameScene.getHeight());

        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setFill(Color.RED);
        gc.fillRect(player.getX(), player.getY(), 20, 20);

        Pane root = new Pane(canvas);

        Scene scene = new Scene(root);

        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.LEFT) {
                if (player.getX() >= gameScene.getMargin()) {
                    player.setX(player.getX() - speed);
                }
            } else if (event.getCode() == KeyCode.RIGHT) {
                if (player.getX() <= gameScene.getWidth() - gameScene.getMargin()) {
                    player.setX(player.getX() + speed);
                }
            }

            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            gc.setFill(Color.RED);
            gc.fillRect(player.getX(), player.getY(), 20, 20);
        });

        stage.setScene(scene);
        stage.show();


    }

    public static void main(String[] args) {
        launch();
    }
}