package com.werka.gra;

import com.werka.gra.objects.*;
import com.werka.gra.scene.GameScene;
import com.werka.gra.scores.ScoreEntity;
import com.werka.gra.util.HibernateUtil;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import static com.werka.gra.objects.Invader.INVADER_SIZE;
import static com.werka.gra.objects.Player.PLAYER_SIZE;

public class HelloApplication extends Application {


    public static HashSet<String> activeKeys = new HashSet<>();

    private List<Invader> invaders = new ArrayList<>();

    private InvaderBoss boss;

    private List<Bullet> bullets;
    private List<InvaderBullet> invaderBullets;
    private Player player;

    private boolean gameOver = true;
    private String playerName;

    public static final int INVADER_SPEED = 10;

    public static final int BULLET_SPEED = 3;
    private int score;
    private int level = 1;
    private int bulletCounter = 0;
    private int invadersKilled = 0;


    @Override
    public void start(Stage stage) throws IOException {


        GameScene gameScene = new GameScene();
        player = new Player(gameScene.getWidth() / 2, gameScene.getHeight() - PLAYER_SIZE, 3);

        createInvadersForLevel();

        bullets = new ArrayList<>();
        invaderBullets = new ArrayList<>();
        Canvas canvas = gameScene.getCanvas();

        Pane root = new Pane(canvas);
        Scene scene = new Scene(root);

        if (playerName == null) {
            TextField textField = new TextField();
            Text text = new Text("Enter your name");
            Button button = new Button("Start");
            button.setOnAction(event -> {
                 playerName = textField.getText();
                gameOver = false;
                stage.setScene(scene);
            });
            VBox vBox = new VBox(10);
            vBox.getChildren().addAll(text, textField, button);
            Scene startScene = new Scene(vBox, 300, 300);
            stage.setScene(startScene);
            stage.show();
        } else {
            stage.setScene(scene);
            stage.show();
        }

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {


                scene.setOnKeyPressed(event -> {
                    //
                    activeKeys.add(event.getCode().toString());
                    System.out.println("Push " + event.getCode().toString());
                    handleStandardShoot(event);
                    handleSpecialShoots(event);
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
                    if(playerName != null && player.getLives()==0) {
                        saveScore();
                    }
                    showGameOverMessage(gameScene);
                    if (activeKeys.contains(KeyCode.ENTER.toString())) {
                        gameOver = false;
                        createInvadersForLevel();
                        bullets.clear();  // czyszczenie listy pocisków
                        invaderBullets.clear(); // czyszczenie listy pocisków przeciwnikow
                        if (player.getLives() < 0) {
                            player.setLives(3);
                            level = 1;
                        }
                    }
                }


            }
        };
        timer.start();
    }

    private void handleStandardShoot(KeyEvent event) { // obsługa strzału gracza i przeciwników
        if (event.getCode() == KeyCode.SPACE && !gameOver) {
            bullets.add(new Bullet(player.getX() + PLAYER_SIZE / 2, player.getY(), -BULLET_SPEED));
            bulletCounter++; // licznik pocisków
            if (bulletCounter % 5 == 0) {
                int noOfInvaders = invaders.size();
                Random random = new Random();
                int invaderToShot = random.nextInt(0, noOfInvaders - 1); // generowanie losowego numeru przeciwnika
                Invader invader = invaders.get(invaderToShot);
                invaderBullets.add(new InvaderBullet(invader.getX() + INVADER_SIZE / 2, invader.getY() + INVADER_SIZE, BULLET_SPEED));
            }
        }
    }

    private void handleSpecialShoots(KeyEvent event) { // obsługa specjalnych strzałów gracza
        if (event.getCode() == KeyCode.V && !gameOver) {
            if (player.getShoots() > 0) { // sprawdzenie czy gracz ma jeszcze strzały specjalne
                bullets.add(new Bullet(player.getX() + PLAYER_SIZE / 2, player.getY(), -BULLET_SPEED));
                bullets.add(new Bullet(player.getX() + PLAYER_SIZE, player.getY(), -BULLET_SPEED));
                bullets.add(new Bullet(player.getX(), player.getY(), -BULLET_SPEED));
                bulletCounter += 3;
                player.decreaseSpecialBullets();
            }
        }
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

    private void saveScore() {
        ScoreEntity scoreEntity = new ScoreEntity(playerName, score);

       /* Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // start a transaction
            transaction = session.beginTransaction();
            session.save(scoreEntity);
            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }*/
    }

    private void render(GameScene gameScene) {
        player.drawPlayer(gameScene);

        for (Invader invader : invaders) {
            invader.drawInvader(gameScene);
        }

        for (Bullet bullet : bullets) {
            bullet.drawBullet(gameScene);
        }

        for (InvaderBullet invaderBullet : invaderBullets) {
            invaderBullet.drawBullet(gameScene);
        }

        gameScene.getGraphicContext().strokeText("Player " + playerName, 400, 20);
        gameScene.getGraphicContext().strokeText("Pozostało żyć:" + player.getLives(), 20, 20);
        gameScene.getGraphicContext().strokeText("Specjalna broń:" + player.getShoots(), 20, 70);
        gameScene.getGraphicContext().strokeText("Punkty:" + score, 200, 20);
        gameScene.getGraphicContext().strokeText("Poziom:" + level, 300, 20);

        if (boss != null) {
            gameScene.getGraphicContext().strokeText("Pozostało żyć BOSSa:" + boss.getLives(), 500, 20);
        }


    }

    private void update() {
        player.update();

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
                        invadersKilled += 10;
                    } else {
                        invadersKilled++;
                        invaders.remove(invader);
                        bullets.remove(bullet);
                        score++;
                    }
                    if (invadersKilled % 50 == 0) {
                        player.increaseLives();
                    }
                    if (invadersKilled % 10 == 0) {
                        player.increaseSpecialShoots();
                    }
                    break;
                }
            }
        }

        for (InvaderBullet invaderBullet : invaderBullets) {
            invaderBullet.update();
            if (invaderBullet.isOutOfBounds()) {
                invaderBullets.remove(invaderBullet);
                break;
            }
            if (invaderBullet.intersects(player)) {
                invaderBullets.remove(invaderBullet);
                player.decreaseLives();
                break;
            }

        }

        if(player.getLives() == 0){
            gameOver = true;
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