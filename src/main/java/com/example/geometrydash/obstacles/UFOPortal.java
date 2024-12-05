package com.example.geometrydash.obstacles;

import java.util.Objects;

import com.example.geometrydash.constants.Constants;
import com.example.geometrydash.player.SpaceshipPlayer;

import javafx.scene.image.Image;

public class UFOPortal extends AbstractTransformationPortal {

        public UFOPortal(double x, double y) {
                super(new Image(Objects
                                .requireNonNull(Block.class.getResource("/assets/portals/ufo_portal.png"))
                                .toExternalForm(),
                                Constants.TILE_SIZE, 2 * Constants.TILE_SIZE, false, false), x, y,
                                SpaceshipPlayer.class);
        }
}
