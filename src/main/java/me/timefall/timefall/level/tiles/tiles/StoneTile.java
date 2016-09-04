package me.timefall.timefall.level.tiles.tiles;

import me.timefall.timefall.graphics.components.Bitmap;
import me.timefall.timefall.graphics.lighting.ShadowType;
import me.timefall.timefall.graphics.components.Sprite;
import me.timefall.timefall.level.tiles.base.Block;

public class StoneTile implements Block
{
    @Override
    public Bitmap[] getAnimations()
    {
        return new Bitmap[0];
    }

    @Override
    public boolean isSolid()
    {
        //TODO: Remove debug code
        return true;
    }

    @Override
    public ShadowType getShadowType()
    {
        return ShadowType.FADE;
    }

    @Override
    public boolean isAnimated()
    {
        return false;
    }

    @Override
    public Bitmap getSprite(int id)
    {
        return Sprite.terrain[1][4];
    }

    @Override
    public int[] getBlockID()
    {
        return new int[]{16};
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
        return "Stone";
    }

    @Override
    public int[] getHex()
    {
        return new int[]{0x808080};
    }

    @Override
    public void tick()
    {

    }
}
