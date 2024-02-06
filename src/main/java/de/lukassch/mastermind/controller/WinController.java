package de.lukassch.mastermind.controller;

import de.lukassch.mastermind.SceneSwitcher;
import de.lukassch.mastermind.database.DatabaseProvider;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class WinController {

    @FXML
    private TextField name;

    private long duration;
    private int tries;

    public void init(long duration, int tries) {
        this.duration = duration;
        this.tries = tries;
    }

    @FXML
    public void onSubmit(ActionEvent event) {
        // Check if name is empty
        if (this.name.getText().isBlank()) {
            return;
        }

        // Save game + Go back to main menu
        DatabaseProvider.saveGame(this.name.getText(), this.duration, this.tries);

        Button button = (Button) event.getSource();
        Stage stage = (Stage) button.getScene().getWindow();

        SceneSwitcher.openMainMenu(stage);
    }
}
