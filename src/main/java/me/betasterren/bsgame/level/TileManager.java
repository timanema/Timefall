package me.betasterren.bsgame.level;

import me.betasterren.bsgame.BSGame;
import me.betasterren.bsgame.entities.EntityManager;
import me.betasterren.bsgame.level.conflict.ConflictManager;
import me.betasterren.bsgame.level.conflict.RenderConflictException;
import me.betasterren.bsgame.level.tiles.*;
import me.betasterren.bsgame.level.tiles.base.Block;
import me.betasterren.bsgame.level.tiles.base.MapObject;
import me.betasterren.bsgame.level.tiles.base.Tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TileManager {
    private ConflictManager conflictManager;
    private EntityManager entityManager;

    public int screenX, screenY, worldX, worldY;

    private int[][] baseLayer;
    private int[][] floraLayer;

    private Level level;

    private List<Block> blocks;
    private List<Tree> trees;

    public TileManager() {
        System.out.println(" Loading game tiles ... ");

        screenX = 50;
        screenY = 50;
        conflictManager = new ConflictManager(this, screenX, screenY, BSGame.getFileManager().getFloraConflicts());

        initMap();

        blocks = new ArrayList<>();
        trees = new ArrayList<>();

        this.baseLayer = new int[worldX][worldY];
        this.floraLayer = new int[worldX][worldY];

        initTiles();
    }

    private void initMap() {
        //TODO: Load map.png and add blocks according to colour
        System.out.println("  Loading map ...");

        worldX = screenX;
        worldY = screenY;
    }

    private void initTiles() {
        System.out.println("  Creating world ...");

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
        for (int i = 0; i < 2; i++) {
            for (int x = 0; x < trees.get(0).getWidth(); x++)
                for (int y = 0; y < trees.get(0).getHeight(); y++) {
                    floraLayer[15 + x + i][13 + y + i * 2] = 10 + yRem;
                    yRem++;
                }
            yRem = 0;
        }

        floraLayer[16][15] = 666999;

        //TODO: tot hier
        initWorld();
    }

    private void initWorld() {
        try {
            conflictManager.solveConflicts();
        } catch (RenderConflictException exception) {
            exception.printStackTrace();

            System.exit(-1);
        }

        System.out.println("  Initializing level ...");

        level = new Level(this, screenX, screenY, (int) Vector.worldxPos, (int) Vector.worldyPos);
        entityManager = new EntityManager(worldX, worldY);
    }

    public Level getLevel() {
        return level;
    }

    public ConflictManager getConflictManager() {
        return conflictManager;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public int[][] getBaseLayer() {
        return baseLayer;
    }

    public int[][] getFloraLayer() {
        return floraLayer;
    }

    public MapObject getMapObjectByID(int ID) {
        // Check block array for given ID
        if (!blocks.isEmpty())
            for (Block block : blocks)
                for (int blockID : block.getBlockID())
                    if (blockID == ID)
                        return block;

        // Check tree array for given ID
        if (!trees.isEmpty())
            for (Tree tree : trees)
                for (int treeID : tree.getBlockID())
                    if (treeID == ID)
                        return tree;
        return null;
    }

    public MapObject getMapObjectByLoc(int x, int y, int layer) {
        int blockID;

        try {
            switch (layer) {
                case 0:
                    // Base layer
                    blockID = baseLayer[x][y];
                    break;
                case 1:
                    // Flora layer
                    blockID = floraLayer[x][y];

                    if (blockID == 666999)
                        blockID = conflictManager.getFloraID(x, y);
                    break;
                default:
                    return null;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }

        return getMapObjectByID(blockID);
    }

    public MapObject getMapObjectByLoc(Vector vector, int layer) {
        return getMapObjectByLoc((int) vector.getX(), (int) vector.getY(), layer);
    }

    public boolean checkBlock(MapObject mapObject) {
        return mapObject instanceof Block;
    }

    public boolean checkTree(MapObject mapObject) {
        return mapObject instanceof Tree;
    }

    public boolean validID(int ID) {
        return getMapObjectByID(ID) != null;
    }
}
