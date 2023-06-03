package com.vocumsineratio.wumpus.milkweed;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Random;

/**
 * @author Danil Suits (danil@vast.com)
 */
public class Wumpus {
    static class App {
        static void run(
                InputStream in,
                PrintStream out,
                Random random) {
            Runnable app = Root.app(
                    in,
                    out,
                    random
            );
            app.run();
        }
    }
    public static void main(String[] args) {
        Wumpus.App.run(
                System.in,
                System.out,
                new Random()
        );
    }
}
