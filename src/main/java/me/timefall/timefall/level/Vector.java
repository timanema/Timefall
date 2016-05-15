package me.timefall.timefall.level;

public class Vector {
    private float xPos, yPos;
    private String worldName;

    public static float worldxPos, worldyPos, playerxPos, playeryPos;
    public static String globalWorldName;

    public Vector() {
        this.xPos = 0.0F;
        this.yPos = 0.0F;
    }

    public Vector(float xPos, float yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public Vector(String worldName, float xPos, float yPos) {
        this.worldName = worldName;
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public void normalize() {
        double length = Math.sqrt(xPos * xPos + yPos * yPos);

        if (length != 0.0) {
            float delta = 1.0F / (float) length;

            xPos *= delta;
            yPos *= delta;
        }
    }

    public Vector zeroVector() {
        return new Vector();
    }

    public boolean equals(Vector vector) {
        return (xPos == vector.xPos && yPos == vector.yPos);
    }

    public void add(Vector vector) {
        xPos += vector.xPos;
        yPos += vector.yPos;
    }

    public void add(float xOff, float yOff) {
        xPos += xOff;
        yPos += yOff;
    }

    public void subtract(Vector vector) {
        xPos -= vector.xPos;
        yPos -= vector.yPos;
    }

    public Vector clone() {
        return new Vector(xPos, yPos);
    }

    public void setLocation(String worldName, float xPos, float yPos) {
        this.worldName = worldName;
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public static void setGlobalWorldName(String newWorldName) {
        globalWorldName = newWorldName;
    }

    public static void setWorldVariables(float xPos, float yPos) {
        worldxPos = xPos;
        worldyPos = yPos;
    }

    public static void setPlayerVariables(float xPos, float yPos) {
        playerxPos = xPos;
        playeryPos = yPos;
    }

    public double getDistance(Vector vector) {
        float difX = Math.abs(xPos - vector.xPos);
        float difY = Math.abs(yPos - vector.yPos);

        return Math.abs(difX * difX + difY * difY);
    }

    public Vector getWorldLocation() {
        return new Vector(worldxPos, worldyPos);
    }

    public float getX() {
        return xPos;
    }

    public float getY() {
        return yPos;
    }

    public String getWorldName() {
        return worldName;
    }
}
