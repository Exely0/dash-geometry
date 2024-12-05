package com.example.geometrydash;

import com.example.geometrydash.level.Level;
import com.example.geometrydash.player.NormalPlayer;
import com.example.geometrydash.game.Game;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainApplication extends Application {

    private List<Level> levels;
    private int currentIndex = 0;
    private Label levelInfo;

    @Override
    public void start(Stage primaryStage) {
        Pane pane = new Pane();
        Scene scene = new Scene(pane, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

        levels = getLevels();
        if (levels.isEmpty()) {
            System.out.println("No levels found.");
            return;
        }

        levelInfo = new Label();
        updateLevelInfo();

        Button prevButton = new Button("Previous");
        prevButton.setOnAction(e -> showPreviousLevel());

        Button nextButton = new Button("Next");
        nextButton.setOnAction(e -> showNextLevel());

        Button selectButton = new Button("Select");
        selectButton.setOnAction(e -> startGame(primaryStage));

        HBox controls = new HBox(10, prevButton, nextButton, selectButton);
        controls.setAlignment(Pos.CENTER);
        controls.getStyleClass().add("hbox");

        VBox layout = new VBox(20, levelInfo, controls);
        layout.setAlignment(Pos.CENTER);
        layout.getStyleClass().add("vbox");

        layout.layoutXProperty().bind(pane.widthProperty().subtract(layout.widthProperty()).divide(2));
        layout.layoutYProperty().bind(pane.heightProperty().subtract(layout.heightProperty()).divide(2));

        pane.getChildren().add(layout);

        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                Platform.exit();
            }
        });

        primaryStage.setScene(scene);
        primaryStage.setTitle("Level Selection");
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }

    private List<Level> getLevels() {
        List<Level> levels = new ArrayList<>();
        URL resource = getClass().getResource("/assets/");
        if (resource != null) {
            File directory = new File(resource.getFile());
            File[] files = directory.listFiles((dir, name) -> name.startsWith("level_") && name.endsWith(".csv"));
            if (files != null) {
                Pattern pattern = Pattern.compile("level_([^_]+(?: [^_]+)*)_([^_]+)_(\\d+)\\.csv");
                for (File file : files) {
                    Matcher matcher = pattern.matcher(file.getName());
                    if (matcher.matches()) {
                        String name = matcher.group(1);
                        String difficulty = matcher.group(2);
                        int number = Integer.parseInt(matcher.group(3));
                        levels.add(new Level(20, 301, "/assets/" + file.getName(), "assets/musics/take_my_hand.mp3",
                                name, difficulty, number));
                    } else {
                        System.out.println("Invalid file name format: " + file.getName());
                    }
                }
                levels.sort(Comparator.comparingInt(Level::getNumber));
            }
        }
        return levels;
    }

    private void updateLevelInfo() {
        if (!levels.isEmpty()) {
            Level level = levels.get(currentIndex);
            String difficulty = level.getDifficulty();
            String capitalizedDifficulty = difficulty.substring(0, 1).toUpperCase()
                    + difficulty.substring(1).toLowerCase();
            levelInfo.setText("Name: " + level.getName() + "\nDifficulty: " + capitalizedDifficulty);
        }
    }

    private void showPreviousLevel() {
        if (currentIndex > 0) {
            currentIndex--;
            updateLevelInfo();
        }
    }

    private void showNextLevel() {
        if (currentIndex < levels.size() - 1) {
            currentIndex++;
            updateLevelInfo();
        }
    }

    private void startGame(Stage primaryStage) {
        Level selectedLevel = levels.get(currentIndex);
        if (selectedLevel == null) {
            System.out.println("Selected level is null.");
            return;
        }

        NormalPlayer player = new NormalPlayer(400, 100, false);

        Pane gamePane = new Pane();

        Game game = new Game(selectedLevel, player, gamePane, false);
        Scene gameScene = new Scene(gamePane, 1500, 1200);
        primaryStage.setScene(gameScene);
        primaryStage.setFullScreen(true);
        game.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}