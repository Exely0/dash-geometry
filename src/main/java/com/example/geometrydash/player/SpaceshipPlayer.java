package com.example.geometrydash.player;

import java.util.List;

import com.example.geometrydash.constants.Constants;
import com.example.geometrydash.level.Level;
import com.example.geometrydash.obstacles.Block;
import com.example.geometrydash.obstacles.GameObject;
import com.example.geometrydash.obstacles.Spike;
import com.example.geometrydash.game.*;

import javafx.scene.layout.Pane;

/**
 * Class representing a SpaceshipPlayer in the Geometry Dash game.
 */
public class SpaceshipPlayer extends AbstractPlayer {

    private boolean spacePressed = false;
    private boolean isOnCeiling = false;

    /**
     * Constructs a SpaceshipPlayer with default parameters.
     */
    public SpaceshipPlayer(double x, double y, boolean invertedGravity) {
        super(x, y, Constants.TILE_SIZE * Constants.GRAVITY, Constants.TILE_SIZE * Constants.MAX_FALL_SPEED,
                Constants.TILE_SIZE * Constants.JUMP_STRENGTH, "/assets/player/spaceship.png", invertedGravity);
    }

    /**
     * Defines the action to be taken by the SpaceshipPlayer.
     */
    @Override
    public void action() {
        if (!spacePressed && !isOnCeiling) {
            if (isInvertedGravity) {
                this.velocityY = -jumpStrength;
            } else {
                this.velocityY = -jumpStrength;
            }
            this.isJumping = true;
            spacePressed = true;
        }
    }

    /**
     * Updates the position of the SpaceshipPlayer.
     *
     * @param level the current level
     */
    @Override
    public void updatePosition(Level level, Game game, Pane pane) {
        handleCollide(level, game, pane);
        if (!isOnGround() && !isOnCeiling()) {
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
        } else {
            this.velocityY = 0;
        }

        this.y += this.velocityY;
        applyTrail(this.x, this.y, pane);
        setPosition(y);
    }

    @Override
    protected void handleCommonCollide(Level level, Game game, Pane pane, List<GameObject> tileList) {
        this.isOnGround = false;
        this.isOnCeiling = false;

        for (GameObject gameObject : tileList) {
            if (gameObject instanceof Spike) {
                if (doesCollideRed(gameObject, level)) {
                    System.out.println("SPIKE DEATH");
                    this.die();
                    break;
                }
            } else if (gameObject instanceof Block) {
                if (doesCollideBlue(gameObject, level)) {
                    System.out.println("BLOCK DEATH");
                    this.die();
                    break;
                } else if (doesCollideRed(gameObject, level)) {
                    if (!isInvertedGravity) {
                        if (this.y + 0.8 * Constants.TILE_SIZE < gameObject.getY() && this.velocityY >= 0) {
                            this.y = gameObject.getY() - Constants.TILE_SIZE;
                            this.isOnGround = true;
                            this.isJumping = false;
                        }
                    } else {
                        if (this.y > gameObject.getY() + 0.8 * Constants.TILE_SIZE && this.velocityY <= 0) {
                            this.y = gameObject.getY() + Constants.TILE_SIZE;
                            this.isOnGround = true;
                            this.isJumping = false;
                        }
                    }
                    if (!isInvertedGravity) {
                        if (this.y > gameObject.getY() + 0.8 * Constants.TILE_SIZE && this.velocityY < 0) {
                            this.isOnCeiling = true;
                            this.y = gameObject.getY() + Constants.TILE_SIZE + 1;
                        }
                    } else {
                        if (this.y + 0.8 * Constants.TILE_SIZE < gameObject.getY() && this.velocityY > 0) {
                            this.isOnCeiling = true;
                            this.y = gameObject.getY() - Constants.TILE_SIZE - 1;
                        }
                    }
                }
            } else if (gameObject instanceof GameObject) {
                gameObject.triggerEffect(this, level, game, pane);
            }
        }

        if (this.y > level.getHeight() * Constants.TILE_SIZE) {
            System.out.println("FALL DEATH");
            this.die();
        }

        if (this.y < -25 * Constants.TILE_SIZE) {
            System.out.println("ROOF DEATH");
            this.die();
        }
    }

    /**
     * Releases the action of the SpaceshipPlayer.
     */
    @Override
    public void releaseAction() {
        spacePressed = false;
    }

    /**
     * Checks if the player is on the ceiling.
     * 
     * @return true if the player is on the ceiling, false otherwise.
     */
    public boolean isOnCeiling() {
        return isOnCeiling;
    }
}
