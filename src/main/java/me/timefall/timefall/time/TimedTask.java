package me.timefall.timefall.time;

public class TimedTask
{
    private int delay, interval, repeats;
    protected Runnable task;
    boolean done;

    public TimedTask(int delay, Runnable task)
    {
        this.delay = delay;
        this.interval = 0;
        this.repeats = 0;
        this.task = task;
        this.done = false;
    }

    public TimedTask(int delay, int interval, int repeats, Runnable task)
    {
        this.delay = delay;
        this.interval = interval;
        this.repeats = repeats;
        this.task = task;
        this.done = false;
    }

    public TimedTask(int delay, int repeats, Runnable task)
    {
        this.delay = delay;
        this.interval = 0;
        this.repeats = repeats;
        this.task = task;
        this.done = false;
    }

    public void tick()
    {
        if (delay == 0)
        {
            run();
        }
        else {
            delay--;
        }
    }

    protected void run()
    {
        this.task.run();
        if (repeats == 0)
        {
            this.done = true;
        }
        else {
            repeats--;
            if (interval > 0)
            {
                delay = interval;
            }
        }
    }
}
