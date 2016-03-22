package me.betasterren.bsgame.game;

public class ThreadManager {
    private static Thread gameThread;

    public ThreadManager() {
        initThreads();
    }

    private void initThreads() {
        gameThread = new Thread(new GameThread());

        gameThread.start();
    }
}
