package me.timefall.timefall.time;

import java.util.ArrayList;
import java.util.Iterator;

public class Time
{
    private long daysPassed;
    private int hours, minutes, seconds, ticks;
    private ArrayList<TimedTask> scheduledTasks;

    public Time(long daysPassed, int hours, int minutes, int seconds, int ticks)
    {
        this.scheduledTasks = new ArrayList<>();

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

        for (int i = 0; i < scheduledTasks.size(); i++)
        {
            scheduledTasks.get(i).tick();

            if (scheduledTasks.get(i).done)
            {
                scheduledTasks.remove(i);
            }
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

    public void scheduleTask(TimedTask task)
    {
        scheduledTasks.add(task);
    }

    public void scheduleTask(int delay, Runnable task)
    {
        this.scheduleTask(delay, 0, 0, task);
    }

    public void scheduleTask(int delay, int repeats, Runnable task)
    {
        this.scheduleTask(delay, 0, repeats, task);
    }

    public void scheduleTask(int delay, int interval, int repeats, Runnable task)
    {
        this.scheduledTasks.add(new TimedTask(delay, interval, repeats, task));
    }
}
