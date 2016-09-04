package me.timefall.timefall.level.tiles.base;

import me.timefall.timefall.graphics.components.Bitmap;
import me.timefall.timefall.graphics.lighting.ShadowType;

public interface Block extends MapObject
{
    public abstract Bitmap[] getAnimations();

    public abstract boolean isAnimated();

    public ShadowType getShadowType();
}
