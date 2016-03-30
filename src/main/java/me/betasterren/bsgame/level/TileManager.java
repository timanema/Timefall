package me.betasterren.bsgame.level;

import me.betasterren.bsgame.level.tiles.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TileManager {
    public int screenX, screenY, worldX, worldY;

    private int[][] baseLayer;
    private int[][] floraLayer;

    private Level level;

    private List<Block> blocks;
    private List<Tree> trees;

    public TileManager() {
        screenX = 150;
        screenY = 150;

        initMap();

        blocks = new ArrayList<>();
        trees = new ArrayList<>();

        this.baseLayer = new int[worldX][worldY];
        this.floraLayer = new int[worldX][worldY];

        initTiles();
    }

    private void initMap() {
        //TODO: Load map.png and add blocks according to colour
        worldX = screenX;
        worldY = screenY;

        System.out.println(worldX + " " + worldY);
        System.out.println(screenX + " " + screenY);
    }

    private void initTiles() {
        blocks.add(new GrassTile());
        blocks.add(new WaterTile());

        trees.add(new SpruceTree());
        trees.add(new HuisBoom());

        Random random = new Random();

        // Debug code
        //TODO: Verwijderen van hier
        for (int x = 0; x < screenX; x++) {
            for (int y = 0; y < screenY; y++) {
                baseLayer[x][y] = 0;
            }
        }

        int xPlus = 10;
        int yPlus = 16;
        // Vijvertje maken
        baseLayer[1 + xPlus][1 + yPlus] = 9;
        baseLayer[2 + xPlus][1 + yPlus] = 2;
        baseLayer[3 + xPlus][1 + yPlus] = 2;
        baseLayer[4 + xPlus][1 + yPlus] = 3;
        baseLayer[4 + xPlus][2 + yPlus] = 4;
        baseLayer[4 + xPlus][3 + yPlus] = 4;
        baseLayer[4 + xPlus][4 + yPlus] = 5;
        baseLayer[3 + xPlus][4 + yPlus] = 6;
        baseLayer[2 + xPlus][4 + yPlus] = 6;
        baseLayer[1 + xPlus][4 + yPlus] = 7;
        baseLayer[1 + xPlus][3 + yPlus] = 8;
        baseLayer[1 + xPlus][2 + yPlus] = 8;
        baseLayer[2 + xPlus][2 + yPlus] = 1;
        baseLayer[2 + xPlus][3 + yPlus] = 1;
        baseLayer[3 + xPlus][2 + yPlus] = 1;
        baseLayer[3 + xPlus][3 + yPlus] = 1;

        int yRem = 0;
        for (int x = 0; x < trees.get(1).getWidth(); x++)
            for (int y = 0; y < trees.get(1).getHeight(); y++) {
                floraLayer[10 + x][11 + y] = 16 + yRem;
                yRem++;
            }

        yRem = 0;
        for (int x = 0; x < trees.get(0).getWidth(); x++)
            for (int y = 0; y < trees.get(0).getHeight(); y++) {
                floraLayer[15 + x][13 + y] = 10 + yRem;
                yRem++;
            }

        //TODO: tot hier
        initWorld();
    }

    private void initWorld() {
        level = new Level(this, screenX, screenY);
    }

    public Level getLevel() {
        return level;
    }

    public int[][] getBaseLayer() {
        return baseLayer;
    }
    public int[][] getFloraLayer() {
        return floraLayer;
    }

    public Block getTileByID(int ID) {
        if (blocks.isEmpty())
            return null;

        for (Block block : blocks)
            for (int blockID : block.getBlockID())
                if (blockID == ID)
                    return block;
        return null;
    }

    public Block getTileByLoc(int x, int y) {
        int blockID;

        try {
            blockID = baseLayer[x][y];
        } catch (ArrayIndexOutOfBoundsException exception) {
            return null;
        }

        return getTileByID(blockID);
    }

    public Block getTileByLoc(Vector vector) {
        return getTileByLoc((int) vector.getX(), (int) vector.getY());
    }

    public Tree getTreeByID(int ID) {
        if (trees.isEmpty())
            return null;

        for (Tree tree : trees)
            for (int treeID : tree.getBlockID())
                if (treeID == ID)
                    return tree;

        return null;
    }

    public Tree getTreeByLoc(int x, int y) {
        int treeID;

        try {
            treeID = floraLayer[x][y];
        } catch (ArrayIndexOutOfBoundsException exception) {
            return null;
        }

        return getTreeByID(treeID);
    }
}
