package de.lukassch.mastermind.game;

import javafx.application.Platform;
import org.jetbrains.annotations.Nullable;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Simple timer that calls a {@link Runnable} every second in the JavaFX thread.
 */
public class GameTimer extends TimerTask {

    private final Timer timer = new Timer("GameTimer", true);
    private Runnable runnable;

    /**
     * Start the timer.
     */
    public void start() {
        this.timer.schedule(this, 0, 1000);
    }

    /**
     * Stop the timer.
     */
    public void stop() {
        this.timer.cancel();
    }

    /**
     * Sets the timer listener. Only one listener can be attached.
     *
     * @param runnable The listener that is called every second
     */
    public void setListener(@Nullable Runnable runnable) {
        this.runnable = runnable;
    }

    @Override
    public void run() {
        Platform.runLater(() -> {
            if (this.runnable != null) {
                this.runnable.run();
            }
        });
    }
}
