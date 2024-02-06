package de.lukassch.mastermind.game;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class CodeTest {

    @Test
    public void testEquals() {
        Code primary = new Code(Colors.RED, Colors.BLUE, Colors.YELLOW, Colors.BLUE);
        Code secondary = new Code(Colors.RED, Colors.BLUE, Colors.YELLOW, Colors.BLUE);
        Code third = new Code(Colors.YELLOW, Colors.BLUE, Colors.YELLOW, Colors.BLUE);

        Assertions.assertEquals(primary, secondary);
        Assertions.assertNotEquals(primary, third);
    }

    @Test
    public void testCompareMixed() {
        Code primary = new Code(Colors.RED, Colors.BLUE, Colors.YELLOW, Colors.BLUE);
        Code secondary = new Code(Colors.BLUE, Colors.RED, Colors.PURPLE, Colors.BLUE);

        List<Result> list = primary.compare(secondary);
        Assertions.assertEquals(1, list.stream().filter(r -> r == Result.GRAY).count());
        Assertions.assertEquals(2, list.stream().filter(r -> r == Result.BLACK).count());
        Assertions.assertEquals(1, list.stream().filter(r -> r == Result.WHITE).count());
    }

    @Test
    public void testCompareOnlyBlack() {
        Code primary = new Code(Colors.RED, Colors.BLUE, Colors.YELLOW, Colors.PURPLE);
        Code secondary = new Code(Colors.BLUE, Colors.RED, Colors.PURPLE, Colors.YELLOW);

        List<Result> list = primary.compare(secondary);
        Assertions.assertEquals(0, list.stream().filter(r -> r == Result.GRAY).count());
        Assertions.assertEquals(4, list.stream().filter(r -> r == Result.BLACK).count());
        Assertions.assertEquals(0, list.stream().filter(r -> r == Result.WHITE).count());
    }

    @Test
    public void testCompareOnlyGray() {
        Code primary = new Code(Colors.RED, Colors.BLUE, Colors.YELLOW, Colors.PURPLE);
        Code secondary = new Code(Colors.GREEN, Colors.TEAL, Colors.GREEN, Colors.TEAL);

        List<Result> list = primary.compare(secondary);

        Assertions.assertEquals(4, list.stream().filter(r -> r == Result.GRAY).count());
        Assertions.assertEquals(0, list.stream().filter(r -> r == Result.BLACK).count());
        Assertions.assertEquals(0, list.stream().filter(r -> r == Result.WHITE).count());
    }
}
