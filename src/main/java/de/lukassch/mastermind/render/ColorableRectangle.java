package de.lukassch.mastermind.render;

import de.lukassch.mastermind.Constants;
import de.lukassch.mastermind.game.Colors;
import javafx.scene.paint.Color;

/**
 * Represents a 2-dimensional box with two positions and a color.
 */
public class ColorableRectangle {

    private final double minX, minY, maxX, maxY;
    private Colors color;

    public ColorableRectangle(Colors color, double minX, double minY, double maxX, double maxY) {
        this.color = color;
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;
    }

    /**
     * Checks if a point is inside the rectangle.
     *
     * @param x Given x position to check
     * @param y Given y position to check
     * @return If the given point is in the rectangle
     */
    public boolean isInside(double x, double y) {
        return x >= this.minX && y >= this.minY
                && x <= this.maxX && y <= this.maxY;
    }

    /**
     * Returns the javafx color of the rectangle.
     *
     * @return The javafx color or a default color if null
     */
    public Color getFxColor() {
        return this.color == null ? Constants.NEUTRAL_100 : this.color.getFxColor();
    }

    /**
     * Returns the color of the rectangle.
     * Can be null.
     *
     * @return The color
     */
    public Colors getColor() {
        return color;
    }

    /**
     * Sets the color of the rectangle.
     *
     * @param color The color to set.
     */
    public void setColor(Colors color) {
        this.color = color;
    }
}
