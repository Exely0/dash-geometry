package com.example.geometrydash.obstacles;

import java.util.Objects;

import com.example.geometrydash.constants.Constants;
import com.example.geometrydash.enums.HitboxType;
import com.example.geometrydash.game.Game;
import com.example.geometrydash.hitbox.Hitbox;
import com.example.geometrydash.level.Level;
import com.example.geometrydash.player.AbstractPlayer;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GravityPad extends GameObject {
    public GravityPad(double x, double y) {
        super(createPaddedImage(), x, y);
        this.hitbox = new Hitbox(HitboxType.RED, createHitbox(0, 0), null);
        this.used = false;
    }

    @Override
    public Rectangle createHitbox(double x, double y) {
        return new Rectangle(x, y + (Constants.TILE_SIZE * 0.8), Constants.TILE_SIZE, Constants.TILE_SIZE);
    }

    private static Image createPaddedImage() {
        int tileSize = Constants.TILE_SIZE;
        int bumperHeight = tileSize / 5;

        Image originalBumper = new Image(Objects.requireNonNull(
                MediumBumper.class.getResource("/assets/bumpers/gravity_pad.png")).toExternalForm(),
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

    public void triggerEffect(AbstractPlayer player, Level level, Game game, Pane pane) {
        if (!used) {
            player.setVelocityY(player.getJumpStrength() / 2);
            player.setY(player.getY() + player.getVelocityY());
            player.updateGravity(!player.isInvertedGravity());
            this.used = true;
        }
    }
}
