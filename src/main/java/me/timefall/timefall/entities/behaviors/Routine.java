package me.timefall.timefall.entities.behaviors;

import me.timefall.timefall.entities.NPC;
import me.timefall.timefall.events.conditions.Condition;
import me.timefall.timefall.level.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

public class Routine {
    private String routineName;
    private List<BehaviorCondition> behaviorConditions;

    public Routine()
    {
        this.routineName = "NoRoutine";

        behaviorConditions = new ArrayList<>();
        Behavior behavior = new Behavior();

        BehaviorCondition behaviorCondition = new BehaviorCondition(behavior, new Condition("true"), 1);
        behaviorConditions.add(behaviorCondition);
    }

    public Routine(String routineName)
    {
        this.routineName = routineName;

        behaviorConditions = new ArrayList<>();
    }

    public void giveCommand(Behavior behavior)
    {
        behavior.isCommand = true;
        addBehavior(behavior, new Condition("true"), -1);
    }

    public void addBehavior(Behavior behavior)
    {
        addBehavior(behavior, new Condition("true"), 0);
    }

    public void addBehavior(Behavior behavior, Condition condition, int priority)
    {
        BehaviorCondition behaviorCondition = new BehaviorCondition(behavior, condition, priority);
        behaviorConditions.add(behaviorCondition);
        behaviorConditions.sort(Comparator.comparing(BehaviorCondition::getPriority));
    }

    public void initRoutines()
    {
        List<Routine> routines = new ArrayList<Routine>();
        //NoRoutine
        Routine noroutine = new Routine();
        routines.add(noroutine);
        //Idle
        Routine idle = new Routine();
        routines.add(idle);
    }

    public Behavior getBehavior(NPC npc)
    {
        Behavior firstBehavior = behaviorConditions.get(0).getBehavior();
        if (firstBehavior.isCommand && firstBehavior.remove)
        {
            behaviorConditions.remove(0);
        }

        for (BehaviorCondition behaviorCondition : behaviorConditions)
        {
            if (behaviorCondition.getCondition().checkCondition(npc))
            {
                return behaviorCondition.getBehavior();
            }
        }

        return null;
    }

    public String getRoutineName()
    {
        return routineName;
    }
}
