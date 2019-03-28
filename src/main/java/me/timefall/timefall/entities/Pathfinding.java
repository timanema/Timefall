package me.timefall.timefall.entities;

import me.timefall.timefall.Timefall;
import me.timefall.timefall.entities.behaviors.BehaviorCondition;
import me.timefall.timefall.level.Vector;
import me.timefall.timefall.level.tiles.base.MapObject;

import java.awt.*;
import java.lang.reflect.Array;
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

    private void removeSolidTiles()
    {

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
                //finish, reconstruct path
            }

            openSet.remove(current);
            closedSet.add(current);

            //get neighbors
        }
    }

    private ArrayList<PathTile> getNeighbors(PathTile tile)
    {
        //first non-diagonal
        //north


        return null;
    }

}
