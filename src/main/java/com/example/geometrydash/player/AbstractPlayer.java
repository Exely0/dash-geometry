package com.example.geometrydash.player;

import javafx.scene.shape.Rectangle;
import javafx.geometry.Bounds;

import com.example.geometrydash.game.*;
import com.example.geometrydash.constants.Constants;
import com.example.geometrydash.enums.HitboxType;
import com.example.geometrydash.hitbox.Hitbox;
import com.example.geometrydash.level.*;
import com.example.geometrydash.obstacles.Block;
import com.example.geometrydash.obstacles.GameObject;
import com.example.geometrydash.obstacles.Spike;
import com.example.geometrydash.enums.PlayerType;
import com.example.geometrydash.visual_effects.Effects;

import javafx.scene.layout.Pane;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javafx.util.Duration;

/**
 * Abstract class representing a player in the Geometry Dash game.
 */
public abstract class AbstractPlayer {
    public double getX() {
        return x;
    }

    protected double x;
    protected double y;
    protected double velocityY;
    protected double maxFallSpeed;
    protected double jumpStrength;
    protected double gravity;
    protected final ImageView sprite;
    protected final ImageView blueHitboxView;
    protected final ImageView redHitboxView;

    public void setActionTriggered(boolean actionTriggered) {
        this.actionTriggered = actionTriggered;
    }

    protected final ImageView deathSprite;
    protected List<Image> deathImages = new ArrayList<>();
    protected boolean isOnGround;
    protected Image skin;
    protected boolean isInvertedGravity;
    protected boolean isJumping;
    protected boolean isDead;
    protected int explosionFrameIndex;
    protected Hitbox redHitbox;
    protected Hitbox blueHitbox;
    protected final long frameDelay = 250;
    private long lastTrailTime;
    private double megaSpeed;
    private boolean isMegaSpeedOn;
    protected boolean isSpacePressed;
    protected PlayerType playerType;
    protected boolean actionTriggered;

    /**
     * Constructs an AbstractPlayer with the specified parameters.
     *
     * @param x            the x-coordinate of the player
     * @param y            the y-coordinate of the player
     * @param gravity      the gravity affecting the player
     * @param maxFallSpeed the maximum fall speed of the player
     * @param jumpStrength the jump strength of the player
     * @param skin         the path to the player's skin image
     * @param playerType   the type of the player
     */
    protected AbstractPlayer(double x, double y, double gravity, double maxFallSpeed, double jumpStrength,
            String skin, boolean invertedGravity) {
        this.skin = new Image(Objects.requireNonNull(getClass().getResource(skin)).toExternalForm());

        sprite = new ImageView(this.skin);
        if (this instanceof RocketPlayer == false) {
            sprite.setFitHeight(Constants.TILE_SIZE);
            sprite.setFitWidth(Constants.TILE_SIZE);
            sprite.setX(x);
            sprite.setY(y);
        } else {
            sprite.setFitHeight(Constants.TILE_SIZE * 1.2);
            sprite.setFitWidth(Constants.TILE_SIZE * 1.2);
            sprite.setX(x + (Constants.TILE_SIZE - sprite.getFitWidth()) / 2);
            sprite.setY(y + (Constants.TILE_SIZE - sprite.getFitHeight()) / 2);
        }

        deathSprite = new ImageView();

        deathSprite.setFitHeight(500);
        deathSprite.setFitWidth(500);
        this.isInvertedGravity = false;
        this.isJumping = false;
        this.isDead = false;
        this.isSpacePressed = false;
        this.megaSpeed = Constants.MEGA_SPEED * Constants.TILE_SIZE;
        this.isMegaSpeedOn = false;
        this.x = x;
        this.y = y;
        this.velocityY = 0;
        this.gravity = gravity;
        this.jumpStrength = jumpStrength;
        this.isOnGround = false;
        this.maxFallSpeed = maxFallSpeed;

        this.blueHitbox = createBlueHitbox();
        this.redHitbox = createRedHitbox();
        this.redHitboxView = new ImageView(this.redHitbox.createHitboxImage());
        this.blueHitboxView = new ImageView(this.blueHitbox.createHitboxImage());
        this.redHitboxView.setFitHeight(Constants.TILE_SIZE);
        this.redHitboxView.setFitWidth(Constants.TILE_SIZE);
        this.redHitboxView.setX(x);
        this.redHitboxView.setY(y);
        this.blueHitboxView.setFitHeight((double) Constants.TILE_SIZE / 3);
        this.blueHitboxView.setFitWidth((double) Constants.TILE_SIZE / 3);
        this.blueHitboxView.setX(x + (double) (Constants.TILE_SIZE - Constants.TILE_SIZE / 3) / 2);
        this.blueHitboxView.setY(y + (double) (Constants.TILE_SIZE - Constants.TILE_SIZE / 3) / 2);

        this.updateGravity(invertedGravity);

        for (int i = 0; i < 11; i++) {
            deathImages.add(new Image(Objects
                    .requireNonNull(getClass().getResource("/assets/explosion/tile" + (i + 1) + ".png"))
                    .toExternalForm()));
        }
        explosionFrameIndex = 0;
    }

