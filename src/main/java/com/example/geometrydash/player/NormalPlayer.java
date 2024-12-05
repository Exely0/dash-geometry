package com.example.geometrydash.player;

import com.example.geometrydash.constants.Constants;
import com.example.geometrydash.level.Level;
import com.example.geometrydash.game.*;
import javafx.animation.RotateTransition;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 * Class representing a NormalPlayer in the Geometry Dash game.
 */
public class NormalPlayer extends AbstractPlayer {

    private final RotateTransition jumpRotateTransition;
    private RotateTransition fallRotateTransition;
    private boolean isJumping;

    /**
     * Constructs a NormalPlayer with default parameters.
     *
     * @param x the x-coordinate of the player
     * @param y the y-coordinate of the player
     * @param invertedGravity whether the gravity is inverted
     */
    public NormalPlayer(double x, double y, boolean invertedGravity) {
        super(x, y, Constants.TILE_SIZE * Constants.GRAVITY, Constants.TILE_SIZE * Constants.MAX_FALL_SPEED,
                -Constants.TILE_SIZE * Constants.JUMP_STRENGTH,
                "/assets/player/cube.png", invertedGravity);

        jumpRotateTransition = new RotateTransition(Duration.millis(300), this.sprite);
        jumpRotateTransition.setByAngle(90);
        jumpRotateTransition.setCycleCount(1);

        isJumping = false;
    }

    /**
     * Updates the position of the NormalPlayer.
     *
     * @param level the current level
     * @param game the current game instance
     * @param pane the pane to which the player belongs
     */
    @Override
    public void updatePosition(Level level, Game game, Pane pane) {
        handleCollide(level, game, pane);

        if (!isOnGround()) {
            this.velocityY += this.gravity;
            if (!isInvertedGravity) {
                if (this.velocityY > this.maxFallSpeed) {
                    this.velocityY = maxFallSpeed;
                }
            } else {
                if (this.velocityY < this.maxFallSpeed) {
                    this.velocityY = maxFallSpeed;
                }
            }
            if (this.isMegaSpeedOn()) {
                this.velocityY = this.getMegaSpeed();
            }

            if (!isJumping && (fallRotateTransition == null
                    || fallRotateTransition.getStatus() != javafx.animation.Animation.Status.RUNNING)) {
                fallRotateTransition = new RotateTransition(Duration.millis(300), this.sprite);
                fallRotateTransition.setByAngle(360);
                fallRotateTransition.setCycleCount(RotateTransition.INDEFINITE);
                fallRotateTransition.play();
            }
        } else {
            this.velocityY = 0;
            isJumping = false;
            this.setMegaSpeedOn(false);
            if (fallRotateTransition != null) {
                fallRotateTransition.stop();
                fallRotateTransition = null;
            }
            double currentRotation = this.sprite.getRotate();
            double nearest90 = Math.round(currentRotation / 90) * 90;
            this.sprite.setRotate(nearest90);
        }
        this.y += this.velocityY;
        applyTrail(this.x, this.y, pane);
        setPosition(y);
    }

    /**
     * Defines the action to be taken by the NormalPlayer.
     */
    @Override
    public void action() {
        if (isOnGround() && !isJumping) {
            this.velocityY += this.jumpStrength;
            this.y += this.velocityY;
            this.isJumping = true;
            jumpRotateTransition.play();
        }
    }
}