package com.vocumsineratio.wumpus.grimm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author Danil Suits (danil@vast.com)
 */
class Hansel implements Rapunzel.Game {
    static Rapunzel.Game game(Rapunzel.Environment env) {
        return new Hansel();
    }

    enum State {
        START,
        ADS,
        ADS_WITH_INSTRUCTIONS,
        RESTORING,
        HUNTING,
        SHOOT_OR_MOVE,
        SHOOTING,
        MOVING,
        BLOCKED,
        PIT
    }

    static class Hunt {
        State state = State.START;
        int [] checkpoint;
        int [] current;
    }

    Hunt hunt = new Hunt();

    @Override
    public Iterable<String> prompt() {
        switch (hunt.state) {
            case START:
                return Collections.singletonList("INSTRUCTIONS (Y-N)");
            case ADS_WITH_INSTRUCTIONS: {
                List<String> lines = new ArrayList<>();
                lines.addAll(Corpus.instructions());
                lines.addAll(Corpus.advertisements());
                lines.addAll(Corpus.title());
                lines.addAll(view());
                lines.addAll(Corpus.shootOrMove());
                return lines;
            }
            case ADS: {
                List<String> lines = new ArrayList<>();
                lines.addAll(Corpus.advertisements());
                lines.addAll(Corpus.title());
                lines.addAll(view());
                lines.addAll(Corpus.shootOrMove());
                return lines;
            }
            case RESTORING: {
                List<String> lines = new ArrayList<>();
                lines.addAll(Corpus.title());
                lines.addAll(view());
                lines.addAll(Corpus.shootOrMove());
                return lines;
            }
            case HUNTING: {
                List<String> lines = new ArrayList<>();
                lines.addAll(view());
                lines.addAll(Corpus.shootOrMove());
                return lines;
            }
            case SHOOT_OR_MOVE: {
                return Corpus.shootOrMove();
            }
            case MOVING: {
                return Corpus.whereTo();
            }
            case BLOCKED: {
                List<String> lines = new ArrayList<>();
                lines.addAll(Corpus.notPossible());
                lines.addAll(Corpus.whereTo());
                return lines;
            }
            case SHOOTING: {
                return Corpus.howFar();
            }
            case PIT: {
                return Arrays.asList(
                        "YYYIIIIEEEE . . . FELL IN PIT",
                        "HA HA HA - YOU LOSE!",
                        "SAME SET-UP (Y-N)"
                );
            }
        }

        throw new IllegalStateException(hunt.state.toString());
    }

    @Override
    public void onLine(String line) {
        switch (hunt.state) {
            case START:
                onStart(line);
                break;
            case ADS_WITH_INSTRUCTIONS:
            case ADS:
            case RESTORING:
            case HUNTING:
            case SHOOT_OR_MOVE:
                onShootOrMove(line);
                break;
            case MOVING:
            case BLOCKED:
                onMoving(line);
                break;
            default:
                throw new IllegalStateException(hunt.state.name());
        }
    }

    void onShootOrMove(String line) {
        switch (line) {
            case "S":
                hunt.state = State.SHOOTING;
                break;
            case "M":
                hunt.state = State.MOVING;
                break;
            default:
                // retry
                hunt.state = State.SHOOT_OR_MOVE;
                break;
        }
    }

    void onMoving(String line) {
        if ("4".equals(line)) {
            hunt.state = State.PIT;
            return;
        }
        hunt.state = State.BLOCKED;
    }
    
    void onStart(String line) {
        if ("N".equals(line)) {
            // Opt Out.
            onStartWithAds();
        } else {
            onStartWithInstructions();
        }
    }

    void onStartWithAds() {
        onCheckpoint();
        onRestore();
        hunt.state = State.ADS;
    }
    void onStartWithInstructions() {
        onCheckpoint();
        onRestore();
        hunt.state = State.ADS_WITH_INSTRUCTIONS;
    }

    void onCheckpoint() {
        onCheckpoint(5, 14, 17, 4, 19, 16, 1);
    }

    void onCheckpoint(int ... checkpoint) {
        hunt.checkpoint = checkpoint;
    }

    void onRestore() {
        hunt.current = Arrays.copyOf(hunt.checkpoint, hunt.checkpoint.length);
    }

    Collection<String> view() {
        List<String> lines = new ArrayList<>();
        // Hazards can't be pre-computed, because the wumpus moves
        lines.add("I FEEL A DRAFT");

        // Room descriptions can be pre-computed, because the maze
        // doesn't change after we start hunting.
        lines.add("YOU ARE IN ROOM " + hunt.current[1]);
        lines.add("TUNNELS LEAD TO 4 13 15");
        lines.add("");
        return lines;
    }
}
class Corpus {
    static Collection<String> instructions() {
        return Collections.singletonList("TODO: Corpus::instructions");
    }

    static Collection<String> advertisements() {
        return Collections.singletonList("TODO: Corpus::advertisements");
    }

    static Collection<String> title() {
        return Collections.singletonList("HUNT THE WUMPUS");
    }

    static Collection<String> shootOrMove() {
        return Collections.singletonList("SHOOT OR MOVE (S-M)");
    }

    static Collection<String> whereTo() {
        return Collections.singletonList("WHERE TO");
    }

    static Collection<String> notPossible() {
        return Collections.singletonList("NOT POSSIBLE");
    }

    static Collection<String> howFar() {
        return Collections.singletonList("NO. OF ROOMS(1-5)");
    }
}
