package com.example.geometrydash.obstacles;

import com.example.geometrydash.constants.Constants;
import com.example.geometrydash.enums.HitboxType;
import com.example.geometrydash.hitbox.Hitbox;

import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

public abstract class AbstractPortal extends GameObject {

    protected AbstractPortal(Image image, double x, double y) {
        super(image, x, y);
        this.hitbox = new Hitbox(HitboxType.BLUE, createHitbox(0, 0), null);
        this.used = false;

    }

    public Rectangle createHitbox(double x, double y) {
        Rectangle hitbox = new Rectangle(0 + x, 0 + y, Constants.TILE_SIZE, 2 * Constants.TILE_SIZE);
        return hitbox;
    }
}
