package me.timefall.timefall.graphics;

import me.timefall.timefall.game.game.AmbientLight;

public class PixelUtils
{
    private static final int ALPHA_SHIFT = 24;
    private static final int RED_SHIFT = 16;
    private static final int GREEN_SHIFT = 8;

    public static int getColour(float alpha, float red, float green, float blue)
    {
        return ((int) (alpha * 255F + 0.5F) << ALPHA_SHIFT | (int) (red * 255F + 0.5F) << RED_SHIFT | (int) (green * 255F + 0.5F) << GREEN_SHIFT | (int) (blue * 255F + 0.5F));
    }

    public static int getColour(Colour colour)
    {
        return getColour(colour.alpha, colour.red, colour.green, colour.blue);
    }

    public static Colour getColour(int colour)
    {
        return new Colour(getAlpha(colour), getRed(colour), getGreen(colour), getBlue(colour));
    }

    public static int getColourPower(int colour, float power)
    {
        return getColour(1F, getRed(colour) * power, getGreen(colour) * power, getBlue(colour) * power);
    }

    public static float getAlpha(int colour)
    {
        return (0xff & (colour >> ALPHA_SHIFT)) / 255F;
    }

    public static float getRed(int colour)
    {
        return (0xff & (colour >> RED_SHIFT)) / 255F;
    }

    public static float getGreen(int colour)
    {
        return (0xff & (colour >> GREEN_SHIFT)) / 255F;
    }

    public static float getBlue(int colour)
    {
        return (0xff & (colour)) / 255F;
    }

    public static int getMax(int colour, int blend)
    {
        return getColour(1, Math.max(getRed(colour), getRed(blend)), Math.max(getGreen(colour), getGreen(blend)), Math.max(getBlue(colour), getBlue(blend)));
    }

    public static int blendColours(int colour, int blend)
    {
        int ambientLight = AmbientLight.getAmbientLight();
        float redBlend = getRed(blend) < getRed(ambientLight) ? getRed(ambientLight) : getRed(blend);
        float blueBlend = getBlue(blend) < getBlue(ambientLight) ? getBlue(ambientLight) : getBlue(blend);
        float greenBlend = getGreen(blend) < getGreen(ambientLight) ? getGreen(ambientLight) : getGreen(blend);

        return getColour(1F, getRed(colour) * redBlend, getGreen(colour) * greenBlend, getBlue(colour) * blueBlend );
    }
}