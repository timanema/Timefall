package me.betasterren.bsgame;

import me.betasterren.bsgame.display.Display;
import me.betasterren.bsgame.events.KeyHandler;

import java.awt.*;

public class BSGame {
    private static Display mainDisplay;

    private static KeyHandler keyHandler;

    public static void main(String args[]) {
        new BSGame();
        keyHandler = new KeyHandler();

        EventQueue.invokeLater(() -> mainDisplay = new Display("BS RPG", 480, 480 / 12 * 9));
    }

    public BSGame() {

    }

    public static Display getMainDisplay() {
        return mainDisplay;
    }

    public static KeyHandler getKeyHandler() {
        return keyHandler;
    }
}
