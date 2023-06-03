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

    interface Core {
        <T>
        T action(
                Quit<T> quit,
                FlushLines<T> flushLines,
                ReadOneLine<T> readOneLine
        );

        void onQuit();

        void onFlushLines();

        void onLine(String line);

        void onExhausted();
    }
}
