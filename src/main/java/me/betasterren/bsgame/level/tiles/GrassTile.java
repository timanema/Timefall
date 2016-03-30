package me.betasterren.bsgame.level.tiles;

import me.betasterren.bsgame.graphics.Bitmap;
import me.betasterren.bsgame.graphics.Sprite;

public class GrassTile implements Block {
    @Override
    public Bitmap[] getAnimations() {
        return null;
    }

    @Override
    public Bitmap getSprite(int ID) {
        return Sprite.sprites[4][1];
    }

    @Override
    public int[] getBlockID() {
        return new int[] {0};
    }

    @Override
    public boolean isSolid() {
        return true;
    }

    @Override
    public boolean isAnimated() {
        return false;
    }

    @Override
    public void tick() {

    }
}
