package me.timefall.timefall.level.tiles.tiles;

import me.timefall.timefall.graphics.components.Bitmap;
import me.timefall.timefall.graphics.lighting.ShadowType;
import me.timefall.timefall.graphics.components.Sprite;
import me.timefall.timefall.level.tiles.base.Block;

public class GrassTile implements Block
{
    @Override
    public Bitmap[] getAnimations()
    {
        return null;
    }

    @Override
    public Bitmap getSprite(int ID)
    {
        return Sprite.terrain[4][1];
    }

    @Override
    public int[] getBlockID()
    {
        return new int[]{0};
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
        return "Grass";
    }

    @Override
    public int[] getHex()
    {
        return new int[]{0x3BE329};
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
    public void tick()
    {

    }

    public static Bitmap spr()
    {
        return Sprite.terrain[6][0];
    }
}
