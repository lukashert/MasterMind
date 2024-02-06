package de.lukassch.mastermind.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Like a <code>{@link java.util.function.Consumer}</code>, but throws an SQLException
 */
public interface SqlConsumer {

    /**
     * Method is performed with the given prepared statement
     *
     * @param statement The given statement
     * @throws SQLException If something went wrong
     */
    void accept(PreparedStatement statement) throws SQLException;
}
