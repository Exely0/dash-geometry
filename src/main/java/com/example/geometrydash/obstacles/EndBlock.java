package com.example.geometrydash.obstacles;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import com.example.geometrydash.constants.Constants;
import com.example.geometrydash.enums.*;
import com.example.geometrydash.game.Game;
import com.example.geometrydash.hitbox.Hitbox;
import com.example.geometrydash.player.AbstractPlayer;
import com.example.geometrydash.level.*;

public class EndBlock extends GameObject {

    public EndBlock(double x, double y) {
        super(null, x, y);
        this.hitbox = new Hitbox(HitboxType.BLUE, createHitbox(0, 0), null);

    }

    public Rectangle createHitbox(double x, double y) {
        return new Rectangle(0 + x, 0 + y, Constants.TILE_SIZE, Constants.TILE_SIZE);
    }

    public void triggerEffect(AbstractPlayer player, Level level, Game game, Pane pane) {
    }

}
