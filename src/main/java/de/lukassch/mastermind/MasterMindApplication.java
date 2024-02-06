package de.lukassch.mastermind;

import de.lukassch.mastermind.database.DatabaseProvider;
import de.lukassch.mastermind.game.RecoverGameProvider;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MasterMindApplication extends Application {

    public static void main(String[] args) {
        // Connect to database
        DatabaseProvider.init("stats.sqlite");

        // Launch GUI
        launch();

        // Close database connection + Save current game
        DatabaseProvider.close();
        RecoverGameProvider.saveGame();
    }

    @Override
    public void start(Stage stage) {
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/lock.png")));

        SceneSwitcher.openMainMenu(stage);
    }


}