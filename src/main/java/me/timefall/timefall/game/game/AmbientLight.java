package me.timefall.timefall.game.game;

import me.timefall.timefall.graphics.utils.PixelUtils;

public class AmbientLight
{
    private final float[] redValues = {0.1F, 2, 3};
    private final float[] greenValues = {0.1F, 2, 3};
    private final float[] blueValues = {0.1F, 2, 3};

    public static int getAmbientLight()
    {
        return PixelUtils.getColour(1, 0.3F, 0.3F, 0.3F);
    }
}
