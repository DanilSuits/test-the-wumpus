package com.vocumsineratio.wumpus.grimm;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * @author Danil Suits (danil@vast.com)
 */
class CLI {

    interface Game {
        enum State { QUIT, READ_LINE }

        State state();
        Iterable<String> prompt();

        void onLine(String line);
        void onExhausted();
    }

    static void interactiveShell(
            Map<String, String> environment,
            InputStream in,
            PrintStream out) {

        Game game = Rapunzel.game(environment::get);

        Scanner lines = new Scanner(in);
        while (true) {
            switch(game.state()) {
                case QUIT:
                    return;
                case READ_LINE:
                    game.prompt().forEach(out::println);
                    try {
                        String line = lines.nextLine();
                        game.onLine(line);
                    } catch (NoSuchElementException e) {
                        // The input is exhausted
                        // So there's nothing more to interact with
                        // so the loop is done...
                        game.onExhausted();
                    }
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
