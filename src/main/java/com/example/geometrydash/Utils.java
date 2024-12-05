package com.example.geometrydash;

import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class Utils {

    public static Image rotateImage180(Image image) {
        ImageView imageView = new ImageView(image);
        imageView.setRotate(180);
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        return imageView.snapshot(params, null);
    }

    public static Image rotateImage90(Image image) {
        ImageView imageView = new ImageView(image);
        imageView.setRotate(90);
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        return imageView.snapshot(params, null);
    }

    public static Image rotateImage270(Image image) {
        ImageView imageView = new ImageView(image);
        imageView.setRotate(270);
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        return imageView.snapshot(params, null);
    }

}
