package me.timefall.timefall.events.conditions;

import me.timefall.timefall.entities.NPC;
import me.timefall.timefall.level.Vector;

public class Condition {
    private boolean truth;
    private int priority;
    private String conditionName;
    private Vector location;
    private float compareValue;
    private String xy;

    public Condition(String conditionName)
    {
        this.conditionName = conditionName;
    }

    public boolean checkCondition(NPC npc)
    {
        if (conditionName.equals("true"))
        {
            return true;
        }

        else if (conditionName.equals("false"))
        {
            return false;
        }

        else if (conditionName.equals("positionGreaterThan"))
        {
            return positionGreaterThan(npc.getLocation(), compareValue, xy);
        }

        else {
            return false;
        }
    }

    public boolean locationEqualsTile(Vector location, int[] tile)
    {
        if (location.getX() >= tile[0] && location.getX() <= tile[0])
        {
            if (location.getY() >= tile[1] && location.getY() <= tile[1])
            {
                return true;
            }
        }

        return false;
    }

    public boolean positionGreaterThan(Vector location, float compareValue, String xy)
    {
        float position;

        if (xy.equals("x"))
        {
            position = location.getX();
        } else {
            position = location.getY();
        }

        if (position > compareValue)
        {
            return true;
        }
        return false;
    }

    public boolean positionLessThan(float position, float compareValue)
    {
        if (position < compareValue)
        {
            return true;
        }
        return false;
    }

    public boolean getTruth()
    {
        return true;
    }

    public float getX()
    {
        return location.getX();
    }

    public float getY()
    {
        return location.getY();
    }
}