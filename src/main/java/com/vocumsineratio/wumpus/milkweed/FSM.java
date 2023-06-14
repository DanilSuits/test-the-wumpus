package com.vocumsineratio.wumpus.milkweed;

/**
 * @author Danil Suits (danil@vast.com)
 */
class FSM {
    interface State extends Actions.Core<FSM.State> {}

    static class Facade implements Actions.Core<Void> {
        FSM.State currentState;

        Facade(FSM.State initialState) {
            this.currentState = initialState;
        }

        @Override
        public <T> T action(
                Actions.Quit<T> quit,
                Actions.FlushLines<T> flushLines,
                Actions.ReadOneLine<T> readOneLine) {
            return currentState.action(
                    quit,
                    flushLines,
                    readOneLine
            )                  ;
        }

        @Override
        public Void onQuit() {
            this.currentState = this.currentState.onQuit();
            return null;
        }

        @Override
        public Void onFlushLines() {
            this.currentState = this.currentState.onFlushLines();
            return null;
        }

        @Override
        public Void onLine(String line) {
            this.currentState = this.currentState.onLine(line);
            return null;
        }

        @Override
        public Void onExhausted() {
            this.currentState = this.currentState.onExhausted();
            return null;
        }
    }

    static class EndOfGame implements State {
        @Override
        public <T> T action(Actions.Quit<T> quit, Actions.FlushLines<T> flushLines, Actions.ReadOneLine<T> readOneLine) {
            return quit.quit();
        }

        @Override
        public State onQuit() {
            return this;
        }

        @Override
        public State onFlushLines() {
            return this;
        }

        @Override
        public State onLine(String line) {
            return this;
        }

        @Override
        public State onExhausted() {
            return this;
        }
    }
}
