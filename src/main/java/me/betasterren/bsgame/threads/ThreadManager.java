package me.betasterren.bsgame.threads;

import me.betasterren.bsgame.threads.GameThread;

public class ThreadManager {
    private static Thread gameThread;

    public ThreadManager() {
        initThreads();
    }

    private void initThreads() {
        gameThread = new Thread(new GameThread());

        gameThread.start();
    }

    public static Thread getGameThread() {
        return gameThread;
    }
}
