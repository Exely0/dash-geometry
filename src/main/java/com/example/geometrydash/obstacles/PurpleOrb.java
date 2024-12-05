package com.example.geometrydash.obstacles;

import java.util.Objects;

import com.example.geometrydash.constants.Constants;

import javafx.scene.image.Image;

public class PurpleOrb extends AbstractOrb {
    public PurpleOrb(double x, double y) {
        super(new Image(Objects.requireNonNull(
                YellowOrb.class.getResource("/assets/orb/purple_orb.png")).toExternalForm(),
                Constants.TILE_SIZE, Constants.TILE_SIZE, false, false), x, y, 0.75);
    }
}
