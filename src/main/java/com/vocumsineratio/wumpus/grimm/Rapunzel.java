package com.vocumsineratio.wumpus.grimm;

/**
 * @author Danil Suits (danil@vast.com)
 */
class Rapunzel implements CLI.Game {
    interface Environment {
        String get(String key);
    }

    interface Game {
        Iterable<String> prompt();
        void onLine(String line);
    }

    static CLI.Game game(Environment env) {
        Rapunzel.Game core = Hansel.game(env);
        return new Rapunzel(core);
    }

    private final Rapunzel.Game core;
    private State state = State.READ_LINE;

    Rapunzel(Game core) {
        this.core = core;
    }

    @Override
    public State state() {
        return state;
    }

    @Override
    public Iterable<String> prompt() {
        return core.prompt();
    }

    @Override
    public void onLine(String line) {
        core.onLine(line);
    }

    @Override
    public void onExhausted() {
        this.state = State.QUIT;
    }
}
