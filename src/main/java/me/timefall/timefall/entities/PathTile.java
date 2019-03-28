package me.timefall.timefall.entities;

public class PathTile {
    public int x;
    public int y;

    public double gScore;
    public double fScore;

    PathTile cameFrom;

    public PathTile(int x, int y)
    {
        this.x = x;
        this.y = y;
        this.gScore = Double.POSITIVE_INFINITY;
        this.fScore = Double.POSITIVE_INFINITY;
    }

    public PathTile(int[] coordinate)
    {
        this.x = coordinate[0];
        this.y = coordinate[1];
        this.gScore = Double.POSITIVE_INFINITY;
        this.fScore = Double.POSITIVE_INFINITY;
    }

    public void setCameFrom(PathTile cameFrom) {
        this.cameFrom = cameFrom;
    }

    public double getgScore() {
        return gScore;
    }

    public double getfScore()
    {
        return fScore;
    }

    public int[] getCoordinate()
    {
        return new int[]{x, y};
    }
}
