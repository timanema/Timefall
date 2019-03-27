package me.timefall.timefall.entities;

import me.timefall.timefall.Timefall;
import me.timefall.timefall.entities.behaviors.BehaviorCondition;
import me.timefall.timefall.level.Vector;

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

    public Pathfinding(Vector target, Vector location)
    {
        this.collisions = Timefall.getTileManager().getCurrentWorld().getCollisions();
        this.worldGrid = Timefall.getTileManager().getCurrentWorld().getWorldGrid();

        this.target = new PathTile((int) target.getX(), (int) target.getY());
        this.location = new PathTile((int) location.getX(), (int) location.getY());

        calculatePath();
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

}
