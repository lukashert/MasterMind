package de.lukassch.mastermind.game;

import javafx.scene.paint.Color;

public enum Colors {

    RED(0xA50032),
    PURPLE(0xB501EB),
    BLUE(0x89CBDF),
    TEAL(0x00B9AF),
    GREEN(0x50C878),
    YELLOW(0xABC444);

    private final Color fxColor;

    /**
     * @param color Hex color code as integer, looks like 0xRRGGBB
     */
    Colors(int color) {
        // Do some unnecessary bit shift magic
        int red = (color & 0xFF0000) >> 16;
        int green = (color & 0x00FF00) >> 8;
        int blue = color & 0x0000FF;

        this.fxColor = Color.rgb(red, green, blue);
    }

    /**
     * @return Returns the color as JavaFX Color
     */
    public Color getFxColor() {
        return this.fxColor;
    }
}
