package me.timefall.timefall.time;

import java.util.ArrayList;
import java.util.Iterator;

public class Time
{
    private long daysPassed;
    private int hours, minutes, seconds, ticks;
    private ArrayList<TimedTask> timedTasks;

    public Time(long daysPassed, int hours, int minutes, int seconds, int ticks)
    {
        this.timedTasks = new ArrayList<>();

        this.daysPassed = daysPassed;
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
        this.ticks = ticks;
    }

    public void tickTime()
    {
        ticks++;

        if (ticks == 1)
        {
            seconds += 2;
            ticks = 0;

            if (seconds == 60)
            {
                minutes++;
                seconds = 0;

                if (minutes == 60)
                {
                    hours++;
                    minutes = 0;

                    if (hours == 24)
                    {
                        daysPassed++;
                        hours = 0;
                    }
                }
            }
        }

        Iterator<TimedTask> taskIterator = this.timedTasks.iterator();

        while (taskIterator.hasNext())
        {
            TimedTask timedTask = taskIterator.next();

            if (timedTask.done())
            {
                this.timedTasks.remove(timedTask);
                continue;
            }

            timedTask.tick();
        }
    }

    public long getDaysPassed()
    {
        return this.daysPassed;
    }

    public int getHours()
    {
        return this.hours;
    }

    public int getMinutes()
    {
        return this.minutes;
    }

    public int getSeconds()
    {
        return this.seconds;
    }

    public int getTicks()
    {
        return this.ticks;
    }

    public String getTimeString()
    {
        long day = daysPassed % 7;
        String dayString = day == 0 ? "Monday" : day == 1 ? "Tuesday" : day == 2 ? "Wednesday" : day == 3 ? "Thursday" : day == 4 ? "Friday" : day == 5 ? "Saturday" : "Sunday";

        return "Week " + (daysPassed / 7 + 1) + " (" + dayString + ") " + hours + ":" + minutes + ":" + seconds + " (" + ticks + ")";
    }

    //// Add tasks ////
    // Sync tasks //
    public void scheduleTask(int delay, Runnable task)
    {
        this.scheduleTask(delay, 1, 1, task);
    }

    public void scheduleTask(int delay, int interval, int repeats, Runnable task)
    {
        this.timedTasks.add(new TimedTask(delay, interval, repeats, task));
    }

    public void scheduleTask(int delay, int interval, Runnable task)
    {
        this.scheduleTask(delay, interval, -2, task);
    }

    // ASync tasks
    public void scheduleASyncTask(int delay, Runnable task)
    {
        this.scheduleASyncTask(delay, 1, 1, task);
    }

    public void scheduleASyncTask(int delay, int interval, int repeats, Runnable task)
    {
        this.timedTasks.add(new ASyncTimedTask(delay, interval, repeats, task));
    }

    public void scheduleASyncTask(int delay, int interval, Runnable task)
    {
        this.scheduleASyncTask(delay, interval, -2, task);
    }
}
