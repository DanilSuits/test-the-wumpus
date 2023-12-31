package com.vocumsineratio.wumpus.floreando;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static com.vocumsineratio.wumpus.floreando.GameTest.Effects.LINE;
import static com.vocumsineratio.wumpus.floreando.GameTest.Effects.DICEROLL;

/**
 * @author Danil Suits (danil@vast.com)
 */
public class GameTest {
    @Test
    public void first_prompt_should_offer_instructions() {
        Game game = new Game();

        Assertions.assertEquals(
                Collections.singletonList("INSTRUCTIONS (Y-N)"),
                game.prompt()
        );
    }

    @Test
    public void start_the_game_if_the_instructions_are_skipped() {
        Game game = new Game();
        game.onLine("N");
        game.onDiceRoll(0);

        String expectedTranscript =
                "\n" +
                        "     ATTENTION ALL WUMPUS LOVERS!!!\n" +
                        "     THERE ARE NOW TWO ADDITIONS TO THE WUMPUS FAMILY\n" +
                        " OF PROGRAMS.\n" +
                        "\n" +
                        "     WUMP2:  SOME DIFFERENT CAVE ARRANGEMENTS\n" +
                        "     WUMP3:  DIFFERENT HAZARDS\n" +
                        "\n" +
                        "HUNT THE WUMPUS\n" +
                        "I FEEL A DRAFT\n" +
                        "YOU ARE IN ROOM 14\n" +
                        "TUNNELS LEAD TO 4 13 15\n" +
                        "\n" +
                        "SHOOT OR MOVE (S-M)\n";

        String[] split = expectedTranscript.split("\n");
        Assertions.assertEquals(
                Arrays.asList(split),
                game.prompt()
        );
    }

    enum Effects { LINE, DICEROLL}
    @Test
    public void test_first_choice_is_input() {
        Game game = new Game();

        Effects actual = game.effect(LINE, DICEROLL);
        Assertions.assertEquals(LINE, actual);
    }

    @Test
    public void game_needs_random_seed_effect() {
        Game game = new Game();
        game.onLine("N");

        Effects actual = game.effect(LINE, DICEROLL);
        Assertions.assertEquals(DICEROLL, actual);

    }
}
