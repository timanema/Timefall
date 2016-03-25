package me.betasterren.bsgame.level;

import me.betasterren.bsgame.level.tiles.Block;
import me.betasterren.bsgame.level.tiles.GrassTile;

import java.util.ArrayList;
import java.util.List;

public class TileManager {
    private int screenX, screenY, worldX, worldY;

    private int[][] tiles;
    private Level level;

    private List<Block> blocks;

    public TileManager() {
        screenX = 40;
        screenY = 23;

        initMap();

        blocks = new ArrayList<>();
        this.tiles = new int[worldX][worldY];

        initTiles();
    }

    private void initMap() {
        //TODO: Load map.png and add blocks according to colours
        worldX = screenX;
        worldY = screenY;
    }

    private void initTiles() {
        blocks.add(new GrassTile());

        // Debug code
        //TODO: Remove this
        for (int x = 0; x < 40; x++) {
            for (int y = 0; y < 23; y++)
                tiles[x][y] = 0;
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
}
