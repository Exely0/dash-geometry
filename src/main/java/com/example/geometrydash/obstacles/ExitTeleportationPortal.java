package com.example.geometrydash.obstacles;

import java.util.Objects;

import com.example.geometrydash.constants.Constants;
import com.example.geometrydash.game.Game;
import com.example.geometrydash.level.Level;
import com.example.geometrydash.player.AbstractPlayer;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class ExitTeleportationPortal extends AbstractPortal {
    public ExitTeleportationPortal(double x, double y) {
        super(new Image(Objects
                .requireNonNull(Block.class.getResource("/assets/portals/exit_teleportation_portal.png"))
                .toExternalForm(),
                Constants.TILE_SIZE, 2 * Constants.TILE_SIZE, false, false), x, y);
    }

    public void triggerEffect(AbstractPlayer player, Level level, Game game, Pane pane) {
    }
}
