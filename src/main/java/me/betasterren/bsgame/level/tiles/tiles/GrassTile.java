package me.betasterren.bsgame.level.tiles.tiles;

import me.betasterren.bsgame.graphics.Bitmap;
import me.betasterren.bsgame.graphics.Sprite;
import me.betasterren.bsgame.level.tiles.base.Block;

public class GrassTile implements Block {
    @Override
    public Bitmap[] getAnimations() {
        return null;
    }

    @Override
    public Bitmap getSprite(int ID) {
        return Sprite.terrain[4][1];
    }

    @Override
    public int[] getBlockID() {
        return new int[]{0};
    }

    @Override
    public int getHeight() {
        return 1;
    }

    @Override
    public int getWidth() {
        return 1;
    }

    @Override
    public String getName() {
        return "Grass";
    }

    @Override
    public int[] getHex() {
        return new int[]{0x3BE329};
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

    }
}
