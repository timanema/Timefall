package me.betasterren.bsgame.graphics;

public class Bitmap {
    public int width, height;
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
        int startX = x;
        int endX = x + bitmap.width;

        int startY = y;
        int endY = y + bitmap.height;

        if (startX < 0) startX = 0;
        if (endX > width) endX = width;
        if (startY < 0) startY = 0;
        if (endY > height) endY = height;

        int absoluteWidth = endX - startX;

        // Check if the bitmap has to be flipped before it's drawn
        if ((mirrorY)) {
            flipDraw(bitmap, x, y);
        } else {
            // Loop through the bitmap
            for (int yy = startY; yy < endY; yy++) {
                int startPoint = (yy - y) * bitmap.width + (startX - x);
                int endPoint = yy * width + startX;
                endPoint -= startPoint;

                for (int xx = startPoint; xx < startPoint + absoluteWidth; xx++) {
                    int colour = bitmap.pixels[xx];

                    // Set the colour code
                    if (colour < 0) pixels[endPoint + xx] = colour;
                }
            }
        }
    }

    public void flipDraw(Bitmap bitmap, int xOffs, int yOffs) {
        // Loop through the bitmap if reverse
        for (int y = 0; y < bitmap.height; y++) {
            int yPix = y + yOffs;
            if (yPix < 0 || yPix >= height) continue;

            for (int x = 0; x < bitmap.width; x++) {
                int xPix = xOffs + bitmap.width - x - 1;
                if (xPix < 0 || xPix >= width) continue;

                int colour = bitmap.pixels[x + y * bitmap.width];

                // Set the colour code
                if (colour < 0) pixels[xPix + yPix * width] = colour;
            }
        }
    }

    public Bitmap flip() {
        // Create a new bitmap
        Bitmap bitmap = new Bitmap(width, height);

        // Loop through the bitmap in reverse
        for (int y = 0; y < bitmap.height; y++) {
            int yPix = y;
            if (yPix < 0 || yPix >= bitmap.height) continue;

            for (int x = 0; x < bitmap.width; x++) {
                int xPix = bitmap.width - x - 1;
                if (xPix < 0 || xPix >= bitmap.width) continue;

                int colour = pixels[x + y * bitmap.width];

                // Set the colour code
                if (colour < 0) bitmap.pixels[xPix + yPix * bitmap.width] = colour;
            }
        }

        return bitmap;
    }

    public Bitmap clone() {
        // Create a new bitmap
        Bitmap clone = new Bitmap(width, height);

        // Render the current pixels onto the clone and return it
        clone.render(this, 0, 0);
        return clone;
    }
}