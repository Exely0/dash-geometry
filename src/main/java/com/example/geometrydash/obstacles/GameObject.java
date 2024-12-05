package com.example.geometrydash.obstacles;

import com.example.geometrydash.hitbox.Hitbox;
import com.example.geometrydash.player.AbstractPlayer;
import com.example.geometrydash.constants.Constants;
import com.example.geometrydash.enums.HitboxType;
import com.example.geometrydash.game.*;
import com.example.geometrydash.level.*;

import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.shape.Rectangle;

import javafx.scene.layout.Pane;

public abstract class GameObject {
    protected Image image;
    protected Hitbox hitbox;

    protected double x;
    protected double y;

    protected boolean used;

    protected GameObject(Image image, double x, double y) {
        this.image = image;
        this.x = x;
        this.y = y;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public boolean isUsed() {
        return used;
    }

    public Image getImage() {
        return this.image;
    }

    public Hitbox getHitbox() {
        return this.hitbox;
    }

    public abstract Rectangle createHitbox(double x, double y);

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Image createHitboxImage() {
        WritableImage hitboxImage = new WritableImage(Constants.TILE_SIZE, Constants.TILE_SIZE);
        PixelWriter pixelWriter = hitboxImage.getPixelWriter();

        for (int y = 0; y < Constants.TILE_SIZE; y++) {
            for (int x = 0; x < Constants.TILE_SIZE; x++) {
                if (createHitbox(0, 0).contains(x, y)) {
                    pixelWriter.setColor(x, y, HitboxType.RED == HitboxType.RED ? javafx.scene.paint.Color.RED
                            : javafx.scene.paint.Color.TRANSPARENT);
                } else {
                    pixelWriter.setColor(x, y, javafx.scene.paint.Color.TRANSPARENT);
                }
            }
        }

        return hitboxImage;
    }

    public abstract void triggerEffect(AbstractPlayer player, Level level, Game game, Pane pane);
}
