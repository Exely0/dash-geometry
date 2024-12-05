package com.example.geometrydash.obstacles;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import java.util.Objects;

import com.example.geometrydash.constants.Constants;
import com.example.geometrydash.enums.*;
import com.example.geometrydash.game.Game;
import com.example.geometrydash.hitbox.Hitbox;
import com.example.geometrydash.player.AbstractPlayer;
import com.example.geometrydash.level.*;

public class Block extends GameObject {

    public Block(double x, double y) {
        super(
                new Image(Objects
                        .requireNonNull(Block.class.getResource("/assets/floor.png")).toExternalForm(),
                        Constants.TILE_SIZE, Constants.TILE_SIZE, false, false),
                x, y);
        this.hitbox = new Hitbox(HitboxType.BLUE, createHitbox(0, 0), null);

    }

    public Rectangle createHitbox(double x, double y) {
        return new Rectangle(0 + x, 0 + y, Constants.TILE_SIZE, Constants.TILE_SIZE);
    }

    public void triggerEffect(AbstractPlayer player, Level level, Game game, Pane pane) {
    }

}
