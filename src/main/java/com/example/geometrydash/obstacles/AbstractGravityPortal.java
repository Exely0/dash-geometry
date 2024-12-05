package com.example.geometrydash.obstacles;

import com.example.geometrydash.game.Game;
import com.example.geometrydash.level.Level;
import com.example.geometrydash.player.*;
import com.example.geometrydash.visual_effects.Effects;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public abstract class AbstractGravityPortal extends AbstractPortal {
    private boolean invertedGravity;

    protected AbstractGravityPortal(Image image, double x, double y, boolean invertedGravity) {
        super(image, x, y);
        this.invertedGravity = invertedGravity;
        this.used = false;
    }

    public void triggerEffect(AbstractPlayer player, Level level, Game game, Pane pane) {
        if (!used) {
            player.updateGravity(this.invertedGravity);
            Effects.applyFlash(pane);
            Effects.applyScreenShake(pane);
            this.used = true;
        }

    }
}
