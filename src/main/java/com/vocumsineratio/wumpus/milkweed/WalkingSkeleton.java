package com.vocumsineratio.wumpus.milkweed;

import java.util.Collections;

/**
 * @author Danil Suits (danil@vast.com)
 */
class WalkingSkeleton implements Root.Core {
    int state = 0;
    String name = "Anonymous";

    @Override
    public <T> T action(
            Root.Actions.Quit<T> quit,
            Root.Actions.FlushLines<T> flushLines,
            Root.Actions.ReadOneLine<T> readOneLine) {
        switch (state) {
            case 0:
                return flushLines.flushLines(
                        Collections.singletonList(
                                "Who are you?"
                        )
                );
            case 1:
                return readOneLine.readOneLine();
            case 2:
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
        this.state = 999;
    }

    @Override
    public void onFlushLines() {
        this.state++;
    }

    @Override
    public void onLine(String line) {
        this.name = line;
        this.state++;
    }

    @Override
    public void onExhausted() {
        this.state = 888;
    }
}
