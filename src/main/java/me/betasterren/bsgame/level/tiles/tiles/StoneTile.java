package me.betasterren.bsgame.level.tiles.tiles;

import me.betasterren.bsgame.graphics.Bitmap;
import me.betasterren.bsgame.graphics.Sprite;
import me.betasterren.bsgame.level.tiles.base.Block;

public class StoneTile implements Block {
    @Override
    public Bitmap[] getAnimations() {
        return new Bitmap[0];
    }

    @Override
    public boolean isSolid() {
        //TODO: Remove debug code
        return true;
    }

    @Override
    public boolean isAnimated() {
        return false;
    }

    @Override
    public Bitmap getSprite(int id) {
        return Sprite.terrain[1][4];
    }

    @Override
    public int[] getBlockID() {
        return new int[] {16};
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
        return "Stone";
    }

    @Override
    public int[] getHex() {
        return new int[] {0x808080};
    }

    @Override
    public void tick() {

    }
}
