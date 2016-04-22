package me.betasterren.bsgame.level.tiles;

import me.betasterren.bsgame.graphics.Bitmap;
import me.betasterren.bsgame.level.tiles.base.Block;

public class VoidTile implements Block {
    @Override
    public Bitmap[] getAnimations() {
        return new Bitmap[0];
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
    public Bitmap getSprite(int id) {
        return null;
    }

    @Override
    public int[] getBlockID() {
        return new int[]{123456789};
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
        return "Void";
    }

    @Override
    public int[] getHex() {
        return new int[]{0x000000};
    }

    @Override
    public void tick() {

    }
}
