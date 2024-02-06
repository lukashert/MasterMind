package de.lukassch.mastermind.render;

import de.lukassch.mastermind.SceneSwitcher;
import de.lukassch.mastermind.game.Game;
import de.lukassch.mastermind.game.GameState;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class GameCanvas {

    private final Game game;
    private final Canvas canvas;

    private final List<AbstractElement> elements = new ArrayList<>();
    private final GameBoardElement gameBoard;
    private final GameEndElement endElement;

    public GameCanvas(Game game, Canvas canvas) {
        this.game = game;
        this.canvas = canvas;

        ColorPickerElement colorPickerElement = new ColorPickerElement();
        this.gameBoard = new GameBoardElement(game, colorPickerElement);
        this.endElement = new GameEndElement(game);

        this.registerElement(colorPickerElement);
        this.registerElement(this.gameBoard);
    }

    public void render() {
        GraphicsContext context = this.canvas.getGraphicsContext2D();
        // Clear screen
        context.clearRect(0, 0, this.canvas.getWidth(), this.canvas.getHeight());

        // Init font related stuff
        context.setFont(new Font(30));
        context.setTextBaseline(VPos.TOP);
        context.setTextAlign(TextAlignment.CENTER);

        if (this.game.getGameState() != GameState.RUNNING) {
            if (this.game.getGameState() == GameState.WIN) {
                SceneSwitcher.openHighScoreSave((Stage) canvas.getScene().getWindow(), this.game.getDuration(), this.game.getTries());
            }
            this.endElement.onDraw(this.canvas, context);
            return;
        }

        // Draw all game elements
        this.elements.forEach(element -> element.onDraw(this.canvas, context));
    }

    public void onMouseClick(MouseEvent event) {
        this.elements.forEach(element -> element.onClick(event));
    }

    public void onKeyPress(KeyEvent event) {
        this.elements.forEach(element -> element.onKeyPress(event));
    }

    public void onScroll(ScrollEvent event) {
        this.elements.forEach(element -> element.onScroll(event));
    }

    public void submitCode() {
        this.gameBoard.readCurrentCode().ifPresent(code -> {
            this.game.append(code);
            this.gameBoard.incrementIndex();
            this.gameBoard.onResize(this.canvas);
            this.render();
        });
    }

    private void registerElement(AbstractElement element) {
        element.onChange(this::render);
        element.onResize(this.canvas);

        this.elements.add(element);
    }

}
