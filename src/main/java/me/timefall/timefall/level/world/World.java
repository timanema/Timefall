package me.timefall.timefall.level.world;

import me.timefall.timefall.Timefall;
import me.timefall.timefall.entities.Entity;
import me.timefall.timefall.level.TileManager;
import me.timefall.timefall.level.tiles.base.Block;
import me.timefall.timefall.level.tiles.base.MapObject;
import me.timefall.timefall.level.tiles.base.Tree;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class World
{
    private TileManager tileManager;

    private String worldName;
    private int width;
    private int height;

    private int xOff;
    private int yOff;

    public int[][] baseLayer;
    private int[][] floraLayer;

    private Entity[] entities;
    private ArrayList<Rectangle> collisions;

    public World(TileManager tileManager, String worldName, int xOff, int yOff, String mapPath, String entityPath)
    {
        this.tileManager = tileManager;
        this.worldName = worldName;
        this.xOff = xOff;
        this.yOff = yOff;
        this.collisions = new ArrayList<>();

        System.out.println("   Loading world: " + worldName);
        this.initWorld(mapPath, entityPath);
    }

    private void initWorld(String mapPath, String enityPath)
    {
        // Trying to read the image
        try
        {
            System.out.println("    Trying to load map file ...");

            BufferedImage[] bufferedImages = new BufferedImage[]{
                    ImageIO.read(Timefall.class.getResourceAsStream(mapPath)),
                    ImageIO.read(Timefall.class.getResourceAsStream(
                            mapPath.replaceAll(worldName + ".png", worldName + "_flora.png")
                            )
                    )
            };
            BufferedImage mapImage = bufferedImages[0];
            BufferedImage floraImage = bufferedImages[1];

            this.width = mapImage.getWidth();
            this.height = mapImage.getHeight();

            baseLayer = new int[width][height];
            floraLayer = new int[width][height];

            System.out.println("    Getting tiles from hex codes ...");

            // Checking if the images are the same size
            if (floraImage.getWidth() != width || floraImage.getHeight() != height)
            {
                System.out.println("FAILED TO LOAD WORLD (" + worldName + ")! " +
                        "FLORA IMAGE IS NOT THE SAME SIZE AS MAP IMAGE");

                Timefall.getFileManager().worldFiles.remove(worldName);

                // Check if there are any other worlds left
                if (Timefall.getFileManager().worldFiles.isEmpty())
                {
                    System.out.println("NO WORLDS LEFT, ABORTING!");
                    System.exit(-1);
                }
            }

            // Loop through all the images
            for (BufferedImage image : bufferedImages)
            {
                // Looping through the base image
                for (int x = 0; x < width; x++)
                    for (int y = 0; y < height; y++)
                    {
                        // Getting the hex code and the MapObject
                        int rgbCode = image.getRGB(x, y);
                        int hexCode = rgbCode & 0xFFFFFF;
                        MapObject mapObject = tileManager.getMapObjectByHex(hexCode);

                        // Check for collision detection
                        if (mapObject != null &&
                                mapObject.isSolid())
                        {
                            this.collisions.add(new Rectangle(x * 16, y * 16, 16, 16));
                        }

                        // Check if the MapObject is a block
                        if (tileManager.checkBlock(mapObject))
                        {
                            Block block = (Block) mapObject;
                            int[] blockID = block.getBlockID();
                            int[] blockHex = block.getHex();

                            // Get the correct ID and set it in the base layer
                            for (int i = 0; i < blockID.length; i++)
                                if (blockHex[i] == hexCode)
                                {
                                    baseLayer[x][y] = blockID[i];
                                    break;
                                }

                            // Check if the MapObject is a tree
                        } else if (tileManager.checkTree(mapObject))
                        {
                            Tree block = (Tree) mapObject;
                            int[] blockID = block.getBlockID();
                            int[] blockHex = block.getHex();

                            // Get the correct ID and set it in the base layer
                            for (int i = 0; i < blockID.length; i++)
                                if (blockHex[i] == hexCode)
                                {
                                    floraLayer[x][y] = blockID[i];
                                    break;
                                }
                        } else if (hexCode == 0x002D2A)
                        {
                            // Do nothing, no flora on this tile
                        } else if (hexCode == 0xBF52B1)
                        {
                            // Conflict tile
                            floraLayer[x][y] = 666999;
                        } else
                        {
                            // MapObject has an unknown hex value
                            baseLayer[x][y] = 123456789;
                            System.out.println("Unknown hex code: " + hexCode);
                        }
                    }
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public String getWorldName()
    {
        return worldName;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    public Entity[] getEntities()
    {
        return entities;
    }

    public int getX()
    {
        return xOff;
    }

    public int getY()
    {
        return yOff;
    }

    public void setOffset(int xOff, int yOff)
    {
        this.xOff = xOff;
        this.yOff = yOff;
    }

    public int[][] getBaseLayer()
    {
        return baseLayer;
    }

    public int[][] getFloraLayer()
    {
        return floraLayer;
    }

    public ArrayList<Rectangle> getCollisions() {
        return this.collisions;
    }
}
