package me.timefall.timefall.level.tiles.base;

import me.timefall.timefall.graphics.Bitmap;
import me.timefall.timefall.graphics.ShadowType;

public interface Block extends MapObject
{
    public abstract Bitmap[] getAnimations();

    public abstract boolean isAnimated();

    public ShadowType getShadowType();
}
