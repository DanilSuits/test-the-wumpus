package com.vocumsineratio.wumpus.milkweed;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Collections;
import java.util.Random;

/**
 * @author Danil Suits (danil@vast.com)
 */
class Root {
    static Runnable app(
            InputStream in,
            PrintStream out,
            Random random){

        Example core = new Example();
        Loop loop = new Loop(true);
        Output output = new Output(out);
        return new Root.GameLoop(core, loop, output);

    }

    interface Actions {
        interface Quit<T> {
            T quit();
        }

        interface FlushLines<T> {
            T flushLines(Iterable<String> lines);
        }
    }

    static class Loop {

        boolean running;

        Loop(boolean running) {
            this.running = running;
        }

        boolean running() {
            return this.running;
        }

        void quit() {
            this.running = false;
        }
    }

    static class Output {
           final PrintStream out;

        Output(PrintStream out) {
            this.out = out;
        }

        void flushLines(Iterable<String> lines) {
            lines.forEach(out::println);
            out.flush();
        }
    }
    interface Core {
        <T>
        T action(
                Actions.Quit<T> quit,
                Actions.FlushLines<T> flushLines
        );

        void onQuit();
        void onFlushLines();
    }

    static class Example implements Core {
        int state = 0;
        String name = "Anonymous";

        @Override
        public <T> T action(
                Actions.Quit<T> quit,
                Actions.FlushLines<T> flushLines) {
            switch (state) {
                case 0:
                    return flushLines.flushLines(
                            Collections.singletonList(
                                    "Who are you?"
                            )
                    );
                case 1:
                    return flushLines.flushLines(
                            Collections.singletonList(
                                    "Hello " + this.name
                            )
                    );
                default:
            }
            return quit.quit();
        }

        @Override
        public void onQuit() {
            // NoOp
        }

        @Override
        public void onFlushLines() {
            this.state++;
        }
    }

    static class GameLoop implements Runnable {
        final Loop loop;
        final Core core;

        final Output output;

        GameLoop(Core core, Loop loop, Output output) {
            this.core = core;
            this.loop = loop;
            this.output = output;
        }

        @Override
        public void run() {
            while(running()) {
                Runnable action = this.action();
                action.run();
            }
        }

        private boolean running() {
            return loop.running();
        }

        Runnable action() {
            return core.action(
                    this::quit,
                    this::flushLines
            );
        }

        Runnable quit() {
            return new Quit();
        }

        class Quit implements Runnable {
            @Override
            public void run() {
                Root.GameLoop.this.loop.quit();
                Root.GameLoop.this.core.onQuit();
            }
        }

        Runnable flushLines(Iterable<String> lines) {
            return new FlushLines(lines);
        }

        class FlushLines implements Runnable {
            final Iterable<String> lines;

            FlushLines(Iterable<String> lines) {
                this.lines = lines;
            }

            @Override
            public void run() {
                GameLoop.this.output.flushLines(lines);
                GameLoop.this.core.onFlushLines();
            }
        }
    }
}
