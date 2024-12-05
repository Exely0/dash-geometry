package com.example.geometrydash.obstacles;

import com.example.geometrydash.constants.Constants;
import com.example.geometrydash.enums.HitboxType;
import com.example.geometrydash.hitbox.Hitbox;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Objects;

import com.example.geometrydash.level.Level;
import com.example.geometrydash.game.Game;
import com.example.geometrydash.player.AbstractPlayer;

public class Spike extends GameObject {

    public Spike(double x, double y) {
        super(
                new Image(Objects
                        .requireNonNull(Spike.class.getResource("/assets/spike.png")).toExternalForm(),
                        Constants.TILE_SIZE, Constants.TILE_SIZE, false, false),
                x, y);
        this.hitbox = new Hitbox(HitboxType.RED, createHitbox(x, y), createHitboxImage());

    }

    public Rectangle createHitbox(double x, double y) {
        return new Rectangle((Constants.TILE_SIZE * 0.4) + x, (Constants.TILE_SIZE *
                0.2) + y, Constants.TILE_SIZE * 0.2, Constants.TILE_SIZE * 0.8);
    }

    @Override
    public void triggerEffect(AbstractPlayer player, Level level, Game game, Pane pane) {
    }
}
