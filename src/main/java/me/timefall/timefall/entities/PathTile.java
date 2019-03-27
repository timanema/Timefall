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
    }

    public PathTile(Integer[] coordinate)
    {
        this.x = coordinate[0];
        this.y = coordinate[1];
    }

    public void calculatefScore(PathTile tile2)
    {
        gScore = diagonalDistance(this, tile2);
    }

    private double diagonalDistance(PathTile tile1, PathTile tile2)
    {
        int x = Math.abs(tile1.x - tile2.x);
        int y = Math.abs(tile1.y - tile2.y);

        return Math.sqrt(x*x + y*y);
    }

    public double getgScore() {
        return gScore;
    }

    public double getfScore()
    {
        return fScore;
    }
}
