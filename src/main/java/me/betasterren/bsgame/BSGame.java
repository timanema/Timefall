package me.betasterren.bsgame;

import me.betasterren.bsgame.display.Display;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class BSGame {
    private Display mainDisplay;
    private Set<Object> listeners;

    public static void main(String args[]) {
        new BSGame();
    }

    public BSGame() {
        listeners = new HashSet<>();

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                mainDisplay = new Display("BS RPG", 500, 500);
            }
        });
    }

    public void registerListener(Object listener) {
        listeners.add(listener);
    }
}
