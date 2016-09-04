package me.timefall.timefall.level;

public enum Direction
{
    NORTH(0, -3), NORTHEAST(2, -2), NORTHWEST(-2, -2), EAST(3, 0), SOUTH(0, 3), SOUTHEAST(2, 2), SOUTHWEST(-2, 2), WEST(-3, 0);

    int xChange;
    int yChange;

    private Direction(int xChange, int yChange)
    {
        this.xChange = xChange;
        this.yChange = yChange;
    }

    public int getxChange()
    {
        return xChange;
    }

    public int getyChange()
    {
        return yChange;
    }
}
