package me.timefall.timefall.entities;

import me.timefall.timefall.Timefall;
import me.timefall.timefall.entities.behaviors.BehaviorCondition;
import me.timefall.timefall.level.Vector;
import me.timefall.timefall.level.tiles.base.MapObject;

import java.awt.*;
import java.lang.reflect.Array;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class Pathfinding {
    private PathTile target;
    private PathTile location;

    private ArrayList<Rectangle> collisions;
    private int[][] worldGrid;
    private HashMap<Integer[], PathTile> allTiles;

    private ArrayList<PathTile> finalPath;

    public Pathfinding(Vector target, Vector location)
    {
        this.collisions = Timefall.getTileManager().getCurrentWorld().getCollisions();
        this.worldGrid = Timefall.getTileManager().getCurrentWorld().getWorldGrid();

        for (int i = 0; i < worldGrid.length; i++)
        {
            for (int j = 0; j < worldGrid[i].length; j++)
            {
                Integer[] coordinate = {i, j};
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
                    allTiles.put(coordinate, new PathTile(i, j));
                }
            }
        }

        Integer[] targetCoord = {(int) target.getX(), (int) target.getY()};
        Integer[] locCoord = {(int) location.getX(), (int) location.getY()};

        this.target = allTiles.get(targetCoord);
        this.location = allTiles.get(locCoord);

        calculatePath();
    }

    private void reconstructPath(PathTile current)
    {
        ArrayList<PathTile> totalPath = new ArrayList<>();
        totalPath.add(current);
        current = current.cameFrom;
        while (!(current.x == target.x && current.y == target.y))
        {
            totalPath.add(current);
            current = current.cameFrom;
        }
        this.finalPath = totalPath;
    }

    private void calculatePath()
    {
        ArrayList<PathTile> closedSet = new ArrayList<>();

        ArrayList<PathTile> openSet = new ArrayList<>();
        openSet.add(location);

        location.gScore = 0.0;

        location.calculatefScore(target);

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

                neighbor.cameFrom = current;
                neighbor.gScore = tentativegScore;
                neighbor.fScore = neighbor.gScore + diagonalDistance(neighbor, target);
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
        Integer[] tileCoordinate = tile.getCoordinate();
        ArrayList<PathTile> neightbors = new ArrayList<>();
        boolean northFree = false;
        boolean eastFree = false;
        boolean southFree = false;
        boolean westFree = false;

        //north
        Integer[] northCoordinate = {tileCoordinate[0], tileCoordinate[1] - 1};
        if (allTiles.containsKey(northCoordinate))
        {
            northFree = true;
            PathTile north = allTiles.get(northCoordinate);
            neightbors.add(north);
        }
        //east
        Integer[] eastCoordinate = {tileCoordinate[0] + 1, tileCoordinate[1]};
        if (allTiles.containsKey(eastCoordinate))
        {
            eastFree = true;
            PathTile east = allTiles.get(eastCoordinate);
            neightbors.add(east);
        }
        //south
        Integer[] southCoordinate = {tileCoordinate[0], tileCoordinate[1] + 1};
        if (allTiles.containsKey(southCoordinate))
        {
            southFree = true;
            PathTile south = allTiles.get(southCoordinate);
            neightbors.add(south);
        }
        //west
        Integer[] westCoordinate = {tileCoordinate[0] - 1, tileCoordinate[1]};
        if (allTiles.containsKey(westCoordinate))
        {
            westFree = true;
            PathTile west = allTiles.get(westCoordinate);
            neightbors.add(west);
        }

        //northeast
        Integer[] northeastCoordinate = {tileCoordinate[0] + 1, tileCoordinate[1] - 1};
        if (allTiles.containsKey(northeastCoordinate) && northFree && eastFree)
        {
            PathTile northeast = allTiles.get(northeastCoordinate);
            neightbors.add(northeast);
        }
        //northwest
        Integer[] northwestCoordinate = {tileCoordinate[0] - 1, tileCoordinate[1] - 1};
        if (allTiles.containsKey(northwestCoordinate) && northFree && westFree)
        {
            PathTile northwest = allTiles.get(northwestCoordinate);
            neightbors.add(northwest);
        }
        //southeast
        Integer[] southeastCoordinate = {tileCoordinate[0] + 1, tileCoordinate[1] + 1};
        if (allTiles.containsKey(southeastCoordinate) && southFree && eastFree)
        {
            PathTile southeast = allTiles.get(southeastCoordinate);
            neightbors.add(southeast);
        }
        //southwest
        Integer[] southwestCoordinate = {tileCoordinate[0] + 1, tileCoordinate[1] - 1};
        if (allTiles.containsKey(southwestCoordinate) && southFree && westFree)
        {
            PathTile southwest = allTiles.get(southwestCoordinate);
            neightbors.add(southwest);
        }

        return neightbors;
    }

}
