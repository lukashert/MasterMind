package de.lukassch.mastermind.controller;

import de.lukassch.mastermind.SceneSwitcher;
import de.lukassch.mastermind.game.Code;
import de.lukassch.mastermind.game.Game;
import de.lukassch.mastermind.game.RecoverGameProvider;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MenuController {

    @FXML
    private Button recoverButton;

    @FXML
    public void onGameStart(ActionEvent event) {
        Button button = (Button) event.getSource();
        Stage stage = (Stage) button.getScene().getWindow();
        SceneSwitcher.startGame(stage, () -> new Game(Code.generateCode()));
    }

    @FXML
    public void onOpenHighscores(ActionEvent event) {
        Button button = (Button) event.getSource();
        Stage stage = (Stage) button.getScene().getWindow();
        SceneSwitcher.openHighscores(stage);
    }

    @FXML
    public void onRecoverGame(ActionEvent event) {
        Button button = (Button) event.getSource();
        Stage stage = (Stage) button.getScene().getWindow();

        if (!SceneSwitcher.startGame(stage, RecoverGameProvider::recoverGameAndDelete)) {
            this.recoverButton.setDisable(true);
        }
    }

    @FXML
    public void initialize() {
        this.recoverButton.setDisable(!RecoverGameProvider.canRecoverGame());
    }
}