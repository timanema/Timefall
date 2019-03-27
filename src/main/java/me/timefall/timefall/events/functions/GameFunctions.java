package me.timefall.timefall.events.functions;
import me.timefall.timefall.level.Direction;

import java.util.Random;

public class GameFunctions {
    public Direction getRandomDirection()
    {
        Random random = new Random();
        int r = random.nextInt(8);

        switch (r) {
            case 0:
                return Direction.NORTH;
            case 1:
                return Direction.NORTHEAST;
            case 2:
                return Direction.NORTHWEST;
            case 3:
                return Direction.EAST;
            case 4:
                return Direction.SOUTH;
            case 5:
                return Direction.SOUTHEAST;
            case 6:
                return Direction.SOUTHWEST;
            case 7:
                return Direction.WEST;
            default:
                return Direction.NORTH;
        }
    }

    public Direction getWeightedRandomDirection(Direction direction)
    {
        return getWeightedRandomDirection(direction, 20);
    }

    public Direction getWeightedRandomDirection(Direction direction, int weight)
    {
        Random random = new Random();
        int r = random.nextInt(weight);
        if (r > 7)
        {
            return direction;
        }
        else {
            switch (r) {
                case 0:
                    return Direction.NORTH;
                case 1:
                    return Direction.NORTHEAST;
                case 2:
                    return Direction.NORTHWEST;
                case 3:
                    return Direction.EAST;
                case 4:
                    return Direction.SOUTH;
                case 5:
                    return Direction.SOUTHEAST;
                case 6:
                    return Direction.SOUTHWEST;
                case 7:
                    return Direction.WEST;
                default:
                    return Direction.NORTH;
            }
        }
    }
}
