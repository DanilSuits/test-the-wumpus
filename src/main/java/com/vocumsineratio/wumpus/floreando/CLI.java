package com.vocumsineratio.wumpus.floreando;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * @author Danil Suits (danil@vast.com)
 */
public class CLI {
    public static void interactiveShell(
            Map<String, String> environment,
            InputStream in,
            PrintStream out) {

        Scanner lines = new Scanner(in);

        class Prompts {
            String instructions() {
                return "INSTRUCTIONS Y-N";
            }
        }

        Prompts prompts = new Prompts();

        // The interactive-shell is running
        boolean interactiveLoop = true;

        while (interactiveLoop) {
            // We write the instructions prompt
            out.println(prompts.instructions());

            // We tell the game the prompt has been displayed
            // The game tells us it needs a line of input
            // We check for input
            try {
                String input = lines.nextLine();
            } catch (NoSuchElementException e) {
                // The input is exhausted
                // So there's nothing more to interact with
                // so the loop is done...
                interactiveLoop = false;
            }
        }
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
