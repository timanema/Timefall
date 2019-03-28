package me.timefall.timefall.entities;

import me.timefall.timefall.Timefall;
import me.timefall.timefall.entities.behaviors.Behavior;
import me.timefall.timefall.entities.behaviors.BehaviorAction;
import me.timefall.timefall.entities.behaviors.BehaviorCondition;
import me.timefall.timefall.level.Direction;
import me.timefall.timefall.level.Vector;
import me.timefall.timefall.level.tiles.base.MapObject;

import java.awt.*;
import java.lang.reflect.Array;
import java.nio.file.Path;
import java.util.*;

public class Pathfinding {
    private PathTile target;
    private PathTile location;

    private int[][] worldGrid;
    private TreeMap<int[], PathTile> allTiles;

    private ArrayList<PathTile> finalPath;

    public Pathfinding(Vector target, Vector location)
    {
        this.worldGrid = Timefall.getTileManager().getCurrentWorld().getWorldGrid();

        allTiles = new TreeMap<>(Arrays::compare);

        for (int i = 0; i < worldGrid.length; i++)
        {
            for (int j = 0; j < worldGrid[i].length; j++)
            {
                MapObject[] mapObjects = Timefall.getTileManager().getMapObjectsByLoc(i, j);
                boolean isTraversable = true;

                for (MapObject mapObject : mapObjects)
                {
                    if (mapObject.isSolid())
                    {
                        isTraversable = false;
                        break;
                    }
                }

                if (isTraversable)
                {
                    allTiles.put(new int[]{i, j}, new PathTile(i, j));
                }
            }
        }

        this.target = allTiles.get(new int[]{(int) target.getX(), (int) target.getY()});
        this.location = allTiles.get(new int[]{(int) location.getX(), (int) location.getY()});

        calculatePath();
    }

    private void reconstructPath(PathTile current)
    {
        ArrayList<PathTile> totalPath = new ArrayList<>();
        totalPath.add(current);
        current = current.cameFrom;
        while (!(current.x == location.x && current.y == location.y))
        {
            totalPath.add(current);
            current = current.cameFrom;
        }
        Collections.reverse(totalPath);
        this.finalPath = totalPath;
    }

    private void calculatePath()
    {
        ArrayList<PathTile> closedSet = new ArrayList<>();

        ArrayList<PathTile> openSet = new ArrayList<>();
        openSet.add(location);

        location.gScore = 0.0;

        location.fScore = diagonalDistance(location, target);

        while (!openSet.isEmpty())
        {
            openSet.sort(Comparator.comparing(PathTile::getfScore));
            PathTile current = openSet.get(0);
            if (current.x == target.x && current.y == target.y)
            {
                reconstructPath(current);
            }

            openSet.remove(current);
            closedSet.add(current);

            for (PathTile neighbor : getNeighbors(current))
            {
                if (closedSet.contains(neighbor))
                {
                    continue;
                }

                double tentativegScore = current.gScore + diagonalDistance(current, neighbor);

                if (!openSet.contains(neighbor))
                {
                    openSet.add(neighbor);
                }
                else if (tentativegScore >= neighbor.gScore)
                {
                    continue;
                }

                neighbor.setCameFrom(current);
                neighbor.gScore = tentativegScore;
                neighbor.fScore = neighbor.gScore + diagonalDistance(neighbor, target);
            }
        }
    }

    public Behavior getBehaviorFromPath (ArrayList<PathTile> path, NPC npc)
    {
        Behavior behavior = new Behavior("path");
        float speedModifier = npc.getSpeedModifier();

        for (int i = 0; i < path.size() - 1; i++)
        {
            PathTile current = path.get(i);
            PathTile next = path.get(i+1);
            Direction directionToNext = getDirectionFromChange(new int[] {next.x - current.x, next.y - current.y});
            double distanceToNext = diagonalDistance(current, next);

            double directionMovement = Math.sqrt(Math.pow(directionToNext.getxChange(), 2) + Math.pow(directionToNext.getyChange(), 2)) / 16 * speedModifier;
            //System.out.println(directionMovement);
            //System.out.println(distanceToNext);

            int numberMoves = (int) Math.round(distanceToNext / directionMovement);
            //System.out.println(numberMoves);
            behavior.addAction(new BehaviorAction("move", directionToNext, numberMoves));
        }

        return behavior;
    }

