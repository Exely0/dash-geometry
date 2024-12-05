package com.example.geometrydash.player;

import com.example.geometrydash.constants.Constants;
import com.example.geometrydash.enums.PlayerType;
import com.example.geometrydash.level.Level;
import com.example.geometrydash.game.*;
import javafx.scene.layout.Pane;

public class WheelPlayer extends AbstractPlayer {

    public WheelPlayer(double x, double y, boolean invertedGravity) {
        super(x, y, Constants.TILE_SIZE * Constants.GRAVITY, Constants.TILE_SIZE * Constants.MAX_FALL_SPEED,
                -Constants.TILE_SIZE * Constants.JUMP_STRENGTH,
                "/assets/player/wheel.png", invertedGravity);
    }

    @Override
    public void action() {
        if (!isOnGround) {
            return;
        }
        this.updateGravity(!this.isInvertedGravity());
        this.velocityY = -this.jumpStrength / 2;
        this.y += this.velocityY;
        this.isOnGround = false;
    }

    /**
     * Updates the position of the DashPlayer.
     *
     * @param level the current level
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

        } else {
            this.velocityY = 0;
            this.setMegaSpeedOn(false);
        }

        if (!isInvertedGravity) {
            this.sprite.setRotate(sprite.getRotate() + 5);

        } else {
            this.sprite.setRotate(sprite.getRotate() - 5);
        }
        this.y += this.velocityY;
        applyTrail(this.x, this.y, pane);
        setPosition(y);
    }
}