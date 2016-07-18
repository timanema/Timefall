package me.timefall.timefall.threads;

public class ThreadManager
{
    private static Thread gameThread;

    public ThreadManager()
    {
        initThreads();
    }

    private void initThreads()
    {
        gameThread = new Thread(new GameThread());

        gameThread.start();
    }

    public static Thread getGameThread()
    {
        return gameThread;
    }
}
