package com.example.geometrydash.obstacles;

import java.util.Objects;

import com.example.geometrydash.constants.Constants;

import javafx.scene.image.Image;

public class Boost2SpeedPortal extends AbstractSpeedPortal {
    public Boost2SpeedPortal(double x, double y) {
        super(new Image(Objects
                .requireNonNull(Block.class.getResource("/assets/portals/speed_boost2_portal.png"))
                .toExternalForm(),
                1.5 * Constants.TILE_SIZE, 2 * Constants.TILE_SIZE, false, false), x, y,
                Constants.LEVEL_SPEED_MULTIPLIER_BOOST_2);
    }
}