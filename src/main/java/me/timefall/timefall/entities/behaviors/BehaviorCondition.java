package me.timefall.timefall.entities.behaviors;

import me.timefall.timefall.events.conditions.Condition;

public class BehaviorCondition {
    private boolean truth;
    private int priority;
    private Behavior behavior;
    private Condition condition;

    public BehaviorCondition(Behavior behavior, Condition condition, int priority)
    {
        this.behavior = behavior;
        this.condition = condition;
        this.priority = priority;
    }

    public boolean getTruth()
    {
        return truth;
    }
    public int getPriority()
    {
        return priority;
    }
    public Behavior getBehavior()
    {
        return behavior;
    }
    public Condition getCondition()
    {
        return condition;
    }

}
