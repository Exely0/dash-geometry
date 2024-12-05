package com.example.geometrydash.hitbox;

import com.example.geometrydash.constants.Constants;
import com.example.geometrydash.enums.*;

import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.Image;

public class Hitbox {

    private final HitboxType type;
    private final Rectangle shape;
    private final Image hitboxImage;

    public Hitbox(HitboxType type, Rectangle shape, Image hitboxImage) {
        this.type = type;
        this.shape = shape;
        this.hitboxImage = hitboxImage;
    }

    public Image createHitboxImage() {

        if (hitboxImage != null) {
            return hitboxImage;
        }

        WritableImage hitboxImage = new WritableImage(Constants.TILE_SIZE, (int) shape.getHeight());
        PixelWriter pixelWriter = hitboxImage.getPixelWriter();

        Color color = (type == HitboxType.RED) ? Color.RED : Color.BLUE;

        for (int y = 0; y < (int) shape.getHeight(); y++) {
            for (int x = 0; x < Constants.TILE_SIZE; x++) {
                if (shape.contains(x, y)) {
                    pixelWriter.setColor(x, y, color);
                } else {
                    pixelWriter.setColor(x, y, Color.TRANSPARENT);
                }
            }
        }

        return hitboxImage;
    }

    public Rectangle getShape() {
        return shape;
    }
}
