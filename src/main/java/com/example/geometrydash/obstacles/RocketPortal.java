package com.example.geometrydash.obstacles;

import com.example.geometrydash.constants.Constants;
import com.example.geometrydash.player.RocketPlayer;

import javafx.scene.image.Image;

import java.util.Objects;

public class RocketPortal extends AbstractTransformationPortal {

    public RocketPortal(double x, double y) {
        super(
                new Image(
                        Objects.requireNonNull(
                                RocketPortal.class.getResource("/assets/portals/rocket_portal.png")).toExternalForm(),
                        Constants.TILE_SIZE,
                        2 * Constants.TILE_SIZE,
                        false,
                        false),
                x, y,
                RocketPlayer.class);
    }
}
