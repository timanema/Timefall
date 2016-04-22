package me.betasterren.bsgame.level.tiles;

import me.betasterren.bsgame.graphics.Bitmap;
import me.betasterren.bsgame.graphics.Sprite;
import me.betasterren.bsgame.level.tiles.base.Block;

public class WaterTile implements Block {
    @Override
    public Bitmap getSprite(int ID) {
        switch (ID) {
            case 1:
                return Sprite.sprites[1][1];
            case 2:
                return Sprite.sprites[1][0];
            case 3:
                return Sprite.sprites[2][0];
            case 4:
                return Sprite.sprites[2][1];
            case 5:
                return Sprite.sprites[2][2];
            case 6:
                return Sprite.sprites[1][2];
            case 7:
                return Sprite.sprites[0][2];
            case 8:
                return Sprite.sprites[0][1];
            case 9:
                return Sprite.sprites[0][0];
            default:
                return null;
        }
    }

    @Override
    public int[] getBlockID() {
        return new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9};
    }

    @Override
    public int getHeight() {
        return 0;
    }

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public Bitmap[] getAnimations() {
        return null;
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public boolean isAnimated() {
        return false;
    }

    @Override
    public void tick() {
        // TODO: Make this block animated
    }
}
