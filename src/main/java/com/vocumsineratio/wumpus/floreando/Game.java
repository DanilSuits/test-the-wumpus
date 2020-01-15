package com.vocumsineratio.wumpus.floreando;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Danil Suits (danil@vast.com)
 */
public class Game {
    int gameState = 0;

    public <T> T effect(T line, T diceRoll) {
        if (1 == gameState) return diceRoll;
        return line;
    }

    public List<String> prompt() {
        Prompts prompts = new Prompts();

        if (0 == gameState)
        return Collections.singletonList(prompts.instructions());

        List<String> prompt = new ArrayList<>();

        int hunter = 14;

        prompt.addAll(prompts.advertisement());
        prompt.add(prompts.title());
        prompt.add(prompts.pitWarning());
        prompt.add(prompts.room(hunter));
        prompt.add(prompts.tunnels(4, 13, 15));
        prompt.add(prompts.blankLine());
        prompt.add(prompts.hunting());

        return prompt;
    }

    public void onLine(String n) {
        //TODO: To change body of created methods use File | Settings | File Templates.
        gameState++;
    }

    public void onDiceRoll(int i) {
        //TODO: To change body of created methods use File | Settings | File Templates.
        gameState++;
    }

    class Prompts {
        String instructions() {
            return "INSTRUCTIONS (Y-N)";
        }

        String hunting() {
            return "SHOOT OR MOVE (S-M)";
        }

        String title() {
            return "HUNT THE WUMPUS";
        }

        String pitWarning() {
            return "I FEEL A DRAFT";
        }

        String room(int roomNumber) {
            return "YOU ARE IN ROOM 14";
        }

        String tunnels(int first, int second, int third) {
            return "TUNNELS LEAD TO 4 13 15";
        }

        List<String> advertisement() {
            List<String> advertisement = new ArrayList<>();
            advertisement.add(blankLine());
            advertisement.addAll(Arrays.asList((
                    "     ATTENTION ALL WUMPUS LOVERS!!!\n" +
                    "     THERE ARE NOW TWO ADDITIONS TO THE WUMPUS FAMILY\n" +
                    " OF PROGRAMS.\n" +
                    "\n" +
                    "     WUMP2:  SOME DIFFERENT CAVE ARRANGEMENTS\n" +
                    "     WUMP3:  DIFFERENT HAZARDS\n").split("\n")));
                    ;

            advertisement.add(blankLine());
            return advertisement;

        }

        String blankLine() {
            return "";
        }
    }
}
