package de.lukassch.mastermind.render;

import de.lukassch.mastermind.game.Code;
import de.lukassch.mastermind.game.Colors;
import de.lukassch.mastermind.game.Game;
import de.lukassch.mastermind.game.Result;
import de.lukassch.mastermind.Constants;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GameBoardElement extends AbstractElement {

    private final Game game;
    private final ColorPickerElement colorPicker;
    private final List<ColorableRectangle> rectangles = new ArrayList<>();

    public GameBoardElement(Game game, ColorPickerElement colorPicker) {
        this.game = game;
        this.colorPicker = colorPicker;
    }

    @Override
    public void onResize(Canvas canvas) {
        this.rectangles.clear();

        for (int i = 0; i < Constants.CODE_LENGTH; i++) {
            double minX = 200 + i * 40;
            double minY = (this.game.getCurrentIndex() + 1) * 40;
            double maxX = minX + 30;
            double maxY = minY + 30;

            this.rectangles.add(new ColorableRectangle(null, minX, minY, maxX, maxY));
        }
    }

    @Override
    public void onDraw(Canvas canvas, GraphicsContext context) {
        for (int j = 0; j < Constants.TRIES; j++) {
            boolean isSelected = j == this.game.getCurrentIndex();

            context.setFill(isSelected ? Constants.ERROR_500 : Color.BLACK);
            context.fillText((j + 1) + "", 120, (j + 1) * 40);

            int offsetY = (j + 1) * 40;

            this.drawResult(offsetY, context, this.game.getResult(j));
            this.drawCircles(offsetY, isSelected, j, context);
        }
    }

    @Override
    public void onClick(MouseEvent event) {
        this.rectangles.stream()
                .filter(element -> element.isInside(event.getX(), event.getY()))
                .findFirst()
                .ifPresent(element -> {
                    element.setColor(this.colorPicker.getCurrentColor());
                    this.callChange();
                });
    }

    @Override
    public void onKeyPress(KeyEvent event) {
        // Determine if the pressed key is a number between 1-6 and calculate its value from ascii table
        int code = event.getCode().getCode();

        if (code < 49 || code > 54) {
            return;
        }

        // Number between 0 and 5
        int index = code - 49;

        this.rectangles.get(index).setColor(this.colorPicker.getCurrentColor());
        this.callChange();
    }

    public void incrementIndex() {
        this.game.incrementIndex();
    }

    public Optional<Code> readCurrentCode() {
        if (this.rectangles.stream().anyMatch(rec -> rec.getColor() == null)) {
            return Optional.empty();
        }

        Colors[] colors = this.rectangles.stream().map(ColorableRectangle::getColor).toList().toArray(new Colors[Constants.CODE_LENGTH]);
        return Optional.of(new Code(colors));
    }

    private void drawResult(int offsetY, GraphicsContext context, List<Result> results) {
        int index = 0;
        for (int x = 145; x <= 175; x += 15) {
            for (int y = 0; y <= 15; y += 15) {
                Result result = results == null ? Result.GRAY : results.get(index);
                context.setFill(result.getFxColor());
                context.setStroke(Color.BLACK);

                context.strokeOval(x, y + offsetY, Constants.SMALL_CIRCLE_SIZE, Constants.SMALL_CIRCLE_SIZE);
                context.fillOval(x, y + offsetY, Constants.SMALL_CIRCLE_SIZE, Constants.SMALL_CIRCLE_SIZE);

                index++;
            }
        }
    }

    private void drawCircles(int offsetY, boolean isSelected, int index, GraphicsContext context) {
        context.strokeLine(200, offsetY + 15, 175 + Constants.CODE_LENGTH * 40, offsetY + 15);

        for (int i = 0; i < Constants.CODE_LENGTH; i++) {
            if (isSelected) {
                ColorableRectangle rectangle = this.rectangles.get(i);
                Color color = rectangle.getFxColor();
                context.setFill(color);
            } else {
                Colors colors = this.game.getColor(index, i);
                context.setFill(colors == null ? Constants.NEUTRAL_400 : colors.getFxColor());
            }

            context.fillOval(200 + i * 40, offsetY, Constants.CIRCLE_SIZE, Constants.CIRCLE_SIZE);
            context.setStroke(Color.BLACK);
            context.strokeOval(200 + i * 40, offsetY, Constants.CIRCLE_SIZE, Constants.CIRCLE_SIZE);
        }
    }
}
