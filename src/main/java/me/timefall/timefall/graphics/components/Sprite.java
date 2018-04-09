package me.timefall.timefall.graphics.components;

import me.timefall.timefall.Timefall;
import me.timefall.timefall.entities.EntityManager;
import me.timefall.timefall.graphics.utils.PixelUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Sprite
{
    // Sprites
    //  Entities
    public static final Bitmap[][] characters = getSubImage("/spritesheets/characters.png", 16, 24, 0, 0);
    public static Bitmap[][] completeCharacters;
    public static Bitmap[][] npcs;

    //  Terrain
    public static final Bitmap[][] terrain = getSubImage("/spritesheets/terrain.png", 16, 16, 0, 0);
    public static final Bitmap[][] buildings = getSubImage("/spritesheets/buildings.png", 16, 16, 0, 0);

    //  Various
    public static Bitmap[][] defaultFontNormal = getSubImage("/spritesheets/fonts/default_font_normal.png", 12, 18, 0, 0);
    public static Bitmap backgroundImage = getSubImage("/spritesheets/titlescreen_background.png", 1280, 720, 0, 0)[0][0];
    public static Bitmap backgroundImageText = getSubImage("/spritesheets/titlescreen_background_text.png", 1280, 720, 0, 0)[0][0];
    public static Bitmap backgroundImageCloud = getSubImage("/spritesheets/titlescreen_background_clouds.png", 1280, 720, 0, 0)[0][0];
    public static Bitmap[][] startupLogo = getSubImage("/spritesheets/beta-sterren-logo.png", 640, 360, 0, 0);

    public static void loadNPC()
    {
        String basePath = "/spritesheets/npc/*.png";
        npcs = new Bitmap[EntityManager.NPC_AMOUNT][5];

        // Loop through all NPC files
        for (int i = 0; i < EntityManager.NPC_AMOUNT; i++)
        {
            Bitmap[] npcBitmap = new Bitmap[5];
            BufferedImage sprite = null;

            // Load file into array
            try
            {
                sprite = ImageIO.read(Timefall.class.getResourceAsStream(basePath.replace("*", i + "")));
                int spriteHeight = sprite.getHeight() / 5;

                for (int y = 0; y < 5; y++) {
                    int[] rgb = sprite.getRGB(0,
                            y * spriteHeight,
                            sprite.getWidth(),
                            spriteHeight,
                            null,
                            0,
                            sprite.getWidth());
                    Colour[] colours = new Colour[rgb.length];

                    for (int j = 0; j < rgb.length; j++)
                        colours[j] = new Colour(PixelUtils.getAlpha(rgb[j]),
                                PixelUtils.getRed(rgb[j]),
                                PixelUtils.getGreen(rgb[j]),
                                PixelUtils.getBlue(rgb[j]));

                    npcBitmap[y] = new Bitmap(sprite.getWidth(), spriteHeight);

                    npcBitmap[y].draw(colours);
                }

            } catch (IOException e)
            {
                e.printStackTrace();
            } finally
            {
                if (sprite != null)
                    sprite.flush();
            }

            // Copy array to sprite array
            System.arraycopy(npcBitmap, 0, npcs[i], 0, 5);
        }
    }

    private static Bitmap[][] getSubImage(String spriteName, int width, int height, int startX, int startY)
    {
        // Trying to read the image and get its width and height
        BufferedImage sprite = null;
        try
        {
            sprite = ImageIO.read(Timefall.class.getResourceAsStream(spriteName));
            int xTiles = (sprite.getWidth() - startX) / width;
            int yTiles = (sprite.getHeight() - startY) / height;

            Bitmap[][] bitResult = new Bitmap[xTiles][yTiles];

            for (int x = 0; x < xTiles; x++)
                for (int y = 0; y < yTiles; y++)
                {
                    int[] rgb = sprite.getRGB(startX + x * width,
                            startY + y * height,
                            width,
                            height,
                            null,
                            0,
                            width);
                    Colour[] colours = new Colour[rgb.length];

                    for (int i = 0; i < rgb.length; i++)
                        colours[i] = new Colour(PixelUtils.getAlpha(rgb[i]),
                                PixelUtils.getRed(rgb[i]),
                                PixelUtils.getGreen(rgb[i]),
                                PixelUtils.getBlue(rgb[i]));

                    bitResult[x][y] = new Bitmap(width, height);

                    bitResult[x][y].draw(colours);
                }

            return bitResult;
        } catch (IOException e)
        {
            e.printStackTrace();
        } finally
        {
            if (sprite != null)
                sprite.flush();
        }
        return null;
    }
}
