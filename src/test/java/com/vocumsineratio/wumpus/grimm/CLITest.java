package com.vocumsineratio.wumpus.grimm;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Collections;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

/**
 * @author Danil Suits (danil@vast.com)
 */
class CLITest {
    @Test
    public void walkingSkeleton () {
        Map<String,String> environment = Collections.emptyMap();

        byte [] empty = {};
        ByteArrayInputStream in = new ByteArrayInputStream(empty);

        ByteArrayOutputStream gameTranscript = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(gameTranscript);

        CLI.interactiveShell(environment, in, out);

        Assertions.assertEquals(
                "INSTRUCTIONS (Y-N)\n",
                gameTranscript.toString()
        );
    }

    @Test
    public void readInput () {
        Map<String,String> environment = Collections.emptyMap();

        byte [] inputs = "N\n".getBytes();
        ByteArrayInputStream in = new ByteArrayInputStream(inputs);

        ByteArrayOutputStream gameTranscript = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(gameTranscript);

        CLI.interactiveShell(environment, in, out);

        // Check that the output starts with the correct line.
        Assertions.assertTrue(
                gameTranscript.toString().startsWith("INSTRUCTIONS (Y-N)\n")
        ) ;

        // Check that the output ends with the correct delimiter
        Assertions.assertTrue(
                gameTranscript.toString().endsWith("\n")
        ) ;

        // Note: this doesn't demonstrate that the output text is correct,
        // just that it is different from the case where the input is immediately
        // exhausted.  It might be that we want something even simpler here;
        // along the lines of a having a simple count of the number of
        // prompts that have been displayed.
        Assertions.assertNotEquals(
                "INSTRUCTIONS (Y-N)\n",
                gameTranscript.toString()
        );
    }
}