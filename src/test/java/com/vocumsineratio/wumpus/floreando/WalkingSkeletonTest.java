package com.vocumsineratio.wumpus.floreando;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Collections;
import java.util.Map;

/**
 * @author Danil Suits (danil@vast.com)
 */
public class WalkingSkeletonTest {
    @Test
    public void first_prompt_should_offer_instructions() {
        Map<String, String> environment = Collections.EMPTY_MAP;
        byte[] nullInput = {};
        ByteArrayInputStream in = new ByteArrayInputStream(nullInput);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(outputStream);

        CLI.interactiveShell(environment, in, out);

        Assertions.assertEquals(
                "INSTRUCTIONS (Y-N)\n",
                outputStream.toString()
        );
    }

    @Test
    public void start_the_game_if_the_instructions_are_skipped() {
        Map<String, String> environment = Collections.EMPTY_MAP;
        byte[] nullInput = "N\n".getBytes();
        ByteArrayInputStream in = new ByteArrayInputStream(nullInput);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(outputStream);

        CLI.interactiveShell(environment, in, out);

        String expectedTranscript =
                "INSTRUCTIONS (Y-N)\n" +
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

        Assertions.assertEquals(
                expectedTranscript,
                outputStream.toString()
        );
    }
}
