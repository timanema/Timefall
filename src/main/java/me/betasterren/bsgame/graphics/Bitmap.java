package me.betasterren.bsgame.graphics;

public class Bitmap {
    private int width, height;
    public int[] pixels;

    public Bitmap(int width, int height) {
        this.width = width;
        this.height = height;

        this.pixels = new int[width * height];
    }

    public void render(Bitmap bitmap, int x, int y) {
        int startX = x;
        int endX = x + bitmap.width;

        int startY = y;
        int endY = y + bitmap.height;

        if (startX < 0) startX = 0;
        if (endX > width) endX = width;
        if (startY < 0) startY = 0;
        if (endY > height) endY = height;

        int absoluteWidth = endX - startX;

        for (int yy = startY; yy < endY; yy++) {
            int startPoint = (yy - y) * bitmap.width + (startX - x);
            int endPoint = yy * width + startX;
            endPoint -= startPoint;

            for (int xx = startPoint; xx < startPoint + absoluteWidth; xx++) {
                int colour = bitmap.pixels[xx];

                if (colour < 0) pixels[endPoint + xx] = colour;
            }
        }
    }
}