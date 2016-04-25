package me.betasterren.bsgame.level.tiles.tiles;

import me.betasterren.bsgame.graphics.Bitmap;
import me.betasterren.bsgame.graphics.Sprite;
import me.betasterren.bsgame.level.tiles.base.Block;

public class SandTile implements Block {
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
        return Sprite.terrain[7][0];
    }

    @Override
    public int[] getBlockID() {
        return new int[] {26};
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
        return "Sand";
    }

    @Override
    public int[] getHex() {
        return new int[] {0xCCC791};
    }

    @Override
    public void tick() {

    }
}
