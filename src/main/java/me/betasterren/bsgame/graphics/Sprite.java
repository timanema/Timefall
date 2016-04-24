package me.betasterren.bsgame.graphics;

import me.betasterren.bsgame.BSGame;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Sprite {
    // Sprites
    public static final Bitmap[][] playerBud = getSubImage("/spritesheets/bud.png", 15, 19, 0, 0);
    public static final Bitmap[][] sprites = getSubImage("/spritesheets/spritesheet1.png", 16, 16, 0, 0);
    public static final Bitmap[][] buildings = getSubImage("/spritesheets/buildings.png", 16, 16, 0, 0);

    public static Bitmap[][] getSubImage(String spriteName, int width , int height, int startX, int startY) {
        // Trying to read the image and get its width and height
        try {
            BufferedImage sprite = ImageIO.read(BSGame.class.getResourceAsStream(spriteName));
            int xTiles = (sprite.getWidth() - startX) / width;
            int yTiles = (sprite.getHeight() - startY) / height;

            Bitmap[][] bitResult = new Bitmap[xTiles][yTiles];

            // Loop through the image and set the colour codes
            for (int x = 0; x < xTiles; x++)
                for (int y = 0; y < yTiles; y++) {
                    bitResult[x][y] = new Bitmap(width, height);

                    sprite.getRGB(startX + x * width, startY + y * height, width, height, bitResult[x][y].pixels, 0, width);
                }

            return bitResult;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
