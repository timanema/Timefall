package me.betasterren.bsgame.level;

public class Vector {
    private float xPos, yPos;
    public static float worldxPos, worldyPos;

    public Vector() {
        this.xPos = 0.0F;
        this.yPos = 0.0F;
    }

    public Vector(float xPos, float yPos) {
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

    public Vector add(Vector vector) {
        xPos += vector.xPos;
        yPos += vector.yPos;

        return new Vector(xPos, yPos);
    }

    public Vector subtract(Vector vector) {
        xPos -= vector.xPos;
        yPos -= vector.yPos;

        return new Vector(xPos, yPos);
    }

    public Vector copy(Vector vector) {
        xPos = vector.xPos;
        yPos = vector.yPos;

        return new Vector(xPos, yPos);
    }

    public static void setWorldVariables(float xPos, float yPos) {
        worldxPos = xPos;
        worldyPos = yPos;
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
}