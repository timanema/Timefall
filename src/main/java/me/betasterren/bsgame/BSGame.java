package me.betasterren.bsgame;

import me.betasterren.bsgame.display.Display;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class BSGame {
    private static Display mainDisplay;
    private Set<Object> listeners;

    public static void main(String args[]) {
        new BSGame();
        EventQueue.invokeLater(() -> mainDisplay = new Display("BS RPG", 500, 500));

    }

    public BSGame() {
        listeners = new HashSet<>();

    }

    public void registerListener(Object listener) {
        listeners.add(listener);
    }

    public static Display getMainDisplay() {
        return mainDisplay;
    }
}
