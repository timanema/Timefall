package me.timefall.timefall.level.tiles.tiles;

import me.timefall.timefall.graphics.components.Bitmap;
import me.timefall.timefall.graphics.lighting.ShadowType;
import me.timefall.timefall.graphics.components.Sprite;
import me.timefall.timefall.level.tiles.base.Block;

public class SandTile implements Block
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
    public ShadowType getShadowType()
    {
        return ShadowType.NONE;
    }

    @Override
    public boolean isAnimated()
    {
        return false;
    }

    @Override
    public Bitmap getSprite(int id)
    {
        return Sprite.terrain[7][0];
    }

    @Override
    public int[] getBlockID()
    {
        return new int[]{26};
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
        return "Sand";
    }

    @Override
    public int[] getHex()
    {
        return new int[]{0xCCC791};
    }

    @Override
    public void tick()
    {

    }
}
