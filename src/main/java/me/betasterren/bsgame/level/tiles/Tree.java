package me.betasterren.bsgame.level.tiles;

import me.betasterren.bsgame.graphics.Bitmap;

public interface Tree {
    public abstract Bitmap getSprite(int ID);
    public abstract int[] getBlockID();
    public abstract int getWidth();
    public abstract int getHeight();
}
