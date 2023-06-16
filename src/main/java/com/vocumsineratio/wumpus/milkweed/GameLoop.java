package com.vocumsineratio.wumpus.milkweed;

/**
 * @author Danil Suits (danil@vast.com)
 */
class GameLoop implements Runnable {
    final Loop loop;
    final Actions.Core<?> core;
    final Actions.Quit<? extends Runnable> quit;
    final Actions.FlushLines<? extends Runnable> flushLines;
    final Actions.ReadOneLine<? extends Runnable> readOneLine;

    GameLoop(
            Actions.Core<?> core,
            Loop loop,
            Actions.Quit<? extends Runnable> quit,
            Actions.FlushLines<? extends Runnable> flushLines,
            Actions.ReadOneLine<? extends Runnable> readOneLine) {
        this.core = core;
        this.loop = loop;
        this.quit = quit;
        this.flushLines = flushLines;
        this.readOneLine = readOneLine;
    }

    @Override
    public void run() {
        while (running()) {
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
