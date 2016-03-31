package me.betasterren.bsgame.level.tiles;

import me.betasterren.bsgame.graphics.Bitmap;

public interface Block {
    public abstract Bitmap[] getAnimations();

    public abstract Bitmap getSprite(int ID);

    public abstract int[] getBlockID();

    public abstract boolean isSolid();

    public abstract boolean isAnimated();

    public void tick();
}
