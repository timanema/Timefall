package me.timefall.timefall.graphics;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Screen extends Bitmap {
    public BufferedImage bufferedImage;

    public Screen(int width, int height) {
        super(width, height);

        // Create a BufferedImage
        this.bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        // Get the pixels of the BufferedImage
        pixels = ((DataBufferInt) bufferedImage.getRaster().getDataBuffer()).getData();
    }
}
