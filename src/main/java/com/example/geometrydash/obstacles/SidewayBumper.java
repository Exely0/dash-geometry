package com.example.geometrydash.obstacles;

import com.example.geometrydash.Utils;
import com.example.geometrydash.constants.Constants;
import com.example.geometrydash.game.Game;
import com.example.geometrydash.player.AbstractPlayer;
import com.example.geometrydash.visual_effects.Effects;
import com.example.geometrydash.level.*;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class SidewayBumper extends AbstractBumper {
    public SidewayBumper(double x, double y) {
        super(Utils.rotateImage270(createPaddedImage("/assets/bumpers/yellow_bumper.png")), x, y, 0);
    }

    @Override
    public void triggerEffect(AbstractPlayer player, Level level, Game game, Pane pane) {
        if (!used) {
            level.setVelocityX(level.getVelocityX() * -1);
        }
    }

    @Override
    public Rectangle createHitbox(double x, double y) {
        return new Rectangle(x + Constants.TILE_SIZE * 0.8, y + Constants.TILE_SIZE * 0.2, Constants.TILE_SIZE * 0.2,
                Constants.TILE_SIZE * 0.8);
    }
}
