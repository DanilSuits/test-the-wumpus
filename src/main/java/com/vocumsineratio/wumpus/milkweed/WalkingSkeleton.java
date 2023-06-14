package com.vocumsineratio.wumpus.milkweed;

import java.util.Collections;

/**
 * @author Danil Suits (danil@vast.com)
 */
class WalkingSkeleton{
    static Actions.Core<Void> start() {
        return new FSM.Facade(
            new AskWho()
        )               ;
    }

    static final FSM.State endOfGame = new FSM.EndOfGame();

    static class HelloWho extends FSM.EndOfGame {
        final String name;

        HelloWho(String name) {
            this.name = name;
        }

        @Override
        public FSM.State onFlushLines() {
            return WalkingSkeleton.endOfGame;
        }

        @Override
        public <T> T action(Actions.Quit<T> quit, Actions.FlushLines<T> flushLines, Actions.ReadOneLine<T> readOneLine) {
            return flushLines.flushLines(
                    Collections.singletonList(
                            "Hello " + this.name
                    )
            );
        }
    }

    static class ReadName extends FSM.EndOfGame {
        @Override
        public <T> T action(Actions.Quit<T> quit, Actions.FlushLines<T> flushLines, Actions.ReadOneLine<T> readOneLine) {
            return readOneLine.readOneLine();
        }

        @Override
        public FSM.State onLine(String line) {
            return new HelloWho(line);
        }

        @Override
        public FSM.State onExhausted() {
            return endOfGame;
        }
    }

    static class AskWho extends FSM.EndOfGame {
        @Override
        public <T> T action(Actions.Quit<T> quit, Actions.FlushLines<T> flushLines, Actions.ReadOneLine<T> readOneLine) {
            return flushLines.flushLines(
                    Collections.singletonList(
                            "Who are you?"
                    )
            );
        }

        @Override
        public FSM.State onFlushLines() {
            return new ReadName();
        }
    }
}
