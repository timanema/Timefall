package me.timefall.timefall.graphics;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Screen extends Bitmap
{
    public BufferedImage bufferedImage;

    public Screen(int width, int height)
    {
        super(width, height);

        // Create a BufferedImage
        this.bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    }

    public void update()
    {
        int[] pixelInts = ((DataBufferInt) bufferedImage.getRaster().getDataBuffer()).getData();

        for (int i = 0, j = 0; i < pixelInts.length; i++) {
            int a = pixels[j++] & 0xff;
            int b = pixels[j++] & 0xff;
            int g = pixels[j++] & 0xff;
            int r = pixels[j++] & 0xff;
            pixelInts[i] = (a << 24) | (r << 16) | (g << 8) | b;
        }
    }
}
