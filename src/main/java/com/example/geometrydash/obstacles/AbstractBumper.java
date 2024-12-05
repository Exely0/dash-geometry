package com.example.geometrydash.obstacles;

import com.example.geometrydash.constants.Constants;
import com.example.geometrydash.enums.HitboxType;
import com.example.geometrydash.hitbox.Hitbox;
import com.example.geometrydash.player.AbstractPlayer;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import com.example.geometrydash.visual_effects.*;

import java.util.Objects;

import com.example.geometrydash.level.*;
import com.example.geometrydash.game.*;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

public abstract class AbstractBumper extends GameObject {

    protected double bumpStrength;

    protected AbstractBumper(Image image, double x, double y, double bumpStrength) {
        super(image, x, y);
        this.bumpStrength = bumpStrength;
        this.hitbox = new Hitbox(HitboxType.RED, createHitbox(0, 0), createHitboxImage());
        this.used = false;
    }

    @Override
    public Rectangle createHitbox(double x, double y) {
        return new Rectangle(x, y + (Constants.TILE_SIZE * 0.8), Constants.TILE_SIZE, Constants.TILE_SIZE);
    }

    public double getBumpStrength() {
        return bumpStrength;
    }

    public void triggerEffect(AbstractPlayer player, Level level, Game game, Pane pane) {
        if (!used) {
            player.setOnGround(false);
            player.setVelocityY(bumpStrength * player.getJumpStrength());
            player.setY(player.getY() + player.getVelocityY());
            Effects.applyGrowingHaloEffect(pane, x + level.getX(), y);
            this.used = true;
        }
    }

    protected static Image createPaddedImage(String imageLink) {
        int tileSize = Constants.TILE_SIZE;
        int bumperHeight = tileSize / 5;

        Image originalBumper = new Image(Objects.requireNonNull(
                MediumBumper.class.getResource(imageLink)).toExternalForm(),
                tileSize, bumperHeight, false, false);

        WritableImage paddedImage = new WritableImage(tileSize, tileSize);
        PixelWriter pixelWriter = paddedImage.getPixelWriter();

        int yOffset = tileSize - bumperHeight;

        PixelReader pixelReader = originalBumper.getPixelReader();
        for (int y = 0; y < bumperHeight; y++) {
            for (int x = 0; x < tileSize; x++) {
                Color color = pixelReader.getColor(x, y);
                pixelWriter.setColor(x, y + yOffset, color);
            }
        }

        return paddedImage;
    }
}
