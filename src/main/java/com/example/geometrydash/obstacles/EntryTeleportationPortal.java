package com.example.geometrydash.obstacles;

import java.util.Objects;
import com.example.geometrydash.constants.Constants;
import com.example.geometrydash.game.Game;
import com.example.geometrydash.player.AbstractPlayer;
import com.example.geometrydash.visual_effects.Effects;
import com.example.geometrydash.level.Level;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class EntryTeleportationPortal extends AbstractPortal {
    private boolean used;

    public EntryTeleportationPortal(double x, double y) {
        super(new Image(Objects.requireNonNull(
                Block.class.getResource("/assets/portals/entry_teleportation_portal.png")).toExternalForm(),
                Constants.TILE_SIZE, 2 * Constants.TILE_SIZE, false, false), x, y);
        this.used = false;
    }

    public void triggerEffect(AbstractPlayer player, Level level, Game game, Pane pane) {
        if (!used) {
            this.used = true;
            int column = (int) (this.x / Constants.TILE_SIZE);

            for (int i = 0; i < level.getHeight(); i++) {
                if (level.getMap()[i][column] instanceof ExitTeleportationPortal) {
                    player.setY(level.getMap()[i][column].getY());
                    Effects.applyFlash(pane);
                    Effects.applyScreenShake(pane);
                    break;
                }
            }

        }
    }
}
