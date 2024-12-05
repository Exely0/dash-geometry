package com.example.geometrydash.obstacles;

import com.example.geometrydash.constants.Constants;
import com.example.geometrydash.enums.HitboxType;
import com.example.geometrydash.hitbox.Hitbox;
import com.example.geometrydash.player.*;
import com.example.geometrydash.visual_effects.Effects;
import com.example.geometrydash.game.*;
import com.example.geometrydash.level.*;

import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.Pane;

public class AbstractOrb extends GameObject {
    private double jumpStrength;

    protected AbstractOrb(Image image, double x, double y, double jumpStrength) {
        super(image, x, y);
        this.hitbox = new Hitbox(HitboxType.BLUE, createHitbox(0, 0), null);
        this.jumpStrength = jumpStrength;
    }

    public Rectangle createHitbox(double x, double y) {
        return new Rectangle(x, y, Constants.TILE_SIZE, Constants.TILE_SIZE);
    }

    public void triggerEffect(AbstractPlayer player, Level level, Game game, Pane pane) {
        if (!isUsed()) {
            if (player.isSpacePressed() && !player.isActionTriggered()) {
                player.setVelocityY(player.getJumpStrength() * this.jumpStrength);
                Effects.applyGrowingHaloEffect(pane, x + level.getX(), y);
                setUsed(true);
            }
        }
    }
}
