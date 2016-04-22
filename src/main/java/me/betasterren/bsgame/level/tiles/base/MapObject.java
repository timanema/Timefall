package me.betasterren.bsgame.level.tiles.base;

import me.betasterren.bsgame.graphics.Bitmap;

public interface MapObject {
    public abstract Bitmap getSprite(int id);
    public abstract int[] getBlockID();
    public abstract int getHeight();
    public abstract int getWidth();
    public abstract String getName();

    public void tick();
}
