package de.lukassch.mastermind.render;

import de.lukassch.mastermind.database.GameStats;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.TextAlignment;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

public class HighscoreNode extends HBox {

    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("dd.MM.yyyy hh:mm", Locale.GERMAN);

    static {
        FORMAT.setTimeZone(TimeZone.getTimeZone("Europe/Berlin"));
    }

    private final GameStats stats;

    public HighscoreNode(int number, GameStats stats, EventHandler<ActionEvent> eventCallback) {
        this.stats = stats;

        Label label = new Label(" #" + number + " " + stats.name() + " - Time: " + getDisplayTime() + " - Tries: " + stats.tries() + " - Date: " + FORMAT.format(stats.date()));
        label.getStyleClass().add("text-label");

        Button button = new Button("Delete");
        button.getStyleClass().add("outline-button");

        button.setFocusTraversable(false);

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().add(button);

        button.setTextAlignment(TextAlignment.CENTER);
        button.setOnAction(eventCallback);

        hBox.getChildren().add(label);

        this.getChildren().add(hBox);
    }

    private String getDisplayTime() {
        long time = stats.duration();
        long millis = time % 1000;
        long seconds = time / 1000L;
        long minutes = (seconds / 60) % 60;

        return String.format("%02d:%02d.%03d", minutes, seconds % 60, millis);
    }

    public int getStatsId() {
        return this.stats.id();
    }
}
