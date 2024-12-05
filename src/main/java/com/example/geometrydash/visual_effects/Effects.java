package com.example.geometrydash.visual_effects;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import javafx.scene.image.ImageView;

import com.example.geometrydash.constants.*;

public class Effects {

    public static void applyFlash(Pane pane) {
        Rectangle flashOverlay = new Rectangle(pane.getWidth(), pane.getHeight(), Color.WHITE);
        flashOverlay.setOpacity(0);
        pane.getChildren().add(flashOverlay);

        FadeTransition flashTransition = new FadeTransition(Duration.seconds(0.3), flashOverlay);
        flashTransition.setFromValue(1.0);
        flashTransition.setToValue(0.0);
        flashTransition.setOnFinished(event -> pane.getChildren().remove(flashOverlay));

        flashTransition.play();
    }

    public static void applyScreenShake(Pane pane) {
        double originalX = pane.getLayoutX();
        double originalY = pane.getLayoutY();

        Timeline shakeTimeline = new Timeline(
                new KeyFrame(Duration.millis(50), e -> pane.setLayoutX(originalX - 5)),
                new KeyFrame(Duration.millis(100), e -> pane.setLayoutX(originalX + 5)),
                new KeyFrame(Duration.millis(150), e -> pane.setLayoutY(originalY - 5)),
                new KeyFrame(Duration.millis(200), e -> pane.setLayoutY(originalY + 5)),
                new KeyFrame(Duration.millis(250), e -> {
                    pane.setLayoutX(originalX);
                    pane.setLayoutY(originalY);
                }));

        shakeTimeline.play();
    }

    public static void applyTrail(Pane pane, double x, double y) {
        Rectangle trail = new Rectangle(x, y + Constants.TILE_SIZE / 8,
                Constants.TILE_SIZE * 0.75, Constants.TILE_SIZE * 0.75);
        trail.setFill(Color.PURPLE);
        trail.setOpacity(1.0);
        trail.setY(y + Constants.TILE_SIZE / 8);

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0), e -> {
                    trail.setOpacity(1.0);
                    trail.setWidth(Constants.TILE_SIZE * 0.75);
                    trail.setHeight(Constants.TILE_SIZE * 0.75);
                    trail.setX(x - Constants.TILE_SIZE);
                    trail.setY(y + Constants.TILE_SIZE / 8);

                }),

                new KeyFrame(Duration.seconds(1), new KeyValue(trail.opacityProperty(), 0.0),
                        new KeyValue(trail.widthProperty(), 0),
                        new KeyValue(trail.heightProperty(), 0),
                        new KeyValue(trail.xProperty(), x - 4 * Constants.TILE_SIZE),
                        new KeyValue(trail.yProperty(), y + Constants.TILE_SIZE / 2)));

        timeline.setCycleCount(1);
        timeline.setOnFinished(e -> pane.getChildren().remove(trail));

        timeline.play();
        pane.getChildren().add(trail);
    }

    public static void applyTrail2(Pane pane, double x, double y) {
        double radius = Constants.TILE_SIZE / 6;

        Circle circleTrail = new Circle(x, y + Constants.TILE_SIZE / 2, radius);
        circleTrail.setFill(Color.PURPLE);
        circleTrail.setOpacity(1.0);

        circleTrail.setCenterX(x + Constants.TILE_SIZE / 2);
        circleTrail.setCenterY(y + Constants.TILE_SIZE / 2);

        Timeline timelineCircle = new Timeline(
                new KeyFrame(Duration.seconds(0), e -> {
                    circleTrail.setOpacity(1.0);
                    circleTrail.setRadius(radius);
                    circleTrail.setCenterX(x + Constants.TILE_SIZE / 2);
                    circleTrail.setCenterY(y + Constants.TILE_SIZE / 2);
                }),

                new KeyFrame(Duration.seconds(1), new KeyValue(circleTrail.opacityProperty(), 0.0),
                        new KeyValue(circleTrail.radiusProperty(), 0),
                        new KeyValue(circleTrail.centerXProperty(), x - 4 * Constants.TILE_SIZE),
                        new KeyValue(circleTrail.centerYProperty(), y + Constants.TILE_SIZE / 2)));

        timelineCircle.setCycleCount(1);
        timelineCircle.setOnFinished(e -> pane.getChildren().remove(circleTrail));

        timelineCircle.play();
        pane.getChildren().add(circleTrail);
    }

    public static void applyGrowingHaloEffect(Pane pane, double x, double y) {
        Circle halo = new Circle(x, y, 0);
        halo.setFill(Color.WHITE);
        halo.setOpacity(1);
        pane.getChildren().add(halo);

        double displacement = -(8.3 / 2) * Constants.TILE_SIZE;

        Timeline haloTimeline = new Timeline(
                new KeyFrame(Duration.seconds(0),
                        new KeyValue(halo.radiusProperty(), 0),
                        new KeyValue(halo.centerXProperty(), x + Constants.TILE_SIZE / 2),
                        new KeyValue(halo.opacityProperty(), 1)),

                new KeyFrame(Duration.seconds(0.5),
                        new KeyValue(halo.radiusProperty(), 50),
                        new KeyValue(halo.centerXProperty(), x + displacement + Constants.TILE_SIZE / 2),
                        new KeyValue(halo.opacityProperty(), 0.4)));

        haloTimeline.setOnFinished(e -> pane.getChildren().remove(halo));
        haloTimeline.play();
    }

}
