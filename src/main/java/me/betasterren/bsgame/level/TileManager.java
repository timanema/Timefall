package me.betasterren.bsgame.level;

import me.betasterren.bsgame.BSGame;
import me.betasterren.bsgame.entities.EntityManager;
import me.betasterren.bsgame.level.conflict.ConflictManager;
import me.betasterren.bsgame.level.conflict.RenderConflictException;
import me.betasterren.bsgame.level.tiles.*;
import me.betasterren.bsgame.level.tiles.base.Block;
import me.betasterren.bsgame.level.tiles.base.MapObject;
import me.betasterren.bsgame.level.tiles.base.Tree;
import me.betasterren.bsgame.level.world.World;

import java.util.ArrayList;
import java.util.List;

public class TileManager {
    private ConflictManager conflictManager;
    private EntityManager entityManager;

    public int worldX, worldY;

    private World currentWorld;
    private ArrayList<World> loadedWorlds;

    private Level level;

    public List<Block> blocks;
    public List<Tree> trees;

    public TileManager() {
        System.out.println(" Loading game tiles ... ");

        blocks = new ArrayList<>();
        trees = new ArrayList<>();
        loadedWorlds = new ArrayList<>();

        initTiles();
        initMap();
    }

    private void initMap() {
        System.out.println("  Loading worlds ...");

        // Creating worlds
        for (String worldName : BSGame.getFileManager().worldFiles) {
            int xOff = 0;
            int yOff = 0;

            if (Vector.globalWorldName.equals(worldName)) {
                xOff = (int) Vector.worldxPos;
                yOff = (int) Vector.worldyPos;
            }

            loadedWorlds.add(new World(this, worldName, xOff, yOff, "/worlds/" + worldName + ".png", "/entities/" + worldName + ".txt"));
        }

        currentWorld = getWorld(Vector.globalWorldName);

        worldX = currentWorld.getWidth();
        worldY = currentWorld.getHeight();

        conflictManager = new ConflictManager(this, loadedWorlds, BSGame.getFileManager().getFloraConflicts());

        initWorld();
    }

    private void initTiles() {
        System.out.println("  Loading tiles ...");

        blocks.add(new GrassTile());
        blocks.add(new WaterTile());
        blocks.add(new VoidTile());

        trees.add(new SpruceTree());
    }

    private void initWorld() {
        for (World world : loadedWorlds)
            try {
                conflictManager.solveConflicts(world);
            } catch (RenderConflictException exception) {
                exception.printStackTrace();

                System.exit(-1);
            }

        System.out.println("  Initializing level ...");

        level = new Level(this, worldX, worldY);
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
        return currentWorld.getBaseLayer();
    }

    public int[][] getFloraLayer() {
        return currentWorld.getFloraLayer();
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

    public MapObject getMapObjectByHex(int hexCode) {
        // Check block array for given hexCode
        if (!blocks.isEmpty())
            for (Block block : blocks)
                for (int hex : block.getHex())
                    if (hex == hexCode)
                        return block;

        // Check tree array for given hexCode
        if (!trees.isEmpty())
            for (Tree tree : trees)
                for (int hex : tree.getHex())
                    if (hex == hexCode)
                        return tree;

        return null;
    }

    public MapObject getMapObjectByLoc(int x, int y, int layer) {
        int blockID;

        try {
            switch (layer) {
                case 0:
                    // Base layer
                    blockID = getBaseLayer()[x][y];
                    break;
                case 1:
                    // Flora layer
                    blockID = getFloraLayer()[x][y];

                    if (blockID == 666999)
                        blockID = conflictManager.getFloraID(currentWorld, x, y);
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

    public World getCurrentWorld() {
        return currentWorld;
    }

    public World getWorld(String worldName) {
        for (World world : loadedWorlds)
            if (world.getWorldName().equals(worldName))
                return world;
        return null;
    }
}
