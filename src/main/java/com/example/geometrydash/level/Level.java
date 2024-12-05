package com.example.geometrydash.level;

import com.example.geometrydash.constants.*;
import com.example.geometrydash.hitbox.Hitbox;
import com.example.geometrydash.obstacles.*;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.logging.Logger;

public class Level {
    private final String csvFilePath;
    private final int height;
    private final int width;
    private final String name;
    private final String difficulty;
    private final int number;
    private final GameObject[][] map;
    private double velocityX;
    private double x;
    private ImageView levelView;
    private ImageView hitboxLayerView;
    private String musicFilePath;

    public Level(int height, int width, String csvFilePath, String musicFilePath, String name, String difficulty,
            int number) {
        this.csvFilePath = csvFilePath;
        this.height = height;
        this.width = width;
        this.map = new GameObject[height][width];
        this.velocityX = Constants.TILE_SIZE * Constants.LEVEL_SPEED;
        this.musicFilePath = musicFilePath;
        this.name = name;
        this.difficulty = difficulty;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public int getNumber() {
        return number;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public double getVelocityX() {
        return velocityX;
    }

    public String getMusicFilePath() {
        return musicFilePath;
    }

    public double getX() {
        return x;
    }

    public int getHeight() {
        return height;
    }

    public void setVelocityX(double velocityX) {
        this.velocityX = velocityX;
    }

    public void loadMap() {
        try (InputStream is = getClass().getResourceAsStream(csvFilePath)) {
            assert is != null;
            try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {

                String line;
                int row = 0;

                while ((line = br.readLine()) != null && row < height) {
                    String[] cells = line.split(",");

                    for (int col = 0; col < cells.length && col < width; col++) {
                        try {
                            int tileType = Integer.parseInt(cells[col].trim());
                            if (tileType == 1) {
                                map[row][col] = new Block(col * Constants.TILE_SIZE, row * Constants.TILE_SIZE);
                            } else if (tileType == -1) {
                                map[row][col] = new EndBlock(col * Constants.TILE_SIZE, row * Constants.TILE_SIZE);
                            } else if (tileType == 2) {
                                map[row][col] = new Spike(col * Constants.TILE_SIZE, row * Constants.TILE_SIZE);
                            } else if (tileType == -2) {
                                map[row][col] = new SpikeReversed(col * Constants.TILE_SIZE, row * Constants.TILE_SIZE);
                            } else if (tileType == 31) {
                                map[row][col] = new SmallBumper(col * Constants.TILE_SIZE, row * Constants.TILE_SIZE);
                            } else if (tileType == -31) {
                                map[row][col] = new SmallBumperReversed(col * Constants.TILE_SIZE,
                                        row * Constants.TILE_SIZE);
                            } else if (tileType == 32) {
                                map[row][col] = new MediumBumper(col * Constants.TILE_SIZE, row * Constants.TILE_SIZE);
                            } else if (tileType == -32) {
                                map[row][col] = new MediumBumperReversed(col * Constants.TILE_SIZE,
                                        row * Constants.TILE_SIZE);
                            } else if (tileType == 33) {
                                map[row][col] = new BigBumper(col * Constants.TILE_SIZE, row * Constants.TILE_SIZE);
                            } else if (tileType == -33) {
                                map[row][col] = new BigBumperReversed(col * Constants.TILE_SIZE,
                                        row * Constants.TILE_SIZE);
                            } else if (tileType == 34) {
                                map[row][col] = new GravityPad(col * Constants.TILE_SIZE, row * Constants.TILE_SIZE);
                            } else if (tileType == 35) {
                                map[row][col] = new SidewayBumper(col * Constants.TILE_SIZE, row * Constants.TILE_SIZE);
                            } else if (tileType == -35) {
                                map[row][col] = new SidewayBumperReversed(col * Constants.TILE_SIZE,
                                        row * Constants.TILE_SIZE);
                            } else if (tileType == 21) {
                                map[row][col] = new RocketPortal(col * Constants.TILE_SIZE, row * Constants.TILE_SIZE);
                            } else if (tileType == 22) {
                                map[row][col] = new DashPortal(col * Constants.TILE_SIZE, row * Constants.TILE_SIZE);
                            } else if (tileType == 23) {
                                map[row][col] = new UFOPortal(col * Constants.TILE_SIZE, row * Constants.TILE_SIZE);
                            } else if (tileType == 24) {
                                map[row][col] = new CubePortal(col * Constants.TILE_SIZE, row * Constants.TILE_SIZE);
                            } else if (tileType == 25) {
                                map[row][col] = new InvertedGravityPortal(col * Constants.TILE_SIZE,
                                        row * Constants.TILE_SIZE);
                            } else if (tileType == 26) {
                                map[row][col] = new NormalGravityPortal(col * Constants.TILE_SIZE,
                                        row * Constants.TILE_SIZE);
                            } else if (tileType == 27) {
                                map[row][col] = new EntryTeleportationPortal(col * Constants.TILE_SIZE,
                                        row * Constants.TILE_SIZE);
                            } else if (tileType == 28) {
                                map[row][col] = new ExitTeleportationPortal(col * Constants.TILE_SIZE,
                                        row * Constants.TILE_SIZE);
                            } else if (tileType == 29) {
                                map[row][col] = new WheelPortal(col * Constants.TILE_SIZE,
                                        row * Constants.TILE_SIZE);
                            } else if (tileType == 41) {
                                map[row][col] = new SlowSpeedPortal(col * Constants.TILE_SIZE,
                                        row * Constants.TILE_SIZE);
                            } else if (tileType == 42) {
                                map[row][col] = new BaseSpeedPortal(col * Constants.TILE_SIZE,
                                        row * Constants.TILE_SIZE);
                            } else if (tileType == 43) {
                                map[row][col] = new Boost1SpeedPortal(col * Constants.TILE_SIZE,
                                        row * Constants.TILE_SIZE);
                            } else if (tileType == 44) {
                                map[row][col] = new Boost2SpeedPortal(col * Constants.TILE_SIZE,
                                        row * Constants.TILE_SIZE);
                            } else if (tileType == 45) {
                                map[row][col] = new Boost3SpeedPortal(col * Constants.TILE_SIZE,
                                        row * Constants.TILE_SIZE);
                            } else if (tileType == 51) {
                                map[row][col] = new YellowOrb(col * Constants.TILE_SIZE,
                                        row * Constants.TILE_SIZE);
                            } else if (tileType == 52) {
                                map[row][col] = new PurpleOrb(col * Constants.TILE_SIZE,
                                        row * Constants.TILE_SIZE);
                            } else if (tileType == 53) {
                                map[row][col] = new BlueOrb(col * Constants.TILE_SIZE,
                                        row * Constants.TILE_SIZE);
                            } else if (tileType == 54) {
                                map[row][col] = new RedOrb(col * Constants.TILE_SIZE,
                                        row * Constants.TILE_SIZE);
                            } else if (tileType == 55) {
                                map[row][col] = new BlackOrb(col * Constants.TILE_SIZE,
                                        row * Constants.TILE_SIZE);
                            } else {
                                map[row][col] = null;
                            }
                        } catch (NumberFormatException e) {
                            System.err.println(
                                    "Invalid number format at row " + row + ", col " + col + ": " + cells[col]);
                            throw e;
                        }
                    }
                    row++;
                }
            }
        } catch (IOException e) {
            Logger.getLogger(Level.class.getName()).log(java.util.logging.Level.SEVERE, "Error loading map", e);
        }
    }

    public ImageView getLevelView() {
        return this.levelView;
    }

    public ImageView getHitboxLayerView() {
        return hitboxLayerView;
    }

    private void writeHitboxImageToPixelWriter(PixelWriter pixelWriter, int row, int col, Image tileImage) {
        if (tileImage == null) {
            return;
        }
        for (int y = 0; y < tileImage.getHeight(); y++) {
            for (int x = 0; x < tileImage.getWidth(); x++) {
                Color color = tileImage.getPixelReader().getColor(x, y);
                pixelWriter.setColor(col * Constants.TILE_SIZE + x, row * Constants.TILE_SIZE + y, color);
            }
        }
    }

    public void displayLevel() {
        Image backgroundImage = new Image(
                Objects.requireNonNull(getClass().getResourceAsStream("/assets/background.png")));
        ImageView backgroundView = new ImageView(backgroundImage);
        backgroundView.setFitWidth(this.width * Constants.TILE_SIZE);
        backgroundView.setFitHeight(this.height * Constants.TILE_SIZE);

        int imageWidth = this.width * Constants.TILE_SIZE;
        int imageHeight = this.height * Constants.TILE_SIZE;

        WritableImage levelImage = new WritableImage(imageWidth, imageHeight);
        PixelWriter pixelWriter = levelImage.getPixelWriter();

        for (int row = 0; row < this.height; row++) {
            for (int col = 0; col < this.width; col++) {
                GameObject tile = map[row][col];
                if (tile != null) {
                    Image tileImage = tile.getImage();
                    writeHitboxImageToPixelWriter(pixelWriter, row, col, tileImage);
                }
            }
        }

        ImageView levelView = new ImageView(levelImage);
        levelView.setX(0);
        levelView.setY(0);
        this.levelView = levelView;
    }

    public void displayLevelHitbox() {
        int imageWidth = this.width * Constants.TILE_SIZE;
        int imageHeight = this.height * Constants.TILE_SIZE;

        WritableImage levelImage = new WritableImage(imageWidth, imageHeight);
        PixelWriter pixelWriter = levelImage.getPixelWriter();

        for (int row = 0; row < this.height; row++) {
            for (int col = 0; col < this.width; col++) {
                GameObject tile = map[row][col];
                if (tile != null) {
                    Hitbox hitbox = tile.getHitbox();
                    if (hitbox != null) {
                        Image hitboxImage = hitbox.createHitboxImage();
                        writeHitboxImageToPixelWriter(pixelWriter, row, col, hitboxImage);
                    }
                }
            }
        }

        ImageView levelView = new ImageView(levelImage);
        levelView.setX(0);
        levelView.setY(0);
        this.hitboxLayerView = levelView;
    }

    public void updatePosition() {
        this.x -= this.velocityX;
        if (this.levelView != null) {
            this.levelView.setX(this.x);
        }
        if (this.hitboxLayerView != null) {
            this.hitboxLayerView.setX(this.x);
        }
    }

    public GameObject getTileTypeAt(double x, double y) {
        int col = (int) ((x - this.x) / Constants.TILE_SIZE);
        int row = (int) (y / Constants.TILE_SIZE);

        if (row >= 0 && row < height && col >= 0 && col < width) {
            return map[row][col];
        }
        return null;
    }

    public double getWidth() {
        return width * Constants.TILE_SIZE;
    }

    public GameObject[][] getMap() {
        return map;
    }

}
