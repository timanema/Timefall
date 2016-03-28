package me.betasterren.bsgame.level;

import me.betasterren.bsgame.level.tiles.Block;
import me.betasterren.bsgame.level.tiles.GrassTile;
import me.betasterren.bsgame.level.tiles.WaterTile;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TileManager {
    private int screenX, screenY, worldX, worldY;

    private int[][] tiles;
    private Level level;

    private List<Block> blocks;

    public TileManager() {
        screenX = 60;
        screenY = 60;

        initMap();

        blocks = new ArrayList<>();
        this.tiles = new int[worldX][worldY];

        initTiles();
    }

    private void initMap() {
        //TODO: Load map.png and add blocks according to colour
        worldX = screenX;
        worldY = screenY;
    }

    private void initTiles() {
        blocks.add(new GrassTile());
        blocks.add(new WaterTile());

        Random random = new Random();

        // Debug code
        //TODO: Remove this
        for (int x = 0; x < screenX; x++) {
            for (int y = 0; y < screenY; y++) {
                tiles[x][y] = (random.nextInt(100) > 30 ? 0 : 1);
            }
        }

        initWorld();
    }

    private void initWorld() {
        level = new Level(this, screenX, screenY);
    }

    public Level getLevel() {
        return level;
    }

    public int[][] getTiles() {
        return tiles;
    }

    public Block getTileByID(int ID) {
        if (blocks.isEmpty())
            return null;

        for (Block block : blocks)
            if (block.getBlockID() == ID)
                return block;
        return null;
    }

    public Block getTileByLoc(int x, int y) {
        int blockID;

        try {
            blockID = tiles[x][y];
        } catch (ArrayIndexOutOfBoundsException exception) {
            return null;
        }

        return getTileByID(blockID);
    }

    public Block getTileByLoc(Vector vector) {
        return getTileByLoc((int) vector.getX(), (int) vector.getY());
    }
}
