package com.example.geometrydash.obstacles;

import java.util.Objects;

import com.example.geometrydash.constants.Constants;

import javafx.scene.image.Image;

public class InvertedGravityPortal extends AbstractGravityPortal {

    public InvertedGravityPortal(double x, double y) {
        super(new Image(Objects
                .requireNonNull(Block.class.getResource("/assets/portals/inverted_gravity_portal.png"))
                .toExternalForm(),
                Constants.TILE_SIZE, 2 * Constants.TILE_SIZE, false, false), x, y, true);
    }
}
