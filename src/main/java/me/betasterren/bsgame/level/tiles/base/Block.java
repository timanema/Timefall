package me.betasterren.bsgame.level.tiles.base;

import me.betasterren.bsgame.graphics.Bitmap;

public interface Block extends MapObject {
    public abstract Bitmap[] getAnimations();

    public abstract boolean isSolid();

    public abstract boolean isAnimated();
}
