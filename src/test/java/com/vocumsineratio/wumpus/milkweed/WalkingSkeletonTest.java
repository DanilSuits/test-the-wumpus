package com.vocumsineratio.wumpus.milkweed;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * @author Danil Suits (danil@vast.com)
 */
class WalkingSkeletonTest {
    static class Example {
        static void run(InputStream stdin, PrintStream stdout) {
            Actions.Core<?> core = WalkingSkeleton.start();

            Runnable sut = StdIO.app(
                    stdin,
                    stdout,
                    core
            );
            sut.run();
        }
    }

    static class Harness {
        static String [] actual(String ... inputs) {
            ByteArrayOutputStream transcript = new ByteArrayOutputStream();

            {
                PrintStream out = new PrintStream(transcript);
                Example.run(
                        StdIn.inputStream(
                                inputs
                        ),
                        out
                );
            }

            return StdOut.actual(transcript);
        }

        static class StdIn {
            static InputStream inputStream (String... lines) {
                String joined = String.join("",
                        Arrays.stream(lines).map(s -> s + "\n").toArray(String[]::new)
                );

                return new ByteArrayInputStream(
                        joined.getBytes(StandardCharsets.UTF_8)
                );
            }
        }

        static class StdOut {
            static String [] actual(ByteArrayOutputStream transcript) {
                return transcript.toString().split("\n");
            }

            static String [] expected(String... outputs) {
                return outputs;
            }
        }

        static String [] expected(String... outputs) {
            return StdOut.expected(outputs);
        }
    }

    @Test
    public void helloBob() {
        Assertions.assertArrayEquals(
                Harness.expected(
                        "Who are you?",
                        "Hello Bob"
                ),
                Harness.actual(
                        "Bob"
                )
        );
    }
}
