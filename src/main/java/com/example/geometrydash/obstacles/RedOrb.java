package com.example.geometrydash.obstacles;

import java.util.Objects;

import com.example.geometrydash.constants.Constants;

import javafx.scene.image.Image;

public class RedOrb extends AbstractOrb {
    public RedOrb(double x, double y) {
        super(new Image(Objects.requireNonNull(
                YellowOrb.class.getResource("/assets/orb/red_orb.png")).toExternalForm(),
                Constants.TILE_SIZE, Constants.TILE_SIZE, false, false), x, y, 2);
    }
}
