package com.example.geometrydash.obstacles;

import com.example.geometrydash.Utils;
import com.example.geometrydash.constants.Constants;
import com.example.geometrydash.enums.HitboxType;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.SnapshotParameters;
import javafx.scene.paint.Color;
import java.util.Objects;

import com.example.geometrydash.level.Level;
import com.example.geometrydash.game.Game;
import com.example.geometrydash.hitbox.Hitbox;
import com.example.geometrydash.player.AbstractPlayer;

public class SpikeReversed extends Spike {

    public SpikeReversed(double x, double y) {
        super(x, y);
        this.image = Utils.rotateImage180(super.image);
    }

    @Override
    public Rectangle createHitbox(double x, double y) {
        return new Rectangle((Constants.TILE_SIZE * 0.4) + x, y,
                Constants.TILE_SIZE * 0.2, Constants.TILE_SIZE * 0.8);
    }
}
