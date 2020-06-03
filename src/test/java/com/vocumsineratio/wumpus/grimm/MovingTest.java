package com.vocumsineratio.wumpus.grimm;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Danil Suits (danil@vast.com)
 */
public class MovingTest {
    @Test
    public void testBlocked() {
        Hansel game = new Hansel();
        game.onLine("N");
        game.onLine("M");
        game.onLine("1");

        List<String> lines = new ArrayList<>();
        for(String line : game.prompt()) {
            lines.add(line);
        }

        Assertions.assertEquals(
                Arrays.asList("NOT POSSIBLE", "WHERE TO"),
                lines
        );
    }
}
