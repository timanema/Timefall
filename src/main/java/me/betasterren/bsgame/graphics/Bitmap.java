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
        render(bitmap, x, y, false);
    }

    public void render(Bitmap bitmap, int x, int y, boolean mirrorY) {
        int startX = x; // 96
        int endX = x + bitmap.width; // 112

        int startY = y; // 0
        int endY = y + bitmap.height; // 16

        if (startX < 0) startX = 0;
        if (endX > width) endX = width;
        if (startY < 0) startY = 0;
        if (endY > height) endY = height;

        int absoluteWidth = endX - startX; // 16

        if ((mirrorY)) {
            flipDraw(bitmap, x, y);
        } else {
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

    public void flipDraw(Bitmap bitmap, int xOffs, int yOffs) {
        for (int y = 0; y < bitmap.height; y++) {
            int yPix = y + yOffs;
            if (yPix < 0 || yPix >= height) continue;

            for (int x = 0; x < bitmap.width; x++) {
                int xPix = xOffs + bitmap.width - x - 1;
                if (xPix < 0 || xPix >= width) continue;

                int colour = bitmap.pixels[x + y * bitmap.width];

                if (colour < 0) pixels[xPix + yPix * width] = colour;
            }
        }
    }
}