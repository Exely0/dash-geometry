package com.example.geometrydash.obstacles;

import java.util.Objects;
import com.example.geometrydash.constants.Constants;
import com.example.geometrydash.enums.HitboxType;
import com.example.geometrydash.player.AbstractPlayer;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.PixelReader;

public class BigBumper extends AbstractBumper {

    public BigBumper(double x, double y) {
        super(createPaddedImage("/assets/bumpers/red_bumper.png"), x, y, 2);
    }
}
