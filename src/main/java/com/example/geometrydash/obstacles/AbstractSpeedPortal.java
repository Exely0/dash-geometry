package com.example.geometrydash.obstacles;

import com.example.geometrydash.level.*;
import com.example.geometrydash.player.AbstractPlayer;
import com.example.geometrydash.visual_effects.Effects;
import com.example.geometrydash.constants.Constants;
import com.example.geometrydash.game.Game;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class AbstractSpeedPortal extends AbstractPortal {
    private double speedModifier;

    protected AbstractSpeedPortal(Image image, double x, double y, double speedModifier) {
        super(image, x, y);
        this.speedModifier = speedModifier;
        this.used = false;
    }

    public void triggerEffect(AbstractPlayer player, Level level, Game game, Pane pane) {
        if (!used) {
            level.setVelocityX(Constants.TILE_SIZE * Constants.LEVEL_SPEED * speedModifier);
            Effects.applyFlash(pane);
            Effects.applyScreenShake(pane);
            this.used = true;
        }

    }
}