    public Direction getDirectionFromChange(int[] change)
    {
        if (change[0] > 0)
        {
            if (change[1] > 0)
            {
                return Direction.SOUTHEAST;
            }
            else if (change[1] < 0)
            {
                return Direction.NORTHEAST;
            }
            else {
                return Direction.EAST;
            }
        }
        else if (change[0] < 0)
        {
            if (change[1] > 0)
            {
                return Direction.SOUTHWEST;
            }
            else if (change[1] < 0)
            {
                return Direction.NORTHWEST;
            }
            else {
                return Direction.WEST;
            }
        }
        else {
            if (change[1] > 0)
            {
                return Direction.SOUTH;
            }
            else {
                return Direction.NORTH;
            }
        }
    }

    private double diagonalDistance(PathTile tile1, PathTile tile2)
    {
        int x = Math.abs(tile1.x - tile2.x);
        int y = Math.abs(tile1.y - tile2.y);

        return Math.sqrt(x*x + y*y);
    }

    private ArrayList<PathTile> getNeighbors(PathTile tile)
    {
        int[] tileCoordinate = tile.getCoordinate();
        ArrayList<PathTile> neightbors = new ArrayList<>();
        boolean northFree = false;
        boolean eastFree = false;
        boolean southFree = false;
        boolean westFree = false;

        //north
        int[] northCoordinate = {tileCoordinate[0], tileCoordinate[1] - 1};
        if (allTiles.containsKey(northCoordinate))
        {
            northFree = true;
            PathTile north = allTiles.get(northCoordinate);
            neightbors.add(north);
        }
        //east
        int[] eastCoordinate = {tileCoordinate[0] + 1, tileCoordinate[1]};
        if (allTiles.containsKey(eastCoordinate))
        {
            eastFree = true;
            PathTile east = allTiles.get(eastCoordinate);
            neightbors.add(east);
        }
        //south
        int[] southCoordinate = {tileCoordinate[0], tileCoordinate[1] + 1};
        if (allTiles.containsKey(southCoordinate))
        {
            southFree = true;
            PathTile south = allTiles.get(southCoordinate);
            neightbors.add(south);
        }
        //west
        int[] westCoordinate = {tileCoordinate[0] - 1, tileCoordinate[1]};
        if (allTiles.containsKey(westCoordinate))
        {
            westFree = true;
            PathTile west = allTiles.get(westCoordinate);
            neightbors.add(west);
        }

        //northeast
        int[] northeastCoordinate = {tileCoordinate[0] + 1, tileCoordinate[1] - 1};
        if (allTiles.containsKey(northeastCoordinate) && northFree && eastFree)
        {
            PathTile northeast = allTiles.get(northeastCoordinate);
            neightbors.add(northeast);
        }
        //northwest
        int[] northwestCoordinate = {tileCoordinate[0] - 1, tileCoordinate[1] - 1};
        if (allTiles.containsKey(northwestCoordinate) && northFree && westFree)
        {
            PathTile northwest = allTiles.get(northwestCoordinate);
            neightbors.add(northwest);
        }
        //southeast
        int[] southeastCoordinate = {tileCoordinate[0] + 1, tileCoordinate[1] + 1};
        if (allTiles.containsKey(southeastCoordinate) && southFree && eastFree)
        {
            PathTile southeast = allTiles.get(southeastCoordinate);
            neightbors.add(southeast);
        }
        //southwest
        int[] southwestCoordinate = {tileCoordinate[0] + 1, tileCoordinate[1] - 1};
        if (allTiles.containsKey(southwestCoordinate) && southFree && westFree)
        {
            PathTile southwest = allTiles.get(southwestCoordinate);
            neightbors.add(southwest);
        }

        return neightbors;
    }

    public ArrayList<PathTile> getFinalPath()
    {
        return finalPath;
    }
}
