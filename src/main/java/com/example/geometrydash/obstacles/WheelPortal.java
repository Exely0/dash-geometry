package com.example.geometrydash.obstacles;

import com.example.geometrydash.constants.Constants;
import com.example.geometrydash.player.WheelPlayer;

import javafx.scene.image.Image;

import java.util.Objects;

public class WheelPortal extends AbstractTransformationPortal {

    public WheelPortal(double x, double y) {
        super(
                new Image(
                        Objects.requireNonNull(
                                RocketPortal.class.getResource("/assets/portals/wheel_portal.png")).toExternalForm(),
                        Constants.TILE_SIZE,
                        2 * Constants.TILE_SIZE,
                        false,
                        false),
                x, y,
                WheelPlayer.class);
    }
}
