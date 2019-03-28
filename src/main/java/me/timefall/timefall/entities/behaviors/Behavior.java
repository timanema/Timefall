package me.timefall.timefall.entities.behaviors;

import me.timefall.timefall.entities.NPC;
import me.timefall.timefall.entities.PathTile;
import me.timefall.timefall.events.conditions.Condition;
import me.timefall.timefall.level.Direction;

import java.util.ArrayList;
import java.util.List;

import static me.timefall.timefall.level.Direction.NORTH;

public class Behavior {
    private String behaviorName;
    public boolean isCommand;
    public boolean remove;
    private List<BehaviorAction> behaviorActions;
    private int actionIndex;

    public Behavior()
    {
        this.behaviorName = "NoBehavior";

        behaviorActions = new ArrayList<>();
        BehaviorAction behaviorAction = new BehaviorAction();
        this.behaviorActions.add(behaviorAction);

        actionIndex = 0;
    }

    public Behavior(String behaviorName)
    {
        this.behaviorName = behaviorName;
        this.isCommand = false;

        behaviorActions = new ArrayList<>();

        actionIndex = 0;
    }

    public Behavior copy()
    {
        Behavior behavior = new Behavior(behaviorName);
        behavior.setBehaviorActions(behaviorActions);
        return behavior;
    }

    public void addAction()
    {
        behaviorActions.add(new BehaviorAction());
    }

    public void addAction (int index)
    {
        behaviorActions.add(index, new BehaviorAction());
    }

    public void addAction (BehaviorAction behaviorAction, int index)
    {
        behaviorActions.add(index, behaviorAction);
    }

    public void addAction(BehaviorAction behaviorAction)
    {
        behaviorActions.add(behaviorAction);
    }

    public BehaviorAction getNextAction()
    {
        BehaviorAction behaviorAction =  behaviorActions.get(actionIndex);
        behaviorAction.index += 1;

        if (behaviorAction.index == behaviorAction.maxIndex)
        {
            behaviorAction.index = 0;
            actionIndex++;
            if (actionIndex == behaviorActions.size())
            {
                if (isCommand)
                {
                    remove = true;
                }
                actionIndex = 0;
            }
        }

        return behaviorAction;
    }

    public String getBehaviorName()
    {
        return behaviorName;
    }

    public void setBehaviorActions(List<BehaviorAction> behaviorActions)
    {
        this.behaviorActions = behaviorActions;
    }
}
