package com.vocumsineratio.wumpus.milkweed;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;

/**
 * @author Danil Suits (danil@vast.com)
 */
class Root {
    static Runnable app(
            InputStream in,
            PrintStream out,
            Random random){

        Actions.Core<?> core = WalkingSkeleton.start();
        return app(in, out, core);

    }

    private static Runnable app(InputStream in, PrintStream out, Actions.Core<?> core) {
        Loop loop = new Loop(true);
        Output output = new Output(out);
        Scanner scanner = new Scanner(in);

        Actions.Quit<Runnable> quit = quit(core, loop);
        Actions.ReadOneLine<Runnable> readOneLine = readOneLine(core, scanner);
        Actions.FlushLines<Runnable> flushLines = flushLines(core, output);

        return new GameLoop(core, loop, quit, flushLines, readOneLine);
    }

    private static Actions.Quit<Runnable> quit(Actions.Core<?> core, Loop loop) {
        Runnable quit = () -> {
            loop.quit();
            core.onQuit();
        };

        return () -> quit;
    }

    private static Actions.ReadOneLine<Runnable> readOneLine(Actions.Core<?> core, Scanner scanner) {
        Root.ReadOneLine readOneLine = new ReadOneLine(scanner, core);

        return readOneLine(readOneLine);
    }

    private static Actions.ReadOneLine<Runnable> readOneLine(Root.ReadOneLine readOneLine) {
        return () -> readOneLine;
    }

    private static Actions.FlushLines<Runnable> flushLines(Actions.Core<?> core, Output output) {
        return new Actions.FlushLines<Runnable>() {
            @Override
            public Runnable flushLines(Iterable<String> lines) {
                Runnable flushLines = () -> {
                    output.flushLines(lines);
                    core.onFlushLines();
                };

                return flushLines;
            }
        };
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

    static class ReadOneLine implements Runnable {
        final Scanner scanner;
        final Actions.Core<?> core;

        ReadOneLine(Scanner scanner, Actions.Core<?> core) {
            this.scanner = scanner;
            this.core = core;
        }

        @Override
        public void run() {
            try {
                String line = scanner.nextLine();
                core.onLine(line);
            } catch (NoSuchElementException e) {
                core.onExhausted();
            }
        }
    }

    static class GameLoop implements Runnable {
        final Loop loop;
        final Actions.Core<?> core;
        final Actions.Quit<Runnable> quit;
        final Actions.FlushLines<Runnable> flushLines;
        final Actions.ReadOneLine<Runnable> readOneLine;

        GameLoop(
                Actions.Core<?> core,
                Loop loop,
                Actions.Quit<Runnable> quit,
                Actions.FlushLines<Runnable> flushLines,
                Actions.ReadOneLine<Runnable> readOneLine) {
            this.core = core;
            this.loop = loop;
            this.quit = quit;
            this.flushLines = flushLines;
            this.readOneLine = readOneLine;
        }

        @Override
        public void run() {
            while(running()) {
                step();
            }
        }

        private void step() {
            Runnable action = this.action();
            action.run();
        }

        private boolean running() {
            return loop.running();
        }

        Runnable action() {
            return core.action(
                    this.quit,
                    this.flushLines,
                    this.readOneLine
            );
        }
    }
}
