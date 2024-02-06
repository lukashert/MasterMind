package de.lukassch.mastermind;

import de.lukassch.mastermind.controller.GameController;
import de.lukassch.mastermind.controller.WinController;
import de.lukassch.mastermind.game.Game;
import de.lukassch.mastermind.game.RecoverGameProvider;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The SceneSwitcher provides static functions for changing the current scene.
 */
public class SceneSwitcher {

    private static final Logger LOGGER = Logger.getLogger(SceneSwitcher.class.getSimpleName());

    public static boolean startGame(@NotNull Stage stage, @NotNull IGameCallable gameConsumer) {
        Game game = gameConsumer.call();

        if (game == null) {
            return false;
        }

        FXMLLoader loader = SceneSwitcher.showScene("game.fxml", stage);

        if (loader == null) {
            return false;
        }

        game.start();
        RecoverGameProvider.setGame(game);

        GameController controller = loader.getController();
        controller.init(game);
        return true;
    }

    public static void openHighScoreSave(@NotNull Stage stage, long duration, int tries) {
        FXMLLoader loader = SceneSwitcher.showScene("win.fxml", stage);

        if (loader == null) {
            return;
        }

        WinController winController = loader.getController();
        winController.init(duration, tries);
    }

    /**
     * Opens the main menu scene.
     *
     * @param stage The parent javafx scene
     */
    public static void openMainMenu(@NotNull Stage stage) {
        SceneSwitcher.showScene("menu.fxml", stage);
    }

    /**
     * Opens the highscore scene.
     *
     * @param stage The parent javafx scene
     */
    public static void openHighscores(@NotNull Stage stage) {
        SceneSwitcher.showScene("highscores.fxml", stage);
    }

    /**
     * Loads and opens a fxml scene.
     *
     * @param resourceFile The name of the fxml file
     * @param stage        The parent javafx stage
     * @return The used fxml loader
     */
    private static FXMLLoader showScene(@NotNull String resourceFile, @NotNull Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MasterMindApplication.class.getResource(resourceFile));
            Scene scene = new Scene(fxmlLoader.load(), stage.getWidth(), stage.getHeight());
            stage.setTitle("MasterMind");
            stage.setScene(scene);
            stage.show();

            RecoverGameProvider.clearGame();

            return fxmlLoader;
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, String.format("Error while loading fxml scene from file '%s'", resourceFile), ex);
            return null;
        }
    }

    /**
     * Utility class to generate a game object.
     */
    public interface IGameCallable {

        /**
         * Called to create a game object.
         *
         * @return The generated game object
         */
        @Nullable
        Game call();

    }
}
