package com.vocumsineratio.wumpus.milkweed;

/**
 * @author Danil Suits (danil@vast.com)
 */
class Loop {

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
