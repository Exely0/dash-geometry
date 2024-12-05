package com.example.geometrydash.player;

import com.example.geometrydash.constants.Constants;
import com.example.geometrydash.enums.PlayerType;
import com.example.geometrydash.level.Level;
import com.example.geometrydash.game.*;
import javafx.scene.layout.Pane;

/**
 * Class representing a DashPlayer in the Geometry Dash game.
 */
public class DashPlayer extends AbstractPlayer {
    private static final double VELOCITY_Y = Constants.TILE_SIZE * 0.15;

    /**
     * Constructs a DashPlayer with default parameters.
     */
    public DashPlayer(double x, double y, boolean invertedGravity) {
        super(x, y, 0, 0, 0, "/assets/player/dash.png", invertedGravity);
        this.velocityY = VELOCITY_Y;
    }

    /**
     * Defines the action to be taken by the DashPlayer.
     */
    @Override
    public void action() {
        this.velocityY = -VELOCITY_Y;
    }

    /**
     * Releases the action of the DashPlayer.
     */
    @Override
    public void releaseAction() {
        this.velocityY = VELOCITY_Y;
    }

    /**
     * Updates the position of the DashPlayer.
     *
     * @param level the current level
     */
    @Override
    public void updatePosition(Level level, Game game, Pane pane) {
        handleCollide(level, game, pane);
        this.y += this.velocityY;
        if (this.velocityY < 0) {
            this.sprite.setRotate(-45);
        } else {
            this.sprite.setRotate(45);
        }
        applyTrail(x, y, pane);
        setPosition(y);
    }
}