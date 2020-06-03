package com.vocumsineratio.wumpus.grimm;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Danil Suits (danil@vast.com)
 */
public class ShootOrMoveTest {
    @Test
    public void testBadInput() {
        Hansel game = new Hansel();
        game.onLine("N");
        game.onLine("X");

        List<String> lines = new ArrayList<>();
        for(String line : game.prompt()) {
            lines.add(line);
        }

        Assertions.assertEquals(
                Collections.singletonList("SHOOT OR MOVE (S-M)"),
                lines
        );
    }

    @Test
    public void testMove() {
        Hansel game = new Hansel();
        game.onLine("N");
        game.onLine("M");

        List<String> lines = new ArrayList<>();
        for(String line : game.prompt()) {
            lines.add(line);
        }

        Assertions.assertEquals(
                Collections.singletonList("WHERE TO"),
                lines
        );
    }

    @Test
    public void testShoot() {
        Hansel game = new Hansel();
        game.onLine("N");
        game.onLine("S");

        List<String> lines = new ArrayList<>();
        for(String line : game.prompt()) {
            lines.add(line);
        }

        Assertions.assertEquals(
                Collections.singletonList("NO. OF ROOMS(1-5)"),
                lines
        );
    }
}
