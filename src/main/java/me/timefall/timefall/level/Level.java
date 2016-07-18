package me.timefall.timefall.level;

import me.timefall.timefall.graphics.Bitmap;
import me.timefall.timefall.graphics.Light;
import me.timefall.timefall.graphics.Screen;
import me.timefall.timefall.level.tiles.base.Block;
import me.timefall.timefall.level.tiles.base.MapObject;
import me.timefall.timefall.level.tiles.base.Tree;

import java.util.ArrayList;

public class Level
{
    private TileManager tileManager;
    private Bitmap[][] groundTiles;

    private int screenX, screenY;

    Light testLight = new Light(0xff0000ff, 140);

    public Level(TileManager tileManager, int screenX, int screenY)
    {
        this.tileManager = tileManager;
        this.screenX = screenX;
        this.screenY = screenY;

        this.groundTiles = new Bitmap[screenX][screenY];

        System.out.println("HNHNH!!!: " + (screenX * screenY));
        updateBitmap();
    }

    public void updateWorld(int screenX, int screenY)
    {
        this.groundTiles = new Bitmap[screenX][screenY];

        updateBitmap();
    }

    public void updateBitmap()
    {
        //System.out.println("EDT (update level): " + EventQueue.isDispatchThread());

        // Update base layer
        for (int x = 0; x < screenX; x++)
            for (int y = 0; y < screenY; y++)
            {
                groundTiles[x][y] = tileManager.getMapObjectByLoc(x, y, 0).getSprite(tileManager.getBaseLayer()[x][y]);

                //System.out.println(x + "," + y + ": " + tileManager.getMapObjectByLoc(x, y, 0).getName());
            }

        // Update flora layer
        for (int x = 0; x < screenX; x++)
            for (int y = 0; y < screenY; y++)
            {
                MapObject mapObject = tileManager.getMapObjectByLoc(x, y, 1);
                Tree tree = null;

                // Check if MapObject isn't null
                if (mapObject == null)
                    continue;

                // Check if MapObject is an instance of Tree
                if (tileManager.checkTree(mapObject))
                {
                    tree = (Tree) mapObject;


                    // Check if the current location is a conflict location
                    if (tileManager.getFloraLayer()[x][y] == 666999)
                    {
                        groundTiles[x][y] = tileManager.getConflictManager().getFloraLayer(tileManager.getCurrentWorld())[x][y];
                    } else
                    {
                        Bitmap treeBitmap = tree.getSprite(tileManager.getFloraLayer()[x][y]);
                        Bitmap groundBitmap = tileManager.getMapObjectByLoc(x, y, 0).getSprite(tileManager.getBaseLayer()[x][y]).clone();


                        groundBitmap.draw(treeBitmap, 0, 0);
                        groundTiles[x][y] = groundBitmap;
                    }

                }
            }
    }

    public void render(Screen screen)
    {
        updateBitmap();

        int x = 0;
        //System.out.println("EDT (draw level): " + EventQueue.isDispatchThread());

        //Loop through the base layer and render it
        for (int[] row : tileManager.getBaseLayer())
        {
            int y = 0;
            for (int column : row)
            {
                screen.draw(groundTiles[x][y], x * 16 - tileManager.getCurrentWorld().getX(), y * 16 - tileManager.getCurrentWorld().getY());
                /*screen.draw(Sprite.test, 0, 16);
                screen.draw(GrassTile.spr(), 0, 0);
                screen.draw(GrassTile.spr(), 16, 0);


                for (int xLoc = 19; xLoc < 19 + 16; xLoc++)
                    for (int yLoc = 19; yLoc < 19 + 16; yLoc++) {
                        screen.colours[xLoc + yLoc * screen.width] = Colour.COLOUR_BLACK;
                    }*/

                y++;
            }

            x++;
        }

        //screen.render();
        //for (int i = 50; i < 66; i++)
        //  for (int j = 50; j < 66; j++)
        //       screen.renderPixel(i, j, 0, 0, 1, 1);

        //for (int lX = 0; lX < testLight.getDiameter(); lX++)
        //    for (int lY = 0; lY < testLight.getDiameter(); lY++) {
        //       screen.
        //   }
    }

    public Block getFacingTile(Direction direction, Vector vector)
    {
        return (Block) tileManager.getMapObjectByLoc(((int) vector.getX()) + direction.getxChange(),
                ((int) vector.getY()) + direction.getyChange(), 0);
    }

    public Block[] getFacingTiles(Direction direction, Vector vector, int range)
    {
        if (range == 0)
            return null;
        else
        {
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

    public Block[] getSurroundingTiles(Vector vector, int range)
    {
        ArrayList<Block> blockList = new ArrayList<>();
        Block[] tiles = new Block[range - 1];

        int xPos = (int) vector.getX();
        int yPos = (int) vector.getY();

        for (int x = xPos - range; x <= xPos + range; x++)
        {
            if (x < 0 || x > tileManager.getCurrentWorld().getWidth())
                continue;

            for (int y = yPos - range; y <= yPos + range; y++)
            {
                if ((y == yPos && x == xPos) || y < 0 || y > tileManager.getCurrentWorld().getHeight())
                    continue;

                MapObject mapObject = tileManager.getMapObjectByLoc(x, y, 0);

                if (mapObject != null && tileManager.checkBlock(mapObject))
                    blockList.add((Block) mapObject);
            }
        }


        tiles = blockList.toArray(tiles);
        return tiles;
    }
}
