package me.timefall.timefall.game.game;

import me.timefall.timefall.graphics.PixelUtils;

public class AmbientLight
{
    //TODO: Make ambient light correspond to the time
    public static int getAmbientLight()
    {
        return PixelUtils.getColour(1, 0.2F, 0.2F, 0.2F);
    }
}
