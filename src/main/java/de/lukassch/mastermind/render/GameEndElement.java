package de.lukassch.mastermind.render;

import de.lukassch.mastermind.game.Game;
import de.lukassch.mastermind.game.GameState;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

public class GameEndElement extends AbstractElement {

    private final Game game;

    public GameEndElement(Game game) {
        this.game = game;
    }

    @Override
    public void onResize(Canvas canvas) {

    }

    @Override
    public void onDraw(Canvas canvas, GraphicsContext context) {
        context.setTextAlign(TextAlignment.CENTER);
        context.setTextBaseline(VPos.CENTER);
        context.setFill(Color.BLACK);

        String text = game.getGameState() == GameState.WIN ? "YOU WON!" : "YOU LOST!";
        context.fillText(text, canvas.getWidth() / 2, canvas.getHeight() / 4);
    }

    @Override
    public void onClick(MouseEvent event) {

    }

    @Override
    public void onKeyPress(KeyEvent event) {

    }
}
