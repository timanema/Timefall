package me.timefall.timefall.game.game;

import me.timefall.timefall.graphics.utils.PixelUtils;

public class AmbientLight
{
    //TODO: Make ambient light correspond to the time
    public static int getAmbientLight()
    {
        return PixelUtils.getColour(1, 0.3F, 0.3F, 0.3F);
    }
}
