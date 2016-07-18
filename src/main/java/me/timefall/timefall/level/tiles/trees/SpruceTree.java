package me.timefall.timefall.level.tiles.trees;

import me.timefall.timefall.graphics.Bitmap;
import me.timefall.timefall.graphics.Sprite;
import me.timefall.timefall.level.tiles.base.Tree;

public class SpruceTree implements Tree
{
    @Override
    public Bitmap getSprite(int ID)
    {
        int base = ((ID / 1000) == 0 ? 1 : 1000);

        int x = (ID <= 19 * base ? 4 : 5);
        int y = (ID / base - 17) + (x == 4 ? 6 + (base == 1000 ? 3 : 0) : 3 + (base == 1000 ? 3 : 0));

        return Sprite.terrain[x][y];
    }

    @Override
    public int[] getBlockID()
    {
        return new int[]{
                17, 18, 19, 20, 21, 22,
                17000, 18000, 19000, 20000, 21000, 22000
        };
    }

    @Override
    public int getHeight()
    {
        return 3;
    }

    @Override
    public int getWidth()
    {
        return 2;
    }

    @Override
    public String getName()
    {
        return "Spruce Tree";
    }

    @Override
    public int[] getHex()
    {
        return new int[]{
                0xA2E81D, 0xA2E82D, 0xA2E83D, 0xA2E85D, 0xA2E86D, 0xA2E87D,
                0xA2E71D, 0xA2E72D, 0xA2E73D, 0xA2E75D, 0xA2E76D, 0xA2E77D,
        };
    }

    @Override
    public void tick()
    {

    }

    @Override
    public boolean isSolid()
    {
        return true;
    }
}
