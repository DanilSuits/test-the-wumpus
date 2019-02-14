package com.vocumsineratio.wumpus.floreando;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
        
        class InteractiveLoop {
            boolean running = true;

            void onQuit() {
                running = false;
            }
        }

        abstract class Action {
            abstract void mumbleBagel(Game game, InteractiveLoop loop);
        }

        Scanner lines = new Scanner(in);

        Action line = new Action() {
            @Override
            void mumbleBagel(Game game, InteractiveLoop loop) {
                game.prompt().forEach(out::println);
                try {
                    String line = lines.nextLine();
                    game.onLine(line);
                } catch (NoSuchElementException e) {
                    // The input is exhausted
                    // So there's nothing more to interact with
                    // so the loop is done...
                    loop.onQuit();
                }
            }
        };

        Action diceRoll = new Action() {
            @Override
            void mumbleBagel(Game game, InteractiveLoop loop) {
                game.onDiceRoll(0);
            }
        };

        Game game = new Game();
        InteractiveLoop interactiveLoop = new InteractiveLoop();

        while (interactiveLoop.running) {
            game.effect(line, diceRoll).mumbleBagel(game, interactiveLoop);
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
