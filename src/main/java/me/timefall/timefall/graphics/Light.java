package me.timefall.timefall.graphics;

public class Light
{
    private int[] lightMap;
    private int radius, diameter;

    public Light(int colour, int radius)
    {
        this.radius = radius;
        this.diameter = radius * 2;
        this.lightMap = new int[diameter * diameter];

        for (int x = 0; x < diameter; x++)
            for (int y = 0; y < diameter; y++)
            {
                int distanceX = x - radius;
                int distanceY = y - radius;

                // Calculating current distance from centre
                float distanceFromCentre = (float) Math.sqrt(distanceX * distanceX + distanceY * distanceY);

                // Set light in lightmap
                if (distanceFromCentre < radius)
                    lightMap[x + y * diameter] = PixelUtils.getColourPower(colour, 1 - distanceFromCentre / radius);
                else
                    lightMap[x + y * diameter] = 0;
            }
    }

    public int getColour(int x, int y)
    {
        if (x < 0 || y < 0 || x >= diameter || y >= diameter)
        {
            return 0xff000000;
        }

        return lightMap[x + y * diameter];
    }

    public int getRadius()
    {
        return this.radius;
    }

    public int getDiameter()
    {
        return this.diameter;
    }

    public int[] getLightMap()
    {
        return this.lightMap;
    }
}
