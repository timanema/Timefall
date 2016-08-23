package me.timefall.timefall.graphics;

import me.timefall.timefall.Timefall;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Sprite
{
    // Sprites
    public static final Bitmap[][] characters = getSubImage("/spritesheets/characters.png", 16, 24, 0, 0);
    public static final Bitmap[][] terrain = getSubImage("/spritesheets/terrain.png", 16, 16, 0, 0);
    public static final Bitmap[][] buildings = getSubImage("/spritesheets/buildings.png", 16, 16, 0, 0);
    public static Bitmap[][] completeCharacters;

    public static Bitmap[][] defaultFontHuge = getSubImage("/spritesheets/fonts/default_font_huge.png", 48, 60, 0, 0);
    public static Bitmap[][] defaultFontLarge = getSubImage("/spritesheets/fonts/default_font_large.png", 12, 18, 0, 0);

    public static Bitmap[][] getSubImage(String spriteName, int width, int height, int startX, int startY)
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
                    int[] rgb = sprite.getRGB(startX + x * width, startY + y * height, width, height, null, 0, width);
                    Colour[] colours = new Colour[rgb.length];

                    for (int i = 0; i < rgb.length; i++)
                        colours[i] = new Colour(PixelUtils.getAlpha(rgb[i]), PixelUtils.getRed(rgb[i]), PixelUtils.getGreen(rgb[i]), PixelUtils.getBlue(rgb[i]));

                    bitResult[x][y] = new Bitmap(width, height);

                    bitResult[x][y].draw(colours);
                }

            // Loop through the image and set the colour codes
            ///for (int xTile = 0; xTile < xTiles; xTile++) {
                /*for (int yTile = 0; yTile < yTiles; yTile++) {
                    bitResult[xTile][yTile] = new Bitmap(width * 4, height * 4);

                    for (int x = xTile * width; x < xTile * width + width; x++)
                        for (int y = yTile * height; y < yTile * height + height; y++) {
                            int colour = sprite.getRGB(x, y);

                            bitResult[xTile][yTile].renderPixel(x - xTile * width,
                                    y - yTile * height,
                                    PixelUtils.getAlpha(colour),
                                    PixelUtils.getRed(colour),
                                    PixelUtils.getGreen(colour),
                                    PixelUtils.getBlue(colour));
                        }*/
            // }


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
