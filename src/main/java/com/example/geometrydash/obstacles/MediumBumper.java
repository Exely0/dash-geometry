package com.example.geometrydash.obstacles;

import java.util.Objects;
import com.example.geometrydash.constants.Constants;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.PixelReader;

public class MediumBumper extends AbstractBumper {

    public MediumBumper(double x, double y) {
        super(createPaddedImage("/assets/bumpers/yellow_bumper.png"), x, y, 1.2);
    }

}
