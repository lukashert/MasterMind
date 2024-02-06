package de.lukassch.mastermind.game;

import de.lukassch.mastermind.Constants;
import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

/**
 * A code is a sequence of different colors that can be compared.
 */
public class Code implements ISaveable {

    private static final Random RANDOM = new Random();
    private static final Logger LOGGER = Logger.getLogger(Code.class.getSimpleName());

    public static Code generateCode() {
        Colors[] colors = new Colors[Constants.CODE_LENGTH];

        for (int i = 0; i < colors.length; i++) {
            colors[i] = Colors.values()[RANDOM.nextInt(Colors.values().length)];
        }

        LOGGER.info("Game code is " + Arrays.toString(colors));
        return new Code(colors);
    }

    private Colors[] colors;

    public Code(Colors... colors) {
        this.colors = colors;
    }

    public Code() {
    }

    public Colors get(int index) {
        return this.colors[index];
    }

    /**
     * Compares the current code with another and returns the resulting list of displayed colors
     *
     * @param comparing The other code
     * @return A shuffled list with the resulting colors, if both codes have the same size
     */
    public List<Result> compare(Code comparing) {
        if (this.colors.length != comparing.colors.length) {
            return Collections.emptyList();
        }

        List<Result> results = new ArrayList<>(this.colors.length);

        List<Colors> currentList = this.toList();
        List<Colors> comparingList = comparing.toList();

        List<Colors> newCurrentList = new ArrayList<>();
        List<Colors> newComparingList = new ArrayList<>();

        // Search for equal values and remove from lists
        for (int i = 0; i < currentList.size(); i++) {
            if (currentList.get(i) == comparingList.get(i)) {
                results.add(Result.WHITE);
            } else {
                newCurrentList.add(currentList.get(i));
                newComparingList.add(comparingList.get(i));
            }
        }

        // Search for equal colors but other position
        for (Colors color : newCurrentList) {
            for (int i = 0; i < newComparingList.size(); i++) {
                if (color == newComparingList.get(i)) {
                    newComparingList.remove(i);
                    results.add(Result.BLACK);
                }
            }
        }

        int fill = this.colors.length - results.size();
        for (int i = 0; i < fill; i++) {
            results.add(Result.GRAY);
        }

        return results;
    }

    @Override
    public void read(@NotNull DataInput stream) throws IOException {
        this.colors = new Colors[stream.readInt()];

        for (int i = 0; i < colors.length; i++) {
            this.colors[i] = Colors.values()[stream.readInt()];
        }
    }

    @Override
    public void write(@NotNull DataOutput stream) throws IOException {
        stream.writeInt(this.colors.length);

        for (Colors color : this.colors) {
            stream.writeInt(color.ordinal());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Code code = (Code) o;
        return Arrays.equals(colors, code.colors);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(colors);
    }

    private List<Colors> toList() {
        return new ArrayList<>(Arrays.asList(this.colors));
    }
}
