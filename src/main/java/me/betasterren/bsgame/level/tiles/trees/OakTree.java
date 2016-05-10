package me.betasterren.bsgame.level.tiles.trees;

import me.betasterren.bsgame.graphics.Bitmap;
import me.betasterren.bsgame.graphics.Sprite;
import me.betasterren.bsgame.level.tiles.base.Tree;

public class OakTree implements Tree {
    @Override
    public Bitmap getSprite(int ID) {
        int base = ((ID / 1000) == 0 ? 1 : 1000);

        int x = (ID <= 12 * base ? 0 : 1);
        int y = (ID - 10 * base) + (x == 0 ? 6 : 3);

        return Sprite.terrain[x][y];
    }

    @Override
    public int[] getBlockID() {
        return new int[]{10, 11, 12, 13, 14, 15,
                10000, 11000, 12000, 13000, 14000, 15000};
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
        return "Oak Tree";
    }

    @Override
    public int[] getHex() {
        return new int[]{0x2A7D3B, 0x2A7D3C, 0x2A7D4B, 0x2A7D5A, 0x1A7D3B, 0x3A7D3B,
                0x2A8D3B, 0x2A8D3C, 0x2A8D4B, 0x2A8D5A, 0x1A8D3B, 0x3A8D3B};
    }

    @Override
    public void tick() {

    }

    @Override
    public boolean isSolid() {
        return true;
    }
}
