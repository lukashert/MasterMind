package de.lukassch.mastermind.game;

import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Used to provide serialize/deserialize methods
 */
public interface ISaveable {

    /**
     * Writes the object into a data stream.
     *
     * @param stream The stream to write to
     * @throws IOException If an error occurs while writing
     */
    void write(@NotNull DataOutput stream) throws IOException;

    /**
     * Reads the object from a data stream.
     *
     * @param stream The stream to read from
     * @throws IOException If an error occurs while reading
     */
    void read(@NotNull DataInput stream) throws IOException;
}
