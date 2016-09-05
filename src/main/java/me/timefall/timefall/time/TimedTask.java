package me.timefall.timefall.time;

public class TimedTask
{
    private int delay, interval, repeats, alive;
    protected Runnable task;

    public TimedTask(int delay, Runnable task)
    {
        this.delay = delay;
        this.interval = 1;
        this.repeats = 1;
        this.task = task;
    }

    public TimedTask(int delay, int interval, int repeats, Runnable task)
    {
        this.delay = delay;
        this.interval = interval;
        this.repeats = repeats;
        this.task = task;
    }

    public TimedTask(int delay, int interval, Runnable task)
    {
        this.delay = delay;
        this.interval = interval;
        this.repeats = -2;
        this.task = task;
    }

    public void tick()
    {
        if (repeats <= 0 && repeats != -2)
        {
            repeats = -1;
            return;
        }

        this.alive++;

        if (alive >= (delay == -1 ? interval : delay))
        {
            repeats -= (repeats == -2 ? 0 : 1);
            delay = -1;
            alive = 0;
            this.run();
        }
    }

    protected void run()
    {
        this.task.run();
    }

    public boolean done()
    {
        return this.repeats == -1;
    }
}
