package me.betasterren.bsgame.level.tiles;

import me.betasterren.bsgame.graphics.Bitmap;

public interface Block {
    //public abstract Vector getPosition();
    public abstract Bitmap getSprite();
    public abstract int getBlockID();
}
