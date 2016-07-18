package me.timefall.timefall.level.tiles.base;

import me.timefall.timefall.graphics.Bitmap;

public interface Block extends MapObject
{
    public abstract Bitmap[] getAnimations();

    public abstract boolean isAnimated();
}
