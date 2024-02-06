package de.lukassch.mastermind.database;

import java.util.List;

public class DatabaseProvider {

    private static GameDao dao;

    public static void init(String fileName) {
        if (DatabaseProvider.dao != null) {
            throw new IllegalStateException("Database was already initialized.");
        }

        DatabaseProvider.dao = GameDao.createDao(fileName);
    }

    public static void close() {
        DatabaseProvider.dao.close();
    }

    /**
     * Creates a new stats entry in the database.
     *
     * @param name     The name of the player
     * @param duration The duration of the game in milliseconds
     * @param tries    The count of tries
     */
    public static void saveGame(String name, long duration, int tries) {
        DatabaseProvider.dao.saveGame(name, duration, tries);
    }

    /**
     * Returns a list of top players.
     *
     * @param entries Count of maximum entries
     * @return A sorted list of the top n players
     */
    public static List<GameStats> getTopEntries(int entries) {
        return DatabaseProvider.dao.getTopEntries(entries);
    }

    /**
     * Delete a specific entry
     *
     * @param id The id of the entry
     */
    public static void delete(int id) {
        DatabaseProvider.dao.delete(id);
    }

    private DatabaseProvider() {
    }
}
