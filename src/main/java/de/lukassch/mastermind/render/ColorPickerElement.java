package de.lukassch.mastermind.render;

import de.lukassch.mastermind.game.Colors;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class ColorPickerElement extends AbstractElement {

    private final List<ColorableRectangle> elements = new ArrayList<>();
    private Colors currentColor = Colors.RED;

    private double perElement;
    private double width;

    @Override
    public void onResize(Canvas canvas) {
        this.width = canvas.getWidth() - 20;
        this.perElement = width / Colors.values().length;

        this.elements.clear();

        Colors[] colorsArray = Colors.values();

        for (int i = 0; i < colorsArray.length; i++) {
            Colors colors = colorsArray[i];
            double minX = 10 + i * perElement;
            double minY = 13 * 40;

            double maxX = minX + perElement;
            double maxY = minY + 40;

            this.elements.add(new ColorableRectangle(colors, minX, minY, maxX, maxY));
        }
    }

    @Override
    public void onDraw(Canvas canvas, GraphicsContext context) {
        context.setStroke(Color.BLACK);

        for (int i = 0; i < this.elements.size(); i++) {
            ColorableRectangle colors = this.elements.get(i);

            context.setFill(colors.getFxColor());
            context.fillRect(10 + i * this.perElement, 13 * 40, this.perElement, 40);
        }

        context.strokeRect(10, 13 * 40, this.width, 40);

        int current = this.currentColor.ordinal();

        context.setLineWidth(5);
        context.strokeRect(10 + current * this.perElement, 13 * 40, this.perElement, 40);
        context.setLineWidth(1);
    }

    @Override
    public void onClick(MouseEvent event) {
        this.elements.stream()
                .filter(element -> element.isInside(event.getX(), event.getY()))
                .findFirst()
                .ifPresent(element -> {
                    this.currentColor = element.getColor();
                    this.callChange();
                });
    }

    @Override
    public void onKeyPress(KeyEvent event) {
        // Check if arrow key was pressed
        int change = event.getCode() == KeyCode.RIGHT ? 1 : event.getCode() == KeyCode.LEFT ? -1 : 0;
        this.moveColor(change);
    }

    @Override
    public void onScroll(ScrollEvent event) {
        int change = event.getDeltaY() < 0 ? -1 : 1;
        this.moveColor(change);
    }

    public Colors getCurrentColor() {
        return this.currentColor;
    }

    private void moveColor(int direction) {
        if (direction == 0) {
            return;
        }
        int ordinal = this.currentColor.ordinal() + direction;

        if (ordinal < 0) {
            ordinal = Colors.values().length - 1;
        } else if (ordinal >= Colors.values().length) {
            ordinal = 0;
        }

        this.currentColor = Colors.values()[ordinal];
        this.callChange();
    }
}