    public boolean isSpacePressed() {
        return isSpacePressed;
    }

    public void setInvertedGravity(boolean isInvertedGravity) {
        this.isInvertedGravity = isInvertedGravity;
    }

    public void updateGravity(boolean newGravity) {
        if (newGravity != this.isInvertedGravity) {
            this.jumpStrength *= -1;
            this.gravity *= -1;
            this.megaSpeed *= -1;
            this.maxFallSpeed *= -1;
            this.isInvertedGravity = newGravity;
        }
        if (isInvertedGravity) {
            sprite.setScaleY(-1);
        } else {
            sprite.setScaleY(1);
        }
    }

    public double getMegaSpeed() {
        return megaSpeed;
    }

    public boolean isInvertedGravity() {
        return isInvertedGravity;
    }

    public void setX(double x) {
        this.x = x;
    }

    /**
     * Plays the explosion animation when the player dies.
     */
    public void playExplosionAnimation() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(frameDelay), e -> {
            if (explosionFrameIndex < deathImages.size()) {
                deathSprite.setImage(deathImages.get(explosionFrameIndex));
                explosionFrameIndex++;
            }
        }));
        timeline.setCycleCount(deathImages.size());
        timeline.play();
    }

    /**
     * Creates the blue hitbox for the player.
     *
     * @return the blue hitbox
     */
    private static Hitbox createBlueHitbox() {
        Rectangle blueRectangle = new Rectangle(0, 0, Constants.TILE_SIZE, Constants.TILE_SIZE);
        return new Hitbox(HitboxType.BLUE, blueRectangle, null);
    }

    /**
     * Creates the red hitbox for the player.
     *
     * @return the red hitbox
     */
    private static Hitbox createRedHitbox() {
        Rectangle redRectangle = new Rectangle(0, 0, Constants.TILE_SIZE, Constants.TILE_SIZE);
        return new Hitbox(HitboxType.RED, redRectangle, null);
    }

    public boolean isActionTriggered() {
        return actionTriggered;
    }

    /**
     * Gets the death sprite of the player.
     *
     * @return the death sprite
     */
    public ImageView getDeathSprite() {
        return deathSprite;
    }

    /**
     * Checks if the player is dead.
     *
     * @return true if the player is dead, false otherwise
     */
    public boolean isDead() {
        return isDead;
    }

    /**
     * Sets the vertical velocity of the player.
     *
     * @param velocityY the vertical velocity to set
     */
    public void setVelocityY(double velocityY) {
        this.velocityY = velocityY;
    }

    /**
     * Gets the vertical velocity of the player.
     *
     * @return the vertical velocity
     */
    public double getVelocityY() {
        return velocityY;
    }

    /**
     * Gets the blue hitbox view of the player.
     *
     * @return the blue hitbox view
     */
    public ImageView getBlueHitboxView() {
        return blueHitboxView;
    }

    /**
     * Gets the red hitbox view of the player.
     *
     * @return the red hitbox view
     */
    public ImageView getRedHitboxView() {
        return redHitboxView;
    }

    /**
     * Sets the y-coordinate of the player.
     *
     * @param y the y-coordinate to set
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Abstract method to define the player's action.
     */
    public abstract void action();

    /**
     * Abstract method to update the player's position.
     *
     * @param level the current level
     */
    public abstract void updatePosition(Level level, Game game, Pane pane);

    public void setSpacePressed(boolean isSpacePressed) {
        this.isSpacePressed = isSpacePressed;
    }

    /**
     * Handles the player's death.
     */
    protected void die() {
        sprite.setImage(null);
        deathSprite.setX(250);
        deathSprite.setY(250);
        this.isDead = true;
        playExplosionAnimation();
    }

    /**
     * Sets the position of the player.
     *
     * @param y the y-coordinate to set
     */
    protected void setPosition(double y) {
        this.y = y;
        sprite.setY(y);
        blueHitboxView.setY(y + (double) (Constants.TILE_SIZE - Constants.TILE_SIZE / 3) / 2);
        redHitboxView.setY(y);
    }

    /**
     * Gets the sprite of the player.
     *
     * @return the sprite
     */
    public ImageView getSprite() {
        return sprite;
    }

    /**
     * Gets the y-coordinate of the player.
     *
     * @return the y-coordinate
     */
    public double getY() {
        return y;
    }

    /**
     * Checks if the player collides with a red hitbox.
     *
     * @param gameObject the game object to check collision with
     * @param level      the current level
     * @return true if the player collides with the red hitbox, false otherwise
     */
    public boolean doesCollideRed(GameObject gameObject, Level level) {
        Rectangle objectHitbox = gameObject.createHitbox(gameObject.getX() + level.getX(), gameObject.getY());
        Bounds objectBounds = objectHitbox.getBoundsInParent();
        Bounds redHitboxBounds = this.redHitboxView.getBoundsInParent();
        return objectBounds.intersects(redHitboxBounds);
    }

    /**
     * Checks if the player collides with a blue hitbox.
     *
     * @param gameObject the game object to check collision with
     * @param level      the current level
     * @return true if the player collides with the blue hitbox, false otherwise
     */
    public boolean doesCollideBlue(GameObject gameObject, Level level) {
        Rectangle objectHitbox = gameObject.createHitbox(gameObject.getX() + level.getX(), gameObject.getY());
        Bounds objectBounds = objectHitbox.getBoundsInParent();
        Bounds blueHitboxBounds = this.blueHitboxView.getBoundsInParent();
        return blueHitboxBounds.intersects(objectBounds);
    }

    public void setMegaSpeedOn(boolean isMegaSpeedOn) {
        this.isMegaSpeedOn = isMegaSpeedOn;
    }

    public boolean isMegaSpeedOn() {
        return isMegaSpeedOn;
    }

    /**
     * Handles common collision logic for the player.
     *
     * @param level    the current level
     * @param tileList the list of tiles to check collision with
     */
    protected void handleCommonCollide(Level level, Game game, Pane pane, List<GameObject> tileList) {
        this.isOnGround = false;
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
                        if (isMegaSpeedOn()) {
                            if (this.y < gameObject.getY() && this.velocityY >= 0) {
                                this.y = gameObject.getY() - Constants.TILE_SIZE;
                                this.isOnGround = true;
                                this.isJumping = false;
                            }
                        } else {
                            if (this.y + 0.8 * Constants.TILE_SIZE < gameObject.getY() && this.velocityY >= 0) {
                                this.y = gameObject.getY() - Constants.TILE_SIZE;
                                this.isOnGround = true;
                                this.isJumping = false;
                            }

                        }

                    } else {
                        if (isMegaSpeedOn()) {
                            if (this.y + Constants.TILE_SIZE > gameObject.getY() && this.velocityY <= 0) {
                                this.y = gameObject.getY() + Constants.TILE_SIZE;
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

                    }
                }

            } else if (gameObject instanceof GameObject) {
                if (doesCollideRed(gameObject, level)) {
                    if (!gameObject.isUsed()) {
                        gameObject.triggerEffect(this, level, game, pane);
                        break;
                    }

                }
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

    public void applyTrail(double x, double y, Pane pane) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastTrailTime >= 100) {
            Effects.applyTrail(pane, x, y);
            lastTrailTime = currentTime;
        }
    }

    /**
     * Gets the list of collision tiles for the player.
     *
     * @param level the current level
     * @return the list of collision tiles
     */
    protected List<GameObject> getCollisionTiles(Level level) {
        double playerTopY = this.y - 0.1;
        double playerBottomY = this.y + Constants.TILE_SIZE;
        double playerLeftX = this.x - 0.1;
        double playerRightX = this.x + Constants.TILE_SIZE;
        double playerCenterX = this.x + Constants.TILE_SIZE / 2;
        double playerCenterY = this.y + Constants.TILE_SIZE / 2;

        GameObject tileBottomLeft = level.getTileTypeAt(playerLeftX, playerBottomY);
        GameObject tileBottomRight = level.getTileTypeAt(playerRightX, playerBottomY);
        GameObject tileTopLeft = level.getTileTypeAt(playerLeftX, playerTopY);
        GameObject tileTopRight = level.getTileTypeAt(playerRightX, playerTopY);
        GameObject tileCenterLeft = level.getTileTypeAt(playerLeftX, playerCenterY);
        GameObject tileCenterRight = level.getTileTypeAt(playerRightX, playerCenterY);
        GameObject tileCenterTop = level.getTileTypeAt(playerCenterX, playerTopY);
        GameObject tileCenterBottom = level.getTileTypeAt(playerCenterX, playerBottomY);
        GameObject tileCenterTop2 = level.getTileTypeAt(playerCenterX, playerTopY - Constants.TILE_SIZE);
        GameObject tileTopRight2 = level.getTileTypeAt(playerRightX, playerTopY - Constants.TILE_SIZE);

        List<GameObject> tileList = new ArrayList<>();
        tileList.add(tileBottomLeft);
        tileList.add(tileBottomRight);
        tileList.add(tileTopLeft);
        tileList.add(tileTopRight);
        tileList.add(tileCenterLeft);
        tileList.add(tileCenterRight);
        tileList.add(tileCenterTop);
        tileList.add(tileCenterBottom);
        tileList.add(tileCenterTop2);
        tileList.add(tileTopRight2);

        return tileList;
    }

    /**
     * Handles collision detection for the player.
     *
     * @param level the current level
     */
    public void handleCollide(Level level, Game game, Pane pane) {
        List<GameObject> tileList = getCollisionTiles(level);
        handleCommonCollide(level, game, pane, tileList);
    }

    /**
     * Sets whether the player is on the ground.
     *
     * @param isOnGround true if the player is on the ground, false otherwise
     */
    public void setOnGround(boolean isOnGround) {
        this.isOnGround = isOnGround;
    }

    /**
     * Gets the jump strength of the player.
     *
     * @return the jump strength
     */
    public double getJumpStrength() {
        return jumpStrength;
    }

    /**
     * Checks if the player is on the ground.
     *
     * @return true if the player is on the ground, false otherwise
     */
    public boolean isOnGround() {
        return isOnGround;
    }

    /**
     * Releases the action of the player.
     */
    public void releaseAction() {
        isSpacePressed = false;
        if (isInvertedGravity) {
            velocityY = -gravity;
        } else {
            velocityY = gravity;
        }
    }
}