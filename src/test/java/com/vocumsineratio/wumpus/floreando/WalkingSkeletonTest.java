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
        Map<String,String> environment = Collections.EMPTY_MAP;
        byte [] nullInput = {};
        ByteArrayInputStream in = new ByteArrayInputStream(nullInput);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(outputStream);

        CLI.interactiveShell(environment, in, out);

        Assertions.assertEquals(
                "INSTRUCTIONS Y-N\n",
                outputStream.toString()
        );

    }
}
