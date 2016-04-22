package me.betasterren.bsgame.level.conflict;

import me.betasterren.bsgame.graphics.Bitmap;
import me.betasterren.bsgame.level.TileManager;

import java.util.HashMap;

public class ConflictManager {
    private TileManager tileManager;
    private int worldX, worldY;

    private Bitmap[][] floraLayer;
    private int[][] impFloraLayer;
    private HashMap<String, String> floraConflicts;

    public ConflictManager(TileManager tileManager, int worldX, int worldY, HashMap<String, String> floraConflicts) {
        this.tileManager = tileManager;
        this.worldX = worldX;
        this.worldY = worldY;

        this.floraLayer = new Bitmap[worldX][worldY];
        this.impFloraLayer = new int[worldX][worldY];
        this.floraConflicts = floraConflicts;
    }

    public void solveConflicts() throws RenderConflictException {
        System.out.println("  Looking for conflicts to solve ...");

        for (String locationString : floraConflicts.keySet()) {
            String[] parsedLocation = locationString.split(",");
            String[] parsedIDs = floraConflicts.get(locationString).split(">");

            if (parsedLocation.length != 2 || parsedIDs.length != 2) {
                System.out.println("Error occurred while trying to process '" + locationString + ": " + floraConflicts.get(locationString) + "'! Format: x,y: ID1>ID2");
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
                System.out.println("Error occurred while trying to process '" + (!coordsParsed ? locationString + "' to coordinates!" : floraConflicts.get(locationString) + "' to IDs!") + " Format: x,y: ID1>ID2");
                throw new RenderConflictException(x, y, "flora");
            }

            if (x < 0 || y < 0 || x > worldX || y > worldY)
                throw new RenderConflictException("X: " + x + ", Y: " + y + " are invalid coordinates! (MinX: 0, MinY: 0, MaxX: " + worldX + ", MaxY: " + worldY + ")");

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

            floraLayer[x][y] = groundBitmap;
            impFloraLayer[x][y] = secondLayer;

            System.out.println("   Solved conflict at (" + x + "," + y + ": flora)!");
        }
    }

    public Bitmap[][] getFloraLayer() {
        return floraLayer;
    }

    public int getFloraID(int x, int y) {
        return impFloraLayer[x][y];
    }
}
