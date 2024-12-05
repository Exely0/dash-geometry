package com.example.geometrydash.obstacles;

import java.util.Objects;

import com.example.geometrydash.constants.Constants;

import javafx.scene.image.Image;

public class YellowOrb extends AbstractOrb {
    public YellowOrb(double x, double y) {
        super(new Image(Objects.requireNonNull(
                YellowOrb.class.getResource("/assets/orb/yellow_orb.png")).toExternalForm(),
                Constants.TILE_SIZE, Constants.TILE_SIZE, false, false), x, y, 1.2);
    }
}
