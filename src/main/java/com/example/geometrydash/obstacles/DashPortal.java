package com.example.geometrydash.obstacles;

import java.util.Objects;

import com.example.geometrydash.constants.Constants;
import com.example.geometrydash.player.DashPlayer;

import javafx.scene.image.Image;

public class DashPortal extends AbstractTransformationPortal {

    public DashPortal(double x, double y) {
        super(new Image(Objects
                .requireNonNull(Block.class.getResource("/assets/portals/dash_portal.png")).toExternalForm(),
                Constants.TILE_SIZE, 2 * Constants.TILE_SIZE, false, false), x, y, DashPlayer.class);
    }
}
