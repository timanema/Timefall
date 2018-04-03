package me.timefall.timefall.level;

import me.timefall.timefall.Timefall;
import me.timefall.timefall.graphics.components.Bitmap;
import me.timefall.timefall.graphics.components.Colour;
import me.timefall.timefall.graphics.lighting.Light;
import me.timefall.timefall.graphics.components.Screen;
import me.timefall.timefall.graphics.lighting.ShadowType;
import me.timefall.timefall.level.tiles.base.Block;
import me.timefall.timefall.level.tiles.base.MapObject;
import me.timefall.timefall.level.tiles.base.Tree;

import java.awt.*;
import java.util.ArrayList;

public class Level
{
    private TileManager tileManager;
    private Bitmap[][] groundTiles;

    private int[] shadowMap;
    private int[][][] shadowGrid;

    private int screenX, screenY;
    private MapObject[][] blockObject;
    private MapObject[][] treeObjects;

    Light testLight = new Light(0xffFFA500, 120);
    Light testLight2 = new Light(0xff0000ff, 80);

    public Level(TileManager tileManager, int screenX, int screenY)
    {
        this.tileManager = tileManager;
        this.screenX = screenX;
        this.screenY = screenY;

        this.updateWorld(screenX, screenY);
    }

    public void updateWorld(int screenX, int screenY)
    {
        this.groundTiles = new Bitmap[screenX][screenY];
        this.shadowMap = new int[screenX * 16 * screenY * 16];
        this.shadowGrid = new int[screenX][screenY][256];
        this.blockObject = new MapObject[screenX][screenY];
        this.treeObjects = new MapObject[screenX][screenY];

        for (int x = 0; x < screenX; x++)
        {
            for (int y = 0; y < screenY; y++)
            {
                blockObject[x][y] = tileManager.getMapObjectByLoc(x, y, 0);
                treeObjects[x][y] = tileManager.getMapObjectByLoc(x, y, 1);
            }
        }


        updateBitmap();
        updateAnimations();
    }

    private void updateAnimations()
    {

    }

    private void updateBitmap()
    {
        // Update base layer
        for (int x = 0; x < screenX; x++)
            for (int y = 0; y < screenY; y++)
            {
                groundTiles[x][y] = blockObject[x][y].getSprite(tileManager.getBaseLayer()[x][y]);

                if (Timefall.getSettings().isDynamicLightEnabled())
                {
                    Bitmap bitmap = groundTiles[x][y];

                    for (int sX = 0; sX < bitmap.width; sX++)
                    {
                        for (int sY = 0; sY < bitmap.height; sY++)
                        {
                            if (bitmap.colours[sX + sY * bitmap.width].alpha != 0.0)
                            {
                                shadowMap[(sX + x * 16) + (sY + y * 16) * screenX * 16] = ((Block) blockObject[x][y]).getShadowType().type;
                            }
                        }
                    }
                }
            }

        // Update flora layer
        for (int x = 0; x < screenX; x++)
        {
            for (int y = 0; y < screenY; y++)
            {
                MapObject mapObject = treeObjects[x][y];
                Tree tree;

                // Check if MapObject isn't null
                if (mapObject == null)
                    continue;

                // Check if MapObject is an instance of Tree
                if (tileManager.checkTree(mapObject))
                {
                    tree = (Tree) mapObject;
                    Bitmap treeBitmap;

                    // Check if the current location is a conflict location
                    if (tileManager.getFloraLayer()[x][y] == 666999)
                    {
                        treeBitmap = tileManager.getConflictManager().getFloraLayer(tileManager.getCurrentWorld())[x][y];
                        groundTiles[x][y] = treeBitmap;
                    } else
                    {
                        treeBitmap = tree.getSprite(tileManager.getFloraLayer()[x][y]);
                        Bitmap groundBitmap = blockObject[x][y].getSprite(tileManager.getBaseLayer()[x][y]).clone();


                        groundBitmap.draw(treeBitmap, 0, 0);
                        groundTiles[x][y] = groundBitmap;
                    }

                    if (Timefall.getSettings().isDynamicLightEnabled())
                    {
                        Bitmap bitmap = treeBitmap;

                        for (int sX = 0; sX < bitmap.width; sX++)
                        {
                            for (int sY = 0; sY < bitmap.height; sY++)
                            {
                                if (bitmap.colours[sX + sY * bitmap.width].alpha != 0.0)
                                {
                                    shadowMap[(sX + x * 16) + (sY + y * 16) * screenX * 16] = ShadowType.TREE_FADE.type;
                                }
                            }
                        }
                    }
                }
            }
        }

        if (Timefall.getSettings().isDynamicLightEnabled())
        {
            // Update shadowgrid
            for (int x = 0; x < screenX; x++)
            {
                for (int y = 0; y < screenY; y++)
                {
                    Bitmap bitmap = groundTiles[x][y];
                    int[] shadows = new int[bitmap.width * bitmap.height];

                    int sX = 0, sY = 0;

                    for (int i = x * 16; i < x * 16 + bitmap.width; i++)
                    {
                        for (int j = y * 16; j < y * 16 + bitmap.height; j++)
                        {
                            shadows[sX + sY * bitmap.width] = this.shadowMap[i + j * screenX * 16];
                            sY++;
                        }
                        sX++;
                        sY = 0;
                    }

                    this.shadowGrid[x][y] = shadows;
                }
            }
        }

        System.out.println("World bitmap updated");
    }

    public void render(Screen screen)
    {
        screen.clearLights();
        updateAnimations();

        int x = 0;

        //Loop through the base layer and render it
        for (int[] row : tileManager.getBaseLayer())
        {
            int y = 0;
            for (int column : row)
            {
                screen.draw(groundTiles[x][y], x * 16 - tileManager.getCurrentWorld().getX(), y * 16 - tileManager.getCurrentWorld().getY(), shadowGrid[x][y]);
                y++;
            }

            x++;
        }

        // TODO: Remove debug code
        /*for (Rectangle rectangle : tileManager.getCurrentWorld().getCollisions())
        {
            for (int j = (int) rectangle.getX(); j < rectangle.getWidth() + rectangle.getX(); j++)
            {
                for (int z = (int) rectangle.getY(); z < rectangle.getHeight() + rectangle.getY(); z++)
                {
                    screen.draw(Colour.BLACK, j - tileManager.getCurrentWorld().getX(), z - tileManager.getCurrentWorld().getY());
                }
            }
        }*/

        screen.drawLight(testLight2, 80 - tileManager.getCurrentWorld().getX(), 80 - tileManager.getCurrentWorld().getY());
        screen.drawLight(testLight, Timefall.getTileManager().getEntityManager().getPlayer().xOff + (Timefall.getTileManager().getEntityManager().getPlayer().getCurrentBitmap().width / 2), Timefall.getTileManager().getEntityManager().getPlayer().yOff + (Timefall.getTileManager().getEntityManager().getPlayer().getCurrentBitmap().height / 2));
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
