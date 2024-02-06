package de.lukassch.mastermind.controller;

import de.lukassch.mastermind.SceneSwitcher;
import de.lukassch.mastermind.database.DatabaseProvider;
import de.lukassch.mastermind.database.GameStats;
import de.lukassch.mastermind.render.HighscoreNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

/**
 * Used to control the elements on the highscore view.
 */
public class HighscoreController {

    @FXML
    private ListView<HighscoreNode> topList;

    @FXML
    public void initialize() {
        int i = 1;

        for (GameStats topEntry : DatabaseProvider.getTopEntries(10)) {
            this.topList.getItems().add(new HighscoreNode(i, topEntry, this::onDelete));
            i++;
        }
    }

    @FXML
    public void onOpenMainMenu(ActionEvent event) {
        Button button = (Button) event.getSource();
        Stage stage = (Stage) button.getScene().getWindow();
        SceneSwitcher.openMainMenu(stage);
    }

    /**
     * Used to delete an element from the highscore list.
     *
     * @param event The called click event
     */
    private void onDelete(ActionEvent event) {
        Button button = (Button) event.getSource();
        HighscoreNode highscoreNode = (HighscoreNode) button.getParent().getParent();
        this.topList.getItems().remove(highscoreNode);

        DatabaseProvider.delete(highscoreNode.getStatsId());
    }
}
