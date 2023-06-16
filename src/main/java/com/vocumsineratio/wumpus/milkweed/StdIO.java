package com.vocumsineratio.wumpus.milkweed;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * @author Danil Suits (danil@vast.com)
 */
class StdIO {
    static void run(Actions.Core<?> core) {
        Runnable app = app(core);
        app.run();
    }

    static Runnable app(Actions.Core<?> core) {
        return StdIO.app(
                System.in,
                System.out,
                core
        )           ;
    }

    static Runnable app(InputStream in, PrintStream out, Actions.Core<?> core) {
        Output output = new Output(out);
        Scanner scanner = new Scanner(in);

        Actions.ReadOneLine<Runnable> readOneLine = readOneLine(core, scanner);
        Actions.FlushLines<Runnable> flushLines = flushLines(core, output);

        return Root.app(core, readOneLine, flushLines);
    }

    private static Actions.ReadOneLine<Runnable> readOneLine(Actions.Core<?> core, Scanner scanner) {
        ReadOneLine readOneLine = new ReadOneLine(scanner, core);

        return readOneLine(readOneLine);
    }

    private static Actions.ReadOneLine<Runnable> readOneLine(ReadOneLine readOneLine) {
        return () -> readOneLine;
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
}
