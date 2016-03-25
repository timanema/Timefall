package me.betasterren.bsgame.graphics;

import java.util.Arrays;

public class Bitmap {
    private int width, height;
    public int[] pixels;

    public Bitmap(int width, int height) {
        this.width = width;
        this.height = height;

        this.pixels = new int[width * height];
    }

    public Bitmap removeRow(int rows, boolean startFromLeft) {
        Bitmap bitmapCopy = new Bitmap(width - rows, height);
        int[] pixelsCopy = new int[width * height];
        int[] newPixels = null;

        System.arraycopy(pixels, 0, pixelsCopy, 0, pixels.length);

        if (startFromLeft) {

        } else {

        }

        System.arraycopy(newPixels, 0, bitmapCopy.pixels, 0, newPixels.length);
        return bitmapCopy;
    }

    public Bitmap removeColumn(int columns, boolean startFromAbove) {
        Bitmap bitmapCopy = new Bitmap(width, height - columns);
        int[] pixelsCopy = new int[width * height];
        int[] newPixels = null;

        System.arraycopy(pixels, 0, pixelsCopy, 0, pixels.length);

        if (startFromAbove) {
             newPixels = Arrays.copyOfRange(pixelsCopy, width * columns + 1, pixelsCopy.length);
        } else {
            newPixels = Arrays.copyOfRange(pixelsCopy, 0, pixelsCopy.length - width * columns);
        }

        /*
        Rekenvoorbeeldje voor me, mag je negeren

        00  01  02  03  04  05  06  07  08  09  10  11  12  13  14  15
        16  17  18  19  20  21  22  23  24  25  26  27  28  29  30  31
        32  33  34  35  36  37  38  39  40  41  42  43  44  45  46  47
        48  49  50  51  52  53  54  55  56  57  58  59  60  61  62  63
        64  65  66  67  68  69  70  71  72  73  74  75  76  77  78  79
         */

        System.arraycopy(newPixels, 0, bitmapCopy.pixels, 0, newPixels.length);
        return bitmapCopy;
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