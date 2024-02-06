package de.lukassch.mastermind.game;

import java.io.*;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Manages the recover last game feature.
 */
public class RecoverGameProvider {

    private static final Logger LOGGER = Logger.getLogger(RecoverGameProvider.class.getSimpleName());
    private static final File LAST_GAME = new File("recover.game");

    // Used for upwards compatibility. First integer in every game file
    private static final int FILE_VERSION = 0;

    private static Game currentGame;

    /**
     * Tries to save the current game to the recover file.
     */
    public static void saveGame() {
        if (RecoverGameProvider.currentGame == null || RecoverGameProvider.currentGame.getGameState() != GameState.RUNNING) {
            return;
        }

        try (DataOutputStream stream = new DataOutputStream(new BufferedOutputStream(Files.newOutputStream(LAST_GAME.toPath())))) {
            stream.writeInt(RecoverGameProvider.FILE_VERSION);

            RecoverGameProvider.currentGame.write(stream);
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, String.format("Error while writing file %s", LAST_GAME.getName()));
        }

        RecoverGameProvider.clearGame();
    }

    /**
     * Read last game and delete the recover file.
     *
     * @return The read game object or null if something went wrong
     */
    public static Game recoverGameAndDelete() {
        Game game = null;

        try (DataInputStream stream = new DataInputStream(new BufferedInputStream(Files.newInputStream(LAST_GAME.toPath())))) {
            // Read version and ignore. Can be used if breaking changes are necessary.
            stream.readInt();

            game = new Game();
            game.read(stream);
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, String.format("Error while reading file %s", LAST_GAME.getName()));
        } finally {
            // Delete file if loaded correctly
            if (!LAST_GAME.delete()) {
                LOGGER.warning(String.format("File %s could not be deleted.", LAST_GAME.getName()));
            }
        }

        return game;
    }

    /**
     * Sets the current game object.
     *
     * @param game The game object
     */
    public static void setGame(Game game) {
        RecoverGameProvider.currentGame = game;
    }

    /**
     * Clears the current game object.
     */
    public static void clearGame() {
        RecoverGameProvider.currentGame = null;
    }

    /**
     * Checks if the recover file exists.
     *
     * @return If the recover file exists
     */
    public static boolean canRecoverGame() {
        return LAST_GAME.exists();
    }

    private RecoverGameProvider() {
    }
}
