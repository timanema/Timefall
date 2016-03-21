package me.betasterren.bsgame.game;

import javax.swing.*;

public class ThreadManager {
    private JPanel gamePanel;
    private static Thread gameThread;


    public ThreadManager(JPanel gamePanel) {
        this.gamePanel = gamePanel;

        initThreads();
    }

    private void initThreads() {
        gameThread = new Thread(new GameThread());

        gameThread.start();
    }
}
