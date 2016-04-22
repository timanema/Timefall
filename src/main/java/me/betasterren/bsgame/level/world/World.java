package me.betasterren.bsgame.level.world;

import me.betasterren.bsgame.BSGame;
import me.betasterren.bsgame.entities.Entity;
import me.betasterren.bsgame.level.TileManager;
import me.betasterren.bsgame.level.tiles.base.Block;
import me.betasterren.bsgame.level.tiles.base.MapObject;
import me.betasterren.bsgame.level.tiles.base.Tree;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class World {
    private TileManager tileManager;

    private String worldName;
    private int width;
    private int height;

    private int xOff;
    private int yOff;

    private int[][] baseLayer;
    private int[][] floraLayer;

    private Entity[] entities;

    public World(TileManager tileManager, String worldName, int xOff, int yOff, String mapPath, String entityPath) {
        this.tileManager = tileManager;
        this.worldName = worldName;
        this.xOff = xOff;
        this.yOff = yOff;

        System.out.println("   Loading world: " + worldName);
        this.initWorld(mapPath, entityPath);
    }

    private void initWorld(String mapPath, String enityPath) {
        try {
            System.out.println("    Trying to load map file ...");

            BufferedImage mapImage = ImageIO.read(BSGame.class.getResourceAsStream(mapPath));
            this.width = mapImage.getWidth();
            this.height = mapImage.getHeight();

            baseLayer = new int[width][height];
            floraLayer = new int[width][height];

            System.out.println("    Getting tiles from hex codes ...");

            for (int x = 0; x < width; x++)
                for (int y = 0; y < height; y++) {
                    int rgbCode = mapImage.getRGB(x, y);
                    int hexCode = rgbCode & 0xFFFFFF;
                    MapObject mapObject = tileManager.getMapObjectByHex(hexCode);

                    if (tileManager.checkBlock(mapObject)) {
                        Block block = (Block) mapObject;
                        int[] blockID = block.getBlockID();
                        int[] blockHex = block.getHex();

                        for (int i = 0; i < blockID.length; i++)
                            if (blockHex[i] == hexCode) {
                                baseLayer[x][y] = blockID[i];
                                break;
                            }

                    } else if (tileManager.checkTree(mapObject)) {
                        Tree tree = (Tree) mapObject;
                        int[] blockID = tree.getBlockID();
                        int[] blockHex = tree.getHex();

                        for (int i = 0; i < blockID.length; i++)
                            if (blockHex[i] == hexCode) {
                                floraLayer[x][y] = blockID[i];
                                break;
                            }
                    } else {
                        // MapObject has an unknown hex value
                        baseLayer[x][y] = 123456789;
                    }
                }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getWorldName() {
        return worldName;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Entity[] getEntities() {
        return entities;
    }

    public int getX() {
        return xOff;
    }

    public int getY() {
        return yOff;
    }

    public void setOffset(int xOff, int yOff) {
        this.xOff = xOff;
        this.yOff = yOff;
    }

    public int[][] getBaseLayer() {
        return baseLayer;
    }

    public int[][] getFloraLayer() {
        return floraLayer;
    }
}
