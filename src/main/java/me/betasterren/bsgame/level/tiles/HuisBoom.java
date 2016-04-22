package me.betasterren.bsgame.level.tiles;

import me.betasterren.bsgame.graphics.Bitmap;
import me.betasterren.bsgame.graphics.Sprite;
import me.betasterren.bsgame.level.tiles.base.Tree;

public class HuisBoom implements Tree {
    @Override
    public Bitmap getSprite(int ID) {
        int x = ID <= 20 ? 0 : (ID <= 25 ? 1 : (ID <= 30 ? 2 : (ID <= 35 ? 3 : (ID <= 40 ? 4 : 5))));
        int y = (ID - 16) - x * 5;

        return Sprite.buildings[x][y];
    }

    @Override
    public int[] getBlockID() {
        int start = 16;
        int[] IDs = new int[25];

        for (int i = 16; i < 16 + 25; i++)
            IDs[i - 16] = i;
        return IDs;
    }

    @Override
    public int getWidth() {
        return 5;
    }

    @Override
    public int getHeight() {
        return 5;
    }

    @Override
    public String getName() {
        return "Huisboom";
    }

    @Override
    public void tick() {

    }
}

