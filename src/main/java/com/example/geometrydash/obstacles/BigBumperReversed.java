
package com.example.geometrydash.obstacles;

import com.example.geometrydash.Utils;
import com.example.geometrydash.constants.Constants;

import javafx.scene.shape.Rectangle;

public class BigBumperReversed extends BigBumper {

    public BigBumperReversed(double x, double y) {
        super(x, y);
        this.image = Utils.rotateImage180(createPaddedImage("/assets/bumpers/red_bumper.png"));
    }

    @Override
    public Rectangle createHitbox(double x, double y) {
        return new Rectangle(x, y, Constants.TILE_SIZE, Constants.TILE_SIZE * 0.2);
    }
}