package com.werka.gra;

import com.werka.gra.objects.Bullet;
import com.werka.gra.objects.Invader;
import com.werka.gra.objects.InvaderBoss;
import com.werka.gra.objects.Player;
import com.werka.gra.scene.GameScene;
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
import static com.werka.gra.objects.Player.PLAYER_SIZE;

public class HelloApplication extends Application {


    public static HashSet<String> activeKeys = new HashSet<>();

    private List<Invader> invaders = new ArrayList<>();

    private InvaderBoss boss;

    private List<Bullet> bullets;
    private Player player;

    private boolean gameOver;

    public static final int INVADER_SPEED = 10;

    public static final int BULLET_SPEED = 3;
    private int score;
    private int level = 1;


    @Override
    public void start(Stage stage) throws IOException {


        GameScene gameScene = new GameScene();
        player = new Player(gameScene.getWidth() / 2, gameScene.getHeight() - PLAYER_SIZE, 3);

        createInvadersForLevel();

        bullets = new ArrayList<>();
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
                    if (event.getCode() == KeyCode.SPACE && !gameOver) {
                        bullets.add(new Bullet(player.getX() + PLAYER_SIZE / 2, player.getY(), -BULLET_SPEED));
                    }
                });

                scene.setOnKeyReleased(keyEvent -> {
                            activeKeys.remove(keyEvent.getCode().toString());
                            System.out.println("release " + keyEvent.getCode().toString());
                        }
                );
                gameScene.getGraphicContext().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

                if (!gameOver) {
                    update();
                    render(gameScene);
                } else {
                    showGameOverMessage(gameScene);
                    if (activeKeys.contains(KeyCode.ENTER.toString())) {
                        gameOver = false;
                        createInvadersForLevel();
                        if (player.getLives() < 0) {
                            player.setLives(3);
                        }
                    }

                }


            }
        };

        timer.start();


    }

    private void createInvadersForLevel() {
        invaders.clear();

        if (level % 3 == 0) {
            boss = new InvaderBoss(100, 50, 100, INVADER_SPEED + level);
            invaders.add(boss);
        } else {
            invaders = createInvaders();
        }
    }

    private void showGameOverMessage(GameScene gameScene) {
        gameScene.getGraphicContext().setFill(Color.WHITE);
        gameScene.getGraphicContext().fillRect(0, 0, gameScene.getWidth(), gameScene.getHeight());
        if (gameOver && player.getLives() == 0) {
            gameScene.getGraphicContext().strokeText("Koniec gry", gameScene.getHeight() / 2, gameScene.getHeight() / 2);
        } else {
            gameScene.getGraphicContext().strokeText("Koniec poziomu", gameScene.getHeight() / 2, gameScene.getHeight() / 2);
        }
        gameScene.getGraphicContext().strokeText("Wciśnij enter aby grac dalej", gameScene.getHeight() / 2 + 150, gameScene.getHeight() / 2);
    }

    private void render(GameScene gameScene) {
        player.drawPlayer(gameScene);
//        if (boss != null) {
//            boss.drawInvader(gameScene);
//        }

        for (Invader invader : invaders) {
            invader.drawInvader(gameScene);
        }

        for (Bullet bullet : bullets) {
            bullet.drawBullet(gameScene);
        }

        gameScene.getGraphicContext().strokeText("Pozostało żyć:" + player.getLives(), 20, 20);
        gameScene.getGraphicContext().strokeText("Punkty:" + score, 200, 20);
        gameScene.getGraphicContext().strokeText("Poziom:" + level, 300, 20);

        if (boss != null) {
            gameScene.getGraphicContext().strokeText("Pozostało żyć BOSSa:" + boss.getLives(), 400, 20);
        }


    }

    private void update() {
        player.update();
//        if (boss != null) {
//            boss.update();
//        }

        for (Invader invader : invaders) {
            invader.update();
            if (player.intersects(invader)) {
                gameOver = true;
                player.decreaseLives();
                break;
            }
        }

        for (Bullet bullet : bullets) {
            bullet.update();
            if (bullet.isOutOfBounds()) {
                bullets.remove(bullet);
                break;
            }
            for (Invader invader : invaders) {
                if (bullet.intersects(invader)) {
                    if (invader instanceof InvaderBoss) {
                        ((InvaderBoss) invader).decreaseLives();
                        if (((InvaderBoss) invader).getLives() == 0) {
                            invaders.remove(invader);
                            bullets.remove(bullet);
                            boss = null;
                            score++;
                        }
                    } else {
                        invaders.remove(invader);
                        bullets.remove(bullet);
                        score++;
                    }
                    break;
                }
            }
        }
        if (invaders.isEmpty()) {
            gameOver = true;
            level++;
            bullets.clear();
        }

    }

    private List<Invader> createInvaders() {
        List<Invader> invaders = new ArrayList<>();
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 8; col++) {
                int x = col * 100 + 50;
                int y = row * 50 + 50;
                invaders.add(new Invader(x, y, INVADER_SIZE, INVADER_SPEED + level));
            }
        }
        return invaders;
    }

    public static void main(String[] args) {
        launch();
    }
}