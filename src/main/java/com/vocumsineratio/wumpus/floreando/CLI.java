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

        class Prompts {
            String instructions() {
                return "INSTRUCTIONS (Y-N)";
            }

            String hunting() {
                return "SHOOT OR MOVE (S-M)";
            }

            String title() {
                return "HUNT THE WUMPUS";
            }

            String pitWarning() {
                return "I FEEL A DRAFT";
            }

            String room(int roomNumber) {
                return "YOU ARE IN ROOM 14";
            }

            String tunnels(int first, int second, int third) {
                return "TUNNELS LEAD TO 4 13 15";
            }

            String advertisement() {
                return "\n" +
                        "     ATTENTION ALL WUMPUS LOVERS!!!\n" +
                        "     THERE ARE NOW TWO ADDITIONS TO THE WUMPUS FAMILY\n" +
                        " OF PROGRAMS.\n" +
                        "\n" +
                        "     WUMP2:  SOME DIFFERENT CAVE ARRANGEMENTS\n" +
                        "     WUMP3:  DIFFERENT HAZARDS\n"
                        ;

            }

            String blankLine() {
                return "";
            }
        }

        Prompts prompts = new Prompts();

        abstract class Choices<T> {
            abstract T input();

            abstract T random();
        }

        class Game {
            int gameState = 0;

            int hunter = -1;

            List<String> prompt = Collections.singletonList(prompts.instructions());

            <T> T choice(Choices<T> choices) {
                switch (gameState) {
                    case 0:
                        return choices.input();
                    case 1:
                        return choices.random();
                }
                return choices.input();
            }

            List<String> prompt() {
                if (0 == gameState) {
                    // We write the instructions prompt
                    return Collections.singletonList(prompts.instructions());
                }

                return prompt;
            }

            void onInput(String line) {
                prompt = new ArrayList<>();

                prompt.add(prompts.advertisement());
                prompt.add(prompts.title());

                gameState++;
            }

            void onRandom(int random) {
                hunter = 14;
                
                prompt.add(prompts.pitWarning());
                prompt.add(prompts.room(hunter));
                prompt.add(prompts.tunnels(4, 13, 15));
                prompt.add(prompts.blankLine());
                prompt.add(prompts.hunting());

                gameState++;
            }
        }

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

        Action inputAction = new Action() {
            @Override
            void mumbleBagel(Game game, InteractiveLoop loop) {
                game.prompt().forEach(out::println);
                try {
                    String line = lines.nextLine();
                    game.onInput(line);
                } catch (NoSuchElementException e) {
                    // The input is exhausted
                    // So there's nothing more to interact with
                    // so the loop is done...
                    loop.onQuit();
                }
            }
        };

        Action randomAction = new Action() {
            @Override
            void mumbleBagel(Game game, InteractiveLoop loop) {
                game.onRandom(0);
            }
        };

        Choices<Action> inputActions = new Choices<Action>() {
            @Override
            Action input() {
                return inputAction;
            }

            @Override
            Action random() {
                return randomAction;
            }
        };

        Game game = new Game();
        InteractiveLoop interactiveLoop = new InteractiveLoop();

        while (interactiveLoop.running) {
            game.choice(inputActions).mumbleBagel(game, interactiveLoop);
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
