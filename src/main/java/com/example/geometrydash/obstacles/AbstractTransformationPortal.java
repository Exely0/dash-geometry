package com.example.geometrydash.obstacles;

import com.example.geometrydash.level.Level;
import com.example.geometrydash.player.AbstractPlayer;
import com.example.geometrydash.visual_effects.*;
import com.example.geometrydash.game.Game;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public abstract class AbstractTransformationPortal extends AbstractPortal {

    private final Class<? extends AbstractPlayer> playerType;

    protected AbstractTransformationPortal(Image image, double x, double y,
            Class<? extends AbstractPlayer> playerType) {
        super(image, x, y);
        this.playerType = playerType;
    }

    public void triggerEffect(AbstractPlayer player, Level level, Game game, Pane pane) {
        if (!used) {
            game.handleModifyPlayerType(playerType);
            Effects.applyFlash(pane);
            Effects.applyScreenShake(pane);
            this.used = true;
        }
    }
}
