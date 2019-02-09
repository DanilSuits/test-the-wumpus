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
                return "INSTRUCTIONS (Y-N)";
            }
        }

        Prompts prompts = new Prompts();

        // The interactive-shell is running
        boolean interactiveLoop = true;

        // The game starts
        int gameState = 0;

        while (interactiveLoop) {
            if (0 == gameState) {
                // We write the instructions prompt
                out.println(prompts.instructions());
            } else {
                // Print advertisement
                String advertisement =
                                "\n" +
                                "     ATTENTION ALL WUMPUS LOVERS!!!\n" +
                                "     THERE ARE NOW TWO ADDITIONS TO THE WUMPUS FAMILY\n" +
                                " OF PROGRAMS.\n" +
                                "\n" +
                                "     WUMP2:  SOME DIFFERENT CAVE ARRANGEMENTS\n" +
                                "     WUMP3:  DIFFERENT HAZARDS\n" +
                                "\n" ;
                out.print(advertisement);

                // Print Title
                out.println("HUNT THE WUMPUS");
                // Print Hazards
                out.println("I FEEL A DRAFT");
                // Print View
                //   Print Room
                out.println("YOU ARE IN ROOM 14");
                //   Print Tunnels
                out.println("TUNNELS LEAD TO 4 13 15");
                //   Print Whitespace
                out.println();
                // Print Prompt.
                out.println("SHOOT OR MOVE (S-M)");
            }
            // We tell the game the prompt has been displayed
            // The game tells us it needs a line of input
            // We check for input
            try {
                String input = lines.nextLine();
                gameState++;
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
