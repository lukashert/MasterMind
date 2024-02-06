package de.lukassch.mastermind.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public interface GameDao {

    Logger LOGGER = Logger.getLogger(GameDao.class.getSimpleName());

    static GameDao createDao(String fileName) {
        String url = "jdbc:sqlite:" + fileName;

        try {
            Connection connection = DriverManager.getConnection(url);
            DatabaseMetaData meta = connection.getMetaData();

            LOGGER.info(String.format("Connected to database driver %s", meta.getDriverName()));
            return new SqlGameDao(connection);
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, String.format("Error while connecting to file %s", fileName), ex);
        }

        return new EmptyDao();
    }

    void close();

    /**
     * Creates a new stats entry in the database.
     *
     * @param name     The name of the player
     * @param duration The duration of the game in milliseconds
     * @param tries    The count of tries
     */
    void saveGame(String name, long duration, int tries);

    /**
     * Returns a list of top players.
     *
     * @param entries Count of maximum entries
     * @return A sorted list of the top n players
     */
    List<GameStats> getTopEntries(int entries);

    /**
     * Delete a specific entry
     *
     * @param id The id of the entry
     */
    void delete(int id);
}
