package me.betasterren.bsgame.level;

import me.betasterren.bsgame.graphics.Bitmap;
import me.betasterren.bsgame.graphics.Screen;
import me.betasterren.bsgame.level.tiles.Block;
import me.betasterren.bsgame.level.tiles.Tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Level {
    private TileManager tileManager;
    private Bitmap[][] groundTiles;

    private int screenX, screenY;
    public boolean playerMoved = true;

    public int xOff = 0;
    public int yOff = 0;

    public Level(TileManager tileManager, int screenX, int screenY, int xOff, int yOff) {
        this.tileManager = tileManager;
        this.screenX = screenX;
        this.screenY = screenY;
        this.xOff = xOff;
        this.yOff = yOff;

        this.groundTiles = new Bitmap[screenX][screenY];

        updateBitmap();
    }

    private void updateBitmap() {
        for (int x = 0; x < screenX; x++)
            for (int y = 0; y < screenY; y++) {
                groundTiles[x][y] = tileManager.getTileByLoc(x, y).getSprite(tileManager.getBaseLayer()[x][y]);
            }

        for (int x = 0; x < screenX; x++)
            for (int y = 0; y < screenY; y++) {
                Tree tree = tileManager.getTreeByLoc(x, y);

                if (tree != null) {
                    if (tileManager.getFloraLayer()[x][y] == 666999) {
                        groundTiles[x][y] = tileManager.getConflictManager().getFloraLayer()[x][y];
                    } else {
                        Bitmap treeBitmap = tree.getSprite(tileManager.getFloraLayer()[x][y]);
                        Bitmap groundBitmap = tileManager.getTileByLoc(x, y).getSprite(tileManager.getBaseLayer()[x][y]).clone();


                        groundBitmap.render(treeBitmap, 0, 0);
                        groundTiles[x][y] = groundBitmap;
                    }
                }
            }

    }

    public void render(Screen screen) {
        updateBitmap();

        if (!playerMoved)
            return;

        int x = 0;

        for (int[] row : tileManager.getBaseLayer()) {
            int y = 0;
            for (int column : row) {
                screen.render(groundTiles[x][y], x * 16 - xOff, y * 16 - yOff);

                y++;
            }

            x++;
        }

        playerMoved = false;
    }

    public Block getTile(Vector vector) {
        return tileManager.getTileByLoc(vector);
    }

    public Block getFacingTile(Direction direction, Vector vector) {
        return tileManager.getTileByLoc(((int) vector.getX()) + direction.getxChange(),
                ((int) vector.getY()) + direction.getyChange());
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
