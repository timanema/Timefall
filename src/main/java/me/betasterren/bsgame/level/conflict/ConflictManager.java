package me.betasterren.bsgame.level.conflict;

import me.betasterren.bsgame.graphics.Bitmap;
import me.betasterren.bsgame.level.TileManager;
import me.betasterren.bsgame.level.world.World;

import java.util.ArrayList;
import java.util.HashMap;

public class ConflictManager {
    private TileManager tileManager;

    private HashMap<String, Bitmap[][]> floraLayer;
    private HashMap<String, int[][]> impFloraLayer;
    private HashMap<String, HashMap<String, String>> floraConflicts;

    public ConflictManager(TileManager tileManager, ArrayList<World> worlds, HashMap<String, HashMap<String, String>> floraConflicts) {
        this.tileManager = tileManager;
        floraLayer = new HashMap<>();
        impFloraLayer = new HashMap<>();
        this.floraConflicts = new HashMap<>();

        for (World world : worlds) {
            floraLayer.put(world.getWorldName(), new Bitmap[world.getWidth()][world.getHeight()]);
            impFloraLayer.put(world.getWorldName(), new int[world.getWidth()][world.getHeight()]);
        }

        for (String worldName : floraConflicts.keySet())
            this.floraConflicts.put(worldName, floraConflicts.get(worldName));
    }

    public void solveConflicts(World world) throws RenderConflictException {
        System.out.println("  Looking for conflicts to solve ...");

        HashMap<String, String> conflicts = floraConflicts.get(world.getWorldName());

        if (conflicts == null || conflicts.isEmpty())
            return;

        for (String locationString : conflicts.keySet()) {
            String[] parsedLocation = locationString.split(",");
            String[] parsedIDs = conflicts.get(locationString).split(">");

            if (parsedLocation.length != 2 || parsedIDs.length != 2) {
                System.out.println("Error occurred while trying to process '" + locationString + ": " + conflicts.get(locationString) + "'! Format: x,y: ID1>ID2");
                throw new RenderConflictException(-1, -1, "flora");
            }

            int x = -1, y = -1, firstLayer, secondLayer;
            boolean coordsParsed = false;

            try {
                x = Integer.parseInt(parsedLocation[0]);
                y = Integer.parseInt(parsedLocation[1]);
                coordsParsed = true;

                firstLayer = Integer.parseInt(parsedIDs[0]);
                secondLayer = Integer.parseInt(parsedIDs[1]);
            } catch (NumberFormatException exception) {
                System.out.println("Error occurred while trying to process '" + (!coordsParsed ? locationString + "' to coordinates!" : conflicts.get(locationString) + "' to IDs!") + " Format: x,y: ID1>ID2");
                throw new RenderConflictException(x, y, "flora");
            }

            if (x < 0 || y < 0 || x > world.getWidth() || y > world.getHeight())
                throw new RenderConflictException("X: " + x + ", Y: " + y + " are invalid coordinates! (MinX: 0, MinY: 0, MaxX: " + world.getWidth() + ", MaxY: " + world.getHeight() + ")");

            if (!tileManager.validID(firstLayer) || !tileManager.validID(secondLayer))
                throw new RenderConflictException("ID: " + firstLayer + ", ID: " + secondLayer + " are invalid IDs!");

            Bitmap groundBitmap = tileManager.getMapObjectByID(tileManager.getBaseLayer()[x][y]).getSprite(tileManager.getBaseLayer()[x][y]).clone();
            Bitmap firstLayerBitmap = tileManager.getMapObjectByID(firstLayer).getSprite(firstLayer).clone();
            Bitmap secondLayerBitmap = tileManager.getMapObjectByID(secondLayer).getSprite(secondLayer).clone();

            // Shouldn't happen but who knows
            if (groundBitmap == null || firstLayerBitmap == null || secondLayerBitmap == null)
                throw new RenderConflictException("Couldn't fetch bitmap for (" + x + "," + y + ") at base or flora layer!");

            groundBitmap.render(firstLayerBitmap, 0, 0);
            groundBitmap.render(secondLayerBitmap, 0, 0);

            floraLayer.get(world.getWorldName())[x][y] = groundBitmap;
            impFloraLayer.get(world.getWorldName())[x][y] = secondLayer;

            System.out.println("   Solved conflict at (" + x + "," + y + ": flora)!");
        }
    }

    public Bitmap[][] getFloraLayer(World world) {
        return floraLayer.get(world.getWorldName());
    }

    public int getFloraID(World world, int x, int y) {
        return impFloraLayer.get(world.getWorldName())[x][y];
    }
}
