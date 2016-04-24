package me.betasterren.bsgame.level;

import me.betasterren.bsgame.graphics.Bitmap;
import me.betasterren.bsgame.graphics.Screen;
import me.betasterren.bsgame.level.tiles.base.Block;
import me.betasterren.bsgame.level.tiles.base.MapObject;
import me.betasterren.bsgame.level.tiles.base.Tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Level {
    private TileManager tileManager;
    private Bitmap[][] groundTiles;

    private int screenX, screenY;

    public Level(TileManager tileManager, int screenX, int screenY) {
        this.tileManager = tileManager;
        this.screenX = screenX;
        this.screenY = screenY;

        this.groundTiles = new Bitmap[screenX][screenY];

        updateBitmap();
    }

    public void updateWorld(int screenX, int screenY) {
        this.groundTiles = new Bitmap[screenX][screenY];

        updateBitmap();
    }

    private void updateBitmap() {
        // Update base layer
        for (int x = 0; x < screenX; x++)
            for (int y = 0; y < screenY; y++) {
                groundTiles[x][y] = tileManager.getMapObjectByLoc(x, y, 0).getSprite(tileManager.getBaseLayer()[x][y]);
            }

        // Update flora layer
        for (int x = 0; x < screenX; x++)
            for (int y = 0; y < screenY; y++) {
                MapObject mapObject = tileManager.getMapObjectByLoc(x, y, 1);
                Tree tree = null;

                // Check if MapObject isn't null
                if (mapObject == null)
                    continue;

                // Check if MapObject is an instance of Tree
                if (tileManager.checkTree(mapObject)) {
                    tree = (Tree) mapObject;


                    // Check if the current location is a conflict location
                    if (tileManager.getFloraLayer()[x][y] == 666999) {
                        groundTiles[x][y] = tileManager.getConflictManager().getFloraLayer(tileManager.getCurrentWorld())[x][y];
                    } else {
                        Bitmap treeBitmap = tree.getSprite(tileManager.getFloraLayer()[x][y]);
                        Bitmap groundBitmap = tileManager.getMapObjectByLoc(x, y, 0).getSprite(tileManager.getBaseLayer()[x][y]).clone();


                        groundBitmap.render(treeBitmap, 0, 0);
                        groundTiles[x][y] = groundBitmap;
                    }

                }
            }

    }

    public void render(Screen screen) {
        updateBitmap();

        int x = 0;

        // Loop through the base layer and render it
        for (int[] row : tileManager.getBaseLayer()) {
            int y = 0;
            for (int column : row) {
                screen.render(groundTiles[x][y], x * 16 - tileManager.getCurrentWorld().getX(), y * 16 - tileManager.getCurrentWorld().getY());

                y++;
            }

            x++;
        }
    }

    public Block getFacingTile(Direction direction, Vector vector) {
        return (Block) tileManager.getMapObjectByLoc(((int) vector.getX()) + direction.getxChange(),
                ((int) vector.getY()) + direction.getyChange(), 0);
    }

    public Block[] getFacingTiles(Direction direction, Vector vector, int range) {
        if (range == 0)
            return null;
        else {
            int xBase = (int) vector.getX();
            int yBase = (int) vector.getY();

            ArrayList<Block> blockList = new ArrayList<>();
            Block[] tiles = new Block[range - 1];

            for (int i = 1; i < range + 1; i++)
                blockList.add(getFacingTile(direction, new Vector(xBase + (i * direction.getxChange()),
                        yBase + (i * direction.getyChange()))));

            tiles = blockList.toArray(tiles);
            return tiles;
        }
    }

    public Block[] getSurroundingTiles(Vector vector, int range) {
        ArrayList<Block> blockList = new ArrayList<>();
        Block[] tiles = new Block[range - 1];

        for (Direction direction : Direction.values()) {
            List tempBlockList = Arrays.asList(getFacingTiles(direction, vector, range));

            blockList.addAll(tempBlockList);
        }

        tiles = blockList.toArray(tiles);
        return tiles;
    }
}
