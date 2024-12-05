module com.example.geometrydash {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.swing;
    requires javafx.media;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.logging;

    opens com.example.geometrydash to javafx.fxml;
    opens com.example.geometrydash.player to javafx.fxml;
    opens com.example.geometrydash.enums to javafx.fxml;
    opens com.example.geometrydash.level to javafx.fxml;
    opens com.example.geometrydash.obstacles to javafx.fxml;
    opens com.example.geometrydash.hitbox to javafx.fxml;
    opens com.example.geometrydash.constants to javafx.fxml;
    opens com.example.geometrydash.game to javafx.fxml;

    exports com.example.geometrydash;
    exports com.example.geometrydash.player;
    exports com.example.geometrydash.enums;
    exports com.example.geometrydash.level;
    exports com.example.geometrydash.obstacles;
    exports com.example.geometrydash.hitbox;
    exports com.example.geometrydash.constants;
    exports com.example.geometrydash.game;
}