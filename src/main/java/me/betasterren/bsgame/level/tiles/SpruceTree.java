package me.betasterren.bsgame.level.tiles;

import me.betasterren.bsgame.graphics.Bitmap;
import me.betasterren.bsgame.graphics.Sprite;

public class SpruceTree implements Tree {
    @Override
    public Bitmap getSprite(int ID) {
        switch (ID) {
            case 10:
                return Sprite.sprites[6][0];
            case 11:
                return Sprite.sprites[6][1];
            case 12:
                return Sprite.sprites[6][2];
            case 13:
                return Sprite.sprites[7][0];
            case 14:
                return Sprite.sprites[7][1];
            case 15:
                return Sprite.sprites[7][2];
        }
        return null;
    }

    @Override
    public int[] getBlockID() {
        return new int[] {10, 11, 12, 13, 14, 15};
    }

    @Override
    public int getWidth() {
        return 2;
    }

    @Override
    public int getHeight() {
        return 3;
    }

}
