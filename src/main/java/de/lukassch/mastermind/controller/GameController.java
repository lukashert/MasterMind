package de.lukassch.mastermind.controller;

import de.lukassch.mastermind.SceneSwitcher;
import de.lukassch.mastermind.game.Code;
import de.lukassch.mastermind.game.Game;
import de.lukassch.mastermind.render.GameCanvas;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.stage.Stage;

/**
 * Used to control the elements on the game view.
 */
public class GameController {

    private GameCanvas gameCanvas;
    private Game game;

    @FXML
    private Canvas canvas;

    @FXML
    private Label statsTime;

    @FXML
    private Label statsTry;

    public void init(Game game) {
        this.gameCanvas = new GameCanvas(game, canvas);
        this.gameCanvas.render();

        this.game = game;
        this.game.setListener(() -> this.statsTime.setText("Time: " + this.game.getDisplayTime()));
        this.updateTries();
    }

    /**
     * Called if the player submits a new code.
     */
    @FXML
    public void onSubmit() {
        this.gameCanvas.submitCode();
        this.updateTries();
    }

    /**
     * Called if the player exits the game or starts a new game.
     *
     * @param event The called event
     */
    @FXML
    public void onExit(ActionEvent event) {
        Button button = (Button) event.getSource();
        Stage stage = (Stage) button.getScene().getWindow();

        this.game.stop();

        if ("mainMenu".equals(button.getId())) {
            SceneSwitcher.openMainMenu(stage);
        } else {
            SceneSwitcher.startGame(stage, () -> new Game(Code.generateCode()));
        }
    }

    /**
     * Called if the player clicks his mouse.
     *
     * @param event The called event
     */
    @FXML
    public void onMouseClick(MouseEvent event) {
        this.gameCanvas.onMouseClick(event);
    }

    /**
     * Called if the player is scrolling with the mouse wheel.
     *
     * @param event The called event
     */
    @FXML
    public void onScroll(ScrollEvent event) {
        this.gameCanvas.onScroll(event);
    }

    /**
     * Called if the player presses any key on the keyboard.
     *
     * @param event The called event
     */
    @FXML
    public void onKeyPressed(KeyEvent event) {
        // ENTER = Submit code
        if (event.getCode() == KeyCode.ENTER) {
            this.onSubmit();
            return;
        }

        this.gameCanvas.onKeyPress(event);
    }

    /**
     * Updates the tries stats field with the current value.
     */
    private void updateTries() {
        this.statsTry.setText("Trys: " + this.game.getTries());
    }
}
