package me.betasterren.bsgame.level.tiles;

import me.betasterren.bsgame.graphics.Bitmap;
import me.betasterren.bsgame.graphics.Sprite;

public class GrassTile implements Block {
    /*public GrassTile(Vector position, Bitmap sprite, int blockID) {

    }*/

    @Override
    public Bitmap getSprite() {
        return Sprite.sprites[4][1];
    }

    @Override
    public int getBlockID() {
        return 0;
    }
}
