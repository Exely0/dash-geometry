package com.example.geometrydash.obstacles;

import java.util.Objects;

import com.example.geometrydash.constants.Constants;

import javafx.scene.image.Image;

public class SlowSpeedPortal extends AbstractSpeedPortal {
    public SlowSpeedPortal(double x, double y) {
        super(new Image(Objects
                .requireNonNull(Block.class.getResource("/assets/portals/speed_slow_portal.png"))
                .toExternalForm(),
                Constants.TILE_SIZE, 2 * Constants.TILE_SIZE, false, false), x, y,
                Constants.LEVEL_SPEED_MULTIPLIER_SLOW);
    }
}
