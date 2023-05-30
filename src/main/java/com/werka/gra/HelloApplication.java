package com.werka.gra;

import com.werka.gra.objects.Invader;
import com.werka.gra.objects.Player;
import com.werka.gra.scene.GameScene;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static com.werka.gra.objects.Invader.INVADER_SIZE;

public class HelloApplication extends Application {


    public static HashSet<String> activeKeys = new HashSet<>();

    private List<Invader> invaders;
    private Player player;

    private boolean gameOver;

    public static final int INVADER_SPEED = 30;

    @Override
    public void start(Stage stage) throws IOException {


        GameScene gameScene = new GameScene();
        player = new Player(gameScene.getWidth()/2, gameScene.getHeight()- Player.PLAYER_SIZE, 3);
        invaders = createInvaders();
        Canvas canvas = gameScene.getCanvas();

        Pane root = new Pane(canvas);
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();


        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                scene.setOnKeyPressed(event -> {
                    //
                    activeKeys.add(event.getCode().toString());
                    System.out.println("Push " + event.getCode().toString());
                });

                scene.setOnKeyReleased(keyEvent -> {
                            activeKeys.remove(keyEvent.getCode().toString());
                            System.out.println("release " + keyEvent.getCode().toString());
                        }
                );
                gameScene.getGraphicContext().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

                if(!gameOver) {
                    update();
                    render(gameScene);
                }else {
                    showGameOverMessage(gameScene);
                    if( activeKeys.contains(KeyCode.ENTER.toString())) {
                        gameOver = false;
                        resetPositions();
                    }

                }


            }
        };

        timer.start();


    }

    private void resetPositions() {
        invaders.clear();
        invaders = createInvaders();
    }

    private void showGameOverMessage(GameScene gameScene) {
        gameScene.getGraphicContext().setFill(Color.WHITE);
        gameScene.getGraphicContext().fillRect(0,0, gameScene.getWidth(), gameScene.getHeight());
        gameScene.getGraphicContext().strokeText("Koniec gry", gameScene.getHeight()/2, gameScene.getHeight()/2 );
        gameScene.getGraphicContext().strokeText("Wciśnij enter aby grac dalej", gameScene.getHeight()/2+50, gameScene.getHeight()/2 );
    }

    private void render(GameScene gameScene) {
        player.drawPlayer(gameScene);

        for (Invader invader : invaders) {
            invader.drawInvader(gameScene);
        }

        gameScene.getGraphicContext().strokeText("Pozostało żyć:"+player.getLives(), 20, 20 );


    }

    private void update() {
        player.update();

        for (Invader invader : invaders) {
            invader.update();
            if(player.intersects(invader)) {
                gameOver = true;
                player.decreaseLives();
                break;
            }
        }

    }

    private List<Invader> createInvaders() {
        List<Invader> invaders = new ArrayList<>();
        for(int row = 0; row < 4; row++) {
            for(int col = 0; col < 8; col++) {
                int x = col * 100 + 50;
                int y = row * 50 + 50;
                invaders.add(new Invader(x,y, INVADER_SIZE, INVADER_SPEED));
            }
        }
        return invaders;
    }

    public static void main(String[] args) {
        launch();
    }
}