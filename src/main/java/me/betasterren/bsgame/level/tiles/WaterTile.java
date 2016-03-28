package me.betasterren.bsgame.level.tiles;

import me.betasterren.bsgame.graphics.Bitmap;
import me.betasterren.bsgame.graphics.Sprite;

public class WaterTile implements Block {
    @Override
    public Bitmap getSprite() {
        return Sprite.sprites[1][1];
    }

    @Override
    public int getBlockID() {
        return 1;
    }

    @Override
    public boolean isSolid() {
        return false;
    }
}
