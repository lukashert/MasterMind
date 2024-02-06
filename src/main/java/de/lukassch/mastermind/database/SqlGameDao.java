package de.lukassch.mastermind.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class SqlGameDao implements GameDao {

    private final Connection connection;

    public SqlGameDao(Connection connection) {
        this.connection = connection;

        this.execute("""
                CREATE TABLE IF NOT EXISTS games
                (
                    id  INTEGER PRIMARY KEY AUTOINCREMENT,
                    date TIMESTAMP DEFAULT current_timestamp,
                    name TEXT NOT NULL,
                    duration BIGINT,
                    tries INTEGER
                );
                """, stm -> {
        });
    }

    @Override
    public void close() {
        try {
            this.connection.close();
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error while closing sql connection", ex);
        }
    }

    @Override
    public void saveGame(String name, long duration, int tries) {
        this.execute("INSERT INTO games (name, duration, tries) VALUES (?,?,?)", statement -> {
            statement.setString(1, name);
            statement.setLong(2, duration);
            statement.setInt(3, tries);
        });
    }

    @Override
    public List<GameStats> getTopEntries(int entries) {
        List<GameStats> stats = new ArrayList<>();
        try (PreparedStatement statement = this.connection.prepareStatement("""
                SELECT * FROM games
                ORDER BY duration, tries, name
                LIMIT ?
                """)) {

            statement.setInt(1, entries);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                GameStats gameStats = new GameStats(resultSet.getInt("id"), resultSet.getString("name"),
                        resultSet.getInt("tries"), resultSet.getLong("duration"), resultSet.getDate("date"));
                stats.add(gameStats);
            }

            resultSet.close();
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, String.format("Error while loading top %d entries", entries), ex);
        }
        return stats;
    }

    @Override
    public void delete(int id) {
        this.execute("DELETE FROM games WHERE id = ?",
                statement -> statement.setInt(1, id));
    }

    private void execute(String sqlStatement, SqlConsumer statementConsumer) {
        try (PreparedStatement statement = this.connection.prepareStatement(sqlStatement)) {
            statementConsumer.accept(statement);
            statement.execute();
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, String.format("Error while executing statement '%s'", sqlStatement), ex);
        }
    }
}
