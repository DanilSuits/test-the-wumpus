package com.vocumsineratio.wumpus.grimm;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Danil Suits (danil@vast.com)
 */
public class FallInPitTest {
    @Test
    public void fallInThePit() {
        Hansel game = new Hansel();
        game.onLine("N");
        game.onLine("M");
        game.onLine("4");

        List<String> lines = new ArrayList<>();
        for(String line : game.prompt()) {
            lines.add(line);
        }

        Assertions.assertEquals(
                Arrays.asList(
                        "YYYIIIIEEEE . . . FELL IN PIT",
                        "HA HA HA - YOU LOSE!",
                        "SAME SET-UP (Y-N)"
                ),
                lines
        );

    }
}
