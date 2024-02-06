package de.lukassch.mastermind.game;

import de.lukassch.mastermind.Constants;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Game implements ISaveable {

    private final GameTimer gameTimer = new GameTimer();

    private LinkedList<GameTry> previousCodes = new LinkedList<>();
    private Code rightCode;
    private long gameStart = System.currentTimeMillis();
    private int currentIndex = 0;

    private GameState gameState = GameState.RUNNING;

    public Game(Code rightCode) {
        this.rightCode = rightCode;
    }

    public Game() {
    }

    /**
     * Starts game related timers
     */
    public void start() {
        this.gameTimer.start();
    }

    /**
     * Stops the game
     */
    public void stop() {
        this.changeState(GameState.CANCELED);
    }

    public void setListener(Runnable runnable) {
        this.gameTimer.setListener(runnable);
    }

    public void append(Code code) {
        this.previousCodes.add(new GameTry(code, code.compare(this.rightCode)));

        if (code.equals(this.rightCode)) {
            this.changeState(GameState.WIN);
        } else if (this.previousCodes.size() >= Constants.TRIES) {
            this.changeState(GameState.LOOSE);
        }
    }

    @Nullable
    public List<Result> getResult(int index) {
        if (index >= this.previousCodes.size()) {
            return null;
        }

        return this.previousCodes.get(index).getResults();
    }

    @Nullable
    public Colors getColor(int index, int position) {
        if (index >= this.previousCodes.size()) {
            return null;
        }

        return this.previousCodes.get(index).getColor(position);
    }

    /**
     * Gives the duration of the game as a string.
     *
     * @return A formatted string of the game duration
     */
    public String getDisplayTime() {
        long time = System.currentTimeMillis() - this.gameStart;
        long seconds = time / 1000L;
        long minutes = (seconds / 60) % 60;

        return String.format("%02d:%02d", minutes, seconds % 60);
    }

    /**
     * Get the number of submitted tries
     *
     * @return The number of tries submitted
     */
    public int getTries() {
        return this.previousCodes.size();
    }

    public GameState getGameState() {
        return this.gameState;
    }

    public long getDuration() {
        return System.currentTimeMillis() - this.gameStart;
    }

    public int getCurrentIndex() {
        return this.currentIndex;
    }

    public void incrementIndex() {
        this.currentIndex++;
    }

    @Override
    public void read(@NotNull DataInput stream) throws IOException {
        // Read start offset
        long duration = stream.readLong();
        this.gameStart = System.currentTimeMillis() - duration;
        this.currentIndex = stream.readInt();

        this.rightCode = new Code();
        this.rightCode.read(stream);

        // Read previous data
        int size = stream.readInt();
        this.previousCodes = new LinkedList<>();

        for (int i = 0; i < size; i++) {
            GameTry gameTry = new GameTry();
            gameTry.read(stream);

            this.previousCodes.add(gameTry);
        }
    }

    @Override
    public void write(@NotNull DataOutput stream) throws IOException {
        // Save start offset
        stream.writeLong(System.currentTimeMillis() - this.gameStart);
        stream.writeInt(this.currentIndex);

        this.rightCode.write(stream);

        // Write previous data
        stream.writeInt(this.previousCodes.size());

        for (GameTry previousCode : this.previousCodes) {
            previousCode.write(stream);
        }
    }

    private void changeState(GameState gameState) {
        this.gameState = gameState;
        this.gameTimer.stop();
    }

}
