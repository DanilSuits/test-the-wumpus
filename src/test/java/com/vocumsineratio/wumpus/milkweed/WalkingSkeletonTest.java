package com.vocumsineratio.wumpus.milkweed;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

/**
 * @author Danil Suits (danil@vast.com)
 */
class WalkingSkeletonTest {
    @Test
    public void helloBob() {
        ByteArrayOutputStream gameTranscript = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(gameTranscript);

        byte [] dataFromPlayer = "Bob\n".getBytes(StandardCharsets.UTF_8);

        InputStream in = new ByteArrayInputStream(
                dataFromPlayer
        );

        Actions.Core<?> core = WalkingSkeleton.start();

        Runnable app = StdIO.app(in, out, core);
        app.run();

        Assertions.assertEquals(
                "Who are you?\n" +
                        "Hello Bob\n",
                gameTranscript.toString()
        );
    }
}
