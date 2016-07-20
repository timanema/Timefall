package me.timefall.timefall.level.tiles.tiles;

import me.timefall.timefall.graphics.Bitmap;
import me.timefall.timefall.graphics.Sprite;
import me.timefall.timefall.level.tiles.base.Block;

public class SnowTile implements Block
{
    @Override
    public Bitmap[] getAnimations()
    {
        return new Bitmap[0];
    }

    @Override
    public boolean isSolid()
    {
        return false;
    }

    @Override
    public int getShadowType()
    {
        return 0;
    }

    @Override
    public boolean isAnimated()
    {
        return false;
    }

    @Override
    public Bitmap getSprite(int id)
    {
        return Sprite.terrain[6][0];
    }

    @Override
    public int[] getBlockID()
    {
        return new int[]{25};
    }

    @Override
    public int getHeight()
    {
        return 1;
    }

    @Override
    public int getWidth()
    {
        return 1;
    }

    @Override
    public String getName()
    {
        return "Snow";
    }

    @Override
    public int[] getHex()
    {
        return new int[]{0xE0D7D3};
    }

    @Override
    public void tick()
    {

    }
}
