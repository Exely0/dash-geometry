package com.example.geometrydash.game;

import java.lang.reflect.InvocationTargetException;

import com.example.geometrydash.constants.Constants;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import com.example.geometrydash.level.Level;
import com.example.geometrydash.player.AbstractPlayer;
import com.example.geometrydash.player.DashPlayer;
import com.example.geometrydash.player.RocketPlayer;
import com.example.geometrydash.player.SpaceshipPlayer;

import javafx.scene.media.Media;

import java.net.URL;
import java.util.Objects;

public class Game {

    private Level level;
    private AbstractPlayer player;
    private boolean isRunning;
    private Timeline gameLoop;
    private boolean hitboxMode;
    private Pane pane;
    private MediaPlayer mediaPlayer;
    private double lastTime = 0;

    public Game(Level level, AbstractPlayer player, Pane pane, boolean hitboxMode) {
        this.level = Objects.requireNonNull(level, "Level must not be null");
        this.player = Objects.requireNonNull(player, "Player must not be null");
        this.pane = Objects.requireNonNull(pane, "Pane must not be null");
        this.hitboxMode = hitboxMode;

        URL musicUrl = getClass().getResource("/assets/musics/take_my_hand.mp3");
        if (musicUrl == null) {
            throw new IllegalArgumentException("Music file not found: /assets/musics/take_my_hand.mp3");
        }

        Media media = new Media(musicUrl.toExternalForm());
        mediaPlayer = new MediaPlayer(media);

        Image backgroundImage = new Image(
                Objects.requireNonNull(getClass().getResourceAsStream("/assets/background.png")));
        ImageView backgroundView = new ImageView(backgroundImage);
        backgroundView.setFitWidth(level.getWidth() * Constants.TILE_SIZE * 2);
        backgroundView.setFitHeight(level.getHeight() * Constants.TILE_SIZE * 2);
        backgroundView.setLayoutX(-Constants.TILE_SIZE);
        backgroundView.setLayoutY(-Constants.TILE_SIZE);

        level.loadMap();

        if (!hitboxMode) {
            level.displayLevel();
            pane.getChildren().add(backgroundView);
            pane.getChildren().add(player.getSprite());
            pane.getChildren().add(level.getLevelView());

        } else {
            level.displayLevelHitbox();
            pane.getChildren().add(level.getHitboxLayerView());
            pane.getChildren().add(player.getRedHitboxView());
            pane.getChildren().add(player.getBlueHitboxView());
        }

        pane.getChildren().add(player.getDeathSprite());

        this.gameLoop = new Timeline(new KeyFrame(Duration.millis(10), e -> {
            if (!this.player.isDead()) {
                this.player.updatePosition(level, this, pane);
                level.updatePosition();
                if (this.player.isSpacePressed()) {
                    this.player.action();
                }
            }
            if (this.player.isDead()) {
                this.level.setVelocityX(0);
                this.player.setVelocityY(0);
            }

            lastTime = System.currentTimeMillis();
        }));
        gameLoop.setCycleCount(Animation.INDEFINITE);
    }

    public AbstractPlayer getPlayer() {
        return player;
    }

    public void setPlayer(AbstractPlayer player) {
        this.player = player;
    }

    public void handleModifyPlayerType(Class<? extends AbstractPlayer> clazz) {
        try {
            AbstractPlayer newPlayer = clazz.getDeclaredConstructor(double.class, double.class, boolean.class)
                    .newInstance(player.getX(), player.getY(), player.isInvertedGravity());

            if (!hitboxMode) {
                pane.getChildren().remove(player.getSprite());
                pane.getChildren().add(newPlayer.getSprite());
            } else {
                pane.getChildren().remove(player.getRedHitboxView());
                pane.getChildren().add(newPlayer.getRedHitboxView());
                pane.getChildren().remove(player.getBlueHitboxView());
                pane.getChildren().add(newPlayer.getBlueHitboxView());
            }

            setPlayer(newPlayer);

        } catch (InstantiationException | IllegalAccessException | InvocationTargetException
                | NoSuchMethodException e) {
            e.printStackTrace();
            System.err.println("Erreur lors de la modification du type de joueur.");
        }
    }

    public void handleAction(Scene scene) {
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.SPACE) {
                player.setSpacePressed(true);
                if (!player.isActionTriggered()) {
                    Timeline timeline = new Timeline(new KeyFrame(
                            Duration.seconds(0.01),
                            e -> player.setActionTriggered(true)));
                    timeline.setCycleCount(1);
                    timeline.play();
                }
            } else if (event.getCode() == KeyCode.ESCAPE) {
                Platform.exit();
            }
        });

        scene.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.SPACE && (player instanceof RocketPlayer || player instanceof SpaceshipPlayer
                    || player instanceof DashPlayer)) {
                player.releaseAction();
            }
            if (event.getCode() == KeyCode.SPACE) {
                player.setSpacePressed(false);
                player.setActionTriggered(false);
            }
        });
    }

    public void start() {
        this.gameLoop.play();
        playMusic();
        handleAction(pane.getScene());
    }

    public void initializeMusic() {
        Media media = new Media(
                Objects.requireNonNull(getClass().getResource(this.level.getMusicFilePath())).toExternalForm());
        mediaPlayer = new MediaPlayer(media);
    }

    public void playMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.play();
        }
    }

    public void stopMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }
}