package com.example.geometrydash.obstacles;

import java.util.Objects;
import com.example.geometrydash.constants.Constants;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

public class SmallBumper extends AbstractBumper {

    public SmallBumper(double x, double y) {
        super(createPaddedImage("/assets/bumpers/violet_bumper.png"), x, y, 0.75);
    }

}
