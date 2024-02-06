package de.lukassch.mastermind.game;

import de.lukassch.mastermind.Constants;
import javafx.scene.paint.Color;

public enum Result {

    GRAY(Constants.NEUTRAL_400),
    BLACK(Color.BLACK),
    WHITE(Color.WHITE);

    private final Color fxColor;

    /**
     * @param color Hex color code as integer, looks like 0xRRGGBB
     */
    Result(Color color) {
        this.fxColor = color;
    }

    /**
     * @return Returns the color as JavaFX Color
     */
    public Color getFxColor() {
        return this.fxColor;
    }
}
