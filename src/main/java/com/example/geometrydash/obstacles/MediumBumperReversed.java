
package com.example.geometrydash.obstacles;

import com.example.geometrydash.Utils;
import com.example.geometrydash.constants.Constants;

import eu.hansolo.tilesfx.colors.Medium;
import javafx.scene.image.Image;

import javafx.scene.shape.Rectangle;

public class MediumBumperReversed extends MediumBumper {

    public MediumBumperReversed(double x, double y) {
        super(x, y);
        this.image = Utils.rotateImage180(createPaddedImage("/assets/bumpers/yellow_bumper.png"));
    }

    @Override
    public Rectangle createHitbox(double x, double y) {
        return new Rectangle(x, y, Constants.TILE_SIZE, Constants.TILE_SIZE * 0.2);
    }
}