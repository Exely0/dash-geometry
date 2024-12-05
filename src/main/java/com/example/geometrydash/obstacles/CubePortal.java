package com.example.geometrydash.obstacles;

import java.util.Objects;

import com.example.geometrydash.constants.Constants;
import com.example.geometrydash.player.NormalPlayer;

import javafx.scene.image.Image;

public class CubePortal extends AbstractTransformationPortal {

    public CubePortal(double x, double y) {
        super(new Image(Objects
                .requireNonNull(Block.class.getResource("/assets/portals/cube_portal.png")).toExternalForm(),
                Constants.TILE_SIZE, 2 * Constants.TILE_SIZE, false, false), x, y, NormalPlayer.class);
    }

}
