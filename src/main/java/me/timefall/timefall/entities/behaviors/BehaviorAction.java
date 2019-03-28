package me.timefall.timefall.entities.behaviors;

import me.timefall.timefall.entities.NPC;
import me.timefall.timefall.events.functions.GameFunctions;
import me.timefall.timefall.level.Direction;

public class BehaviorAction {
    private GameFunctions gameFunctions;
    private String actionName;
    private Direction direction;
    private float distance;
    public int index;
    public int maxIndex;

    public BehaviorAction()
    {
        this.gameFunctions = new GameFunctions();
        this.actionName = "noAction";
        index = 0;
        maxIndex = 1;
    }
    public BehaviorAction(String actionName)
    {
        this.gameFunctions = new GameFunctions();
        this.actionName = actionName;
        index = 0;
        maxIndex = 1;
    }
    public BehaviorAction(String actionName, int maxIndex)
    {
        this.gameFunctions = new GameFunctions();
        this.actionName = actionName;
        index = 0;
        this.maxIndex = maxIndex;
    }
    public BehaviorAction(String actionName, Direction direction)
    {
        this.gameFunctions = new GameFunctions();
        this.actionName = actionName;
        this.direction = direction;
        index = 0;
        maxIndex = 1;
    }
    public BehaviorAction(String actionName, Direction direction, int maxIndex)
    {
        this.gameFunctions = new GameFunctions();
        this.actionName = actionName;
        this.direction = direction;
        index = 0;
        this.maxIndex = maxIndex;
    }

    public BehaviorAction(String actionName, Direction direction, float distance)
    {
        this.gameFunctions = new GameFunctions();
        this.actionName = actionName;
        this.direction = direction;
        this.distance = distance;
        index = 0;
        maxIndex = 1;
    }

    public void execute(NPC npc)
    {
        if (actionName.equals("noAction")){

        } else if (actionName.equals("randomMove")){
            randomMove(npc);
            randomMove(npc);
        } else if (actionName.equals("randomWeightedMove"))
        {
            randomWeightedMove(npc, Direction.WEST);
        } else if (actionName.equals("move"))
        {
            move(npc, direction);
        }
        else if (actionName.equals("moveDistance"))
        {
            move(npc, direction, distance);
        }
    }

    public void randomMove(NPC npc)
    {
        move(npc, gameFunctions.getRandomDirection());
    }

    public void randomWeightedMove(NPC npc, Direction direction)
    {
        move(npc, gameFunctions.getWeightedRandomDirection(direction));
    }

    public void randomWeightedMove(NPC npc, Direction direction, int weight)
    {
        move(npc, gameFunctions.getWeightedRandomDirection(direction, weight));
    }

    public void move(NPC npc, Direction direction)
    {
        npc.move(direction);
    }

    public void move(NPC npc, Direction direction, float distance)
    {
        npc.move(direction, distance);
    }
}
