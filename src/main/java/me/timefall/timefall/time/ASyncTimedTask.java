package me.timefall.timefall.time;

public class ASyncTimedTask extends TimedTask
{
    public ASyncTimedTask(int delay, Runnable task)
    {
        super(delay, task);
    }

    public ASyncTimedTask(int delay, int interval, int repeats, Runnable task)
    {
        super(delay, interval, repeats, task);
    }

    public ASyncTimedTask(int delay, int interval, Runnable task)
    {
        super(delay, interval, task);
    }

    @Override
    protected void run()
    {
        new Thread(task).start();
    }
}
