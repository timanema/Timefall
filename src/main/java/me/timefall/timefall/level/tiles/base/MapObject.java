package me.timefall.timefall.level.tiles.base;

import me.timefall.timefall.graphics.Bitmap;

public interface MapObject
{
    public abstract Bitmap getSprite(int id);

    public abstract int[] getBlockID();

    public abstract int getHeight();

    public abstract int getWidth();

    public abstract String getName();

    public abstract int[] getHex();

    public void tick();

    public boolean isSolid();
}
