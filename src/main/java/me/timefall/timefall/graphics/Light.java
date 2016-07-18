package me.timefall.timefall.graphics;

public class Light
{
    private int[][] lightMap;
    private int colour, radius, diameter;

    public Light(int colour, int radius)
    {
        this.colour = colour;
        this.radius = radius;
        this.diameter = radius * 2;
        this.lightMap = new int[diameter][diameter];

        for (int x = 0; x < diameter; x++)
            for (int y = 0; y < diameter; y++)
            {
                int distanceX = x - radius;
                int distanceY = y - radius;

                // Calculating current distance from centre
                float distanceFromCentre = (float) Math.sqrt(distanceX * distanceX + distanceY * distanceY);

                // Set light in lightmap
                if (distanceFromCentre < radius)
                    lightMap[x][y] = PixelUtils.getColourPower(colour, 1 - distanceFromCentre / radius);
                else
                    lightMap[x][y] = 0;
            }
    }

    public int getDiameter()
    {
        return diameter;
    }
}
