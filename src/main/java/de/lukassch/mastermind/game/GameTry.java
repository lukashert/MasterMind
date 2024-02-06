package de.lukassch.mastermind.game;

import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameTry implements ISaveable {

    private Code code;
    private List<Result> results;

    public GameTry(@NotNull Code code, @NotNull List<Result> results) {
        this.code = code;
        this.results = results;
    }

    public GameTry() {
    }

    public Colors getColor(int index) {
        return this.code.get(index);
    }

    public List<Result> getResults() {
        return this.results;
    }

    @Override
    public void read(@NotNull DataInput stream) throws IOException {
        this.code = new Code();
        this.code.read(stream);

        int size = stream.readInt();
        this.results = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {
            this.results.add(Result.values()[stream.readInt()]);
        }
    }

    @Override
    public void write(@NotNull DataOutput stream) throws IOException {
        this.code.write(stream);

        stream.writeInt(this.results.size());

        for (Result result : this.results) {
            stream.writeInt(result.ordinal());
        }
    }
}
