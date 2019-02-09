package com.vocumsineratio.wumpus.floreando;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Map;

/**
 * @author Danil Suits (danil@vast.com)
 */
public class CLI {
    public static void interactiveShell(
            Map<String, String> environment,
            InputStream in,
            PrintStream out) {

        class Prompts {
            String instructions() {
                return "INSTRUCTIONS Y-N";
            }
        }

        Prompts prompts = new Prompts();

        // The game is running

        // We write the instructions prompt
        out.println(prompts.instructions());

        // We tell the game the prompt has been displayed
        // The game tells us it needs a line of input
        // We check for input
        // The input is exhausted
        // We tell the game the input is exhausted
        // Therefore the game quits

        // The game has quit, so we are done.
    }

    public static void main(String[] args) {
        // We're not going to be using developer tests to cover this method
        // therefore it needs to be "so simple that there are obviously
        // no deficiencies"
        interactiveShell(
                System.getenv(),
                System.in,
                System.out
        );
    }
}
