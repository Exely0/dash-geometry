package com.example.geometrydash.obstacles;

import java.util.Objects;

import com.example.geometrydash.constants.Constants;
import com.example.geometrydash.enums.HitboxType;
import com.example.geometrydash.game.Game;
import com.example.geometrydash.hitbox.Hitbox;
import com.example.geometrydash.player.AbstractPlayer;
import com.example.geometrydash.visual_effects.Effects;
import com.example.geometrydash.level.*;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class BlackOrb extends GameObject {
    public BlackOrb(double x, double y) {
        super(new Image(Objects.requireNonNull(
                YellowOrb.class.getResource("/assets/orb/black_orb.png")).toExternalForm(),
                Constants.TILE_SIZE, Constants.TILE_SIZE, false, false), x, y);
        this.hitbox = new Hitbox(HitboxType.BLUE, createHitbox(0, 0), null);
    }

    public Rectangle createHitbox(double x, double y) {
        return new Rectangle(0 + x, 0 + y, Constants.TILE_SIZE, Constants.TILE_SIZE);
    }

    public void triggerEffect(AbstractPlayer player, Level level, Game game, Pane pane) {
        if (!isUsed()) {
            if (player.isSpacePressed() && !player.isActionTriggered()) {
                player.setMegaSpeedOn(true);
                Effects.applyGrowingHaloEffect(pane, x + level.getX(), y);
                this.used = true;
            }
        }
    }
}
