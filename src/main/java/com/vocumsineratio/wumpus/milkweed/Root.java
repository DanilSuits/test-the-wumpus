package com.vocumsineratio.wumpus.milkweed;

/**
 * @author Danil Suits (danil@vast.com)
 */
class Root {
    static Runnable app(Actions.Core<?> core, Actions.ReadOneLine<Runnable> readOneLine, Actions.FlushLines<Runnable> flushLines) {
        Loop loop = new Loop(true);
        Actions.Quit<Runnable> quit = quit(core, loop);
        return new GameLoop(
                core,
                loop,
                quit,
                flushLines,
                readOneLine
        );
    }

    private static Actions.Quit<Runnable> quit(Actions.Core<?> core, Loop loop) {
        Runnable quit = () -> {
            loop.quit();
            core.onQuit();
        };

        return () -> quit;
    }
}
