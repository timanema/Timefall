package me.betasterren.bsgame.level;

public enum Direction {
    NORTH(0, -1), NORTHEAST(1, 1), NORTHWEST(-1, 1), EAST(1, 0), SOUTH(0, 1), SOUTHEAST(1, -1), SOUTHWEST(-1, -1), WEST(-1, 0);

    int xChange;
    int yChange;

    private Direction(int xChange, int yChange) {
        this.xChange = xChange;
        this.yChange = yChange;
    }

    public int getxChange() {
        return xChange;
    }

    public int getyChange() {
        return yChange;
    }
}
