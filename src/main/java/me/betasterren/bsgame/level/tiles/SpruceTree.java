package me.betasterren.bsgame.level.tiles;

import me.betasterren.bsgame.graphics.Bitmap;
import me.betasterren.bsgame.graphics.Sprite;
import me.betasterren.bsgame.level.tiles.base.Tree;

public class SpruceTree implements Tree {
    @Override
    public Bitmap getSprite(int ID) {
        int x = (ID <= 12 ? 6 : 7);
        int y = (ID - 10) - (x == 6 ? 0 : 3);

        return Sprite.sprites[x][y];
    }

    @Override
    public int[] getBlockID() {
        return new int[] {10, 11, 12, 13, 14, 15};
    }

    @Override
    public int getWidth() {
        return 2;
    }

    @Override
    public int getHeight() {
        return 3;
    }

    @Override
    public String getName() {
        return "Spruce Tree";
    }

    @Override
    public void tick() {

    }

}
