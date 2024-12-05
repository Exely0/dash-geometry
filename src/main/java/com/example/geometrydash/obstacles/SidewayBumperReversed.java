package com.example.geometrydash.obstacles;

import com.example.geometrydash.Utils;
import com.example.geometrydash.constants.Constants;
import com.example.geometrydash.game.Game;
import com.example.geometrydash.player.AbstractPlayer;
import com.example.geometrydash.level.*;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class SidewayBumperReversed extends SidewayBumper {
    public SidewayBumperReversed(double x, double y) {
        super(x, y);
        this.image = Utils.rotateImage90(createPaddedImage("/assets/bumpers/yellow_bumper.png"));
    }

    @Override
    public Rectangle createHitbox(double x, double y) {
        return new Rectangle(x, y + Constants.TILE_SIZE * 0.2, Constants.TILE_SIZE * 0.2, Constants.TILE_SIZE * 0.8);
    }

    // @Override
    // public void triggerEffect(AbstractPlayer player, Level level, Game game, Pane
    // pane) {
    // if (!used) {
    // level.setVelocityX(level.getTargetVelocityX());
    // }
    // }

}
