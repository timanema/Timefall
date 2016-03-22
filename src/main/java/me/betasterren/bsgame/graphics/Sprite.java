package me.betasterren.bsgame.graphics;

import me.betasterren.bsgame.BSGame;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Sprite {
    // Sprites
    public static final Bitmap[][] sprites = getSubImage("spritesheet1", 16, 16, 0, 0);

    public static Bitmap[][] getSubImage(String spriteName, int width , int height, int startX, int startY) {
        try {
            BufferedImage sprite = ImageIO.read(BSGame.class.getResourceAsStream("/" + spriteName + ".png"));
            int xTiles = (sprite.getWidth() - startX) / width;
            int yTiles = (sprite.getHeight() - startY) / height;

            Bitmap[][] bitResult = new Bitmap[xTiles][yTiles];

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
