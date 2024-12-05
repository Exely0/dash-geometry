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
 * Class representing a RocketPlayer in the Geometry Dash game.
 */
public class RocketPlayer extends AbstractPlayer {
    private double lastAction = 0;
    private static final double MAX_SPEED = -Constants.TILE_SIZE * Constants.MAX_FALL_SPEED * 0.9;
    private static final double ACCELERATION = Constants.TILE_SIZE * 0.003;
    private static final double INITIAL_BOOST = -Constants.TILE_SIZE * 0.01;
    private boolean isSpacePressed = false;
    private boolean isOnCeiling = false;

    /**
     * Constructs a RocketPlayer with default parameters.
     */
    public RocketPlayer(double x, double y, boolean invertedGravity) {
        super(x, y, Constants.TILE_SIZE * 0.02, Constants.TILE_SIZE * 0.013, -Constants.TILE_SIZE * 0.02,
                "/assets/player/rocket.png", invertedGravity);
    }

    /**
     * Defines the action to be taken by the RocketPlayer.
     */
    @Override
    public void action() {
        if (isDead) {
            return;
        }
        isSpacePressed = true;
        if (System.currentTimeMillis() - lastAction < 50) {
            return;
        }
        if (isOnGround()) {
            if (!isInvertedGravity) {
                velocityY = INITIAL_BOOST;

            } else {
                velocityY = -INITIAL_BOOST;
            }
        }
        lastAction = System.currentTimeMillis();
    }

    /**
     * Updates the position of the RocketPlayer.
     *
     * @param level the current level
     */
    @Override
    public void updatePosition(Level level, Game game, Pane pane) {
        handleCollide(level, game, pane);

        if (isSpacePressed) {
            if (isInvertedGravity) {
                velocityY += ACCELERATION;
                if (velocityY > -MAX_SPEED) {
                    velocityY = -MAX_SPEED;
                }
            } else {
                velocityY -= ACCELERATION;
                if (velocityY < MAX_SPEED) {
                    velocityY = MAX_SPEED;
                }
            }
        } else {
            if (isInvertedGravity) {
                velocityY -= ACCELERATION;
                if (velocityY < MAX_SPEED) {
                    velocityY = MAX_SPEED;
                }
            } else {
                velocityY += ACCELERATION;
                if (velocityY > -MAX_SPEED) {
                    velocityY = -MAX_SPEED;
                }
            }

        }
        if (isOnGround || isOnCeiling) {
            this.velocityY = 0;
            applyTrail(this.x, this.y, pane);
        }

        y += velocityY;
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
                            this.y = gameObject.getY() + Constants.TILE_SIZE;
                        }
                    } else {
                        if (this.y + 0.8 * Constants.TILE_SIZE < gameObject.getY() && this.velocityY > 0) {
                            this.isOnCeiling = true;
                            this.y = gameObject.getY() - Constants.TILE_SIZE;
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
     * Releases the action of the RocketPlayer.
     */
    @Override
    public void releaseAction() {
        isSpacePressed = false;
    }
}