package de.lukassch.mastermind.database;

import java.util.Collections;
import java.util.List;

public class EmptyDao implements GameDao {

    @Override
    public void close() {
    }

    @Override
    public void saveGame(String name, long duration, int tries) {
    }

    @Override
    public List<GameStats> getTopEntries(int entries) {
        return Collections.emptyList();
    }

    @Override
    public void delete(int id) {
    }
}
