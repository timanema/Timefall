package me.betasterren.bsgame;

import me.betasterren.bsgame.display.Display;

import java.awt.*;

public class BSGame {
    private Display mainDisplay;

    public static void main(String args[]) {
        new BSGame();
    }

    public BSGame() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                mainDisplay = new Display("BS RPG", 500, 500);
            }
        });
    }
}
