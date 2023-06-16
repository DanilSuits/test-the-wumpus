package com.vocumsineratio.wumpus.milkweed;

/**
 * @author Danil Suits (danil@vast.com)
 */
interface Actions {
    interface Quit<T> {
        T quit();
    }

    interface FlushLines<T> {
        T flushLines(Iterable<String> lines);
    }

    interface ReadOneLine<T> {
        T readOneLine();
    }

    interface Core<S> {
        <T>
        T action(
                Quit<? extends T> quit,
                FlushLines<? extends T> flushLines,
                ReadOneLine<? extends T> readOneLine
        );

        S onQuit();

        S onFlushLines();

        S onLine(String line);

        S onExhausted();
    }
}
