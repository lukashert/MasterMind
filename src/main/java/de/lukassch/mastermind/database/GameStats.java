package de.lukassch.mastermind.database;

import java.util.Date;

/**
 * Represents a stats entry of a player.
 *
 * @param id       The unique id of the entry
 * @param name     The name of the player
 * @param tries    The count of tries needed to find the solution
 * @param duration The duration of the game in milliseconds
 * @param date     The timestamp when the game was saved
 */
public record GameStats(int id, String name, int tries, long duration, Date date) {
}
