package me.timefall.timefall.graphics;

public class Bitmap
{
    public int width, height;
    public byte[] pixels;
    public Colour[] colours;

    public Bitmap(int width, int height)
    {
        this.width = width;
        this.height = height;

        this.pixels = new byte[width * height * 4];
        this.colours = new Colour[width * height];
    }

    public void renderPixel(int x, int y, Colour colour)
    {
        if (colour == null)
            return;

        this.renderPixel(x, y, colour.alpha, colour.red, colour.green, colour.blue);
    }

    public void renderPixel(int x, int y, float alpha, float red, float green, float blue)
    {
        if ((x < 0 || y < 0 || x >= width || y >= width) || alpha == 0.0F)
            return;

        // Get index
        int index = (x + y * width) * 4;

        // Set colours
        pixels[index] = (byte) ((alpha * 255F) + 0.5F);
        pixels[index + 1] = (byte) ((blue * 255F) + 0.5F);
        pixels[index + 2] = (byte) ((green * 255F) + 0.5F);
        pixels[index + 3] = (byte) ((red * 255F) + 0.5F);
    }

    public void renderBytes(Bitmap bitmap, int xOff, int yOff)
    {
        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++)
            {
                int index = x + y * bitmap.width;

                if (index < bitmap.colours.length)
                    this.renderPixel(x + xOff, y + yOff, bitmap.colours[index]);
            }
    }

    public void draw(Colour[] colours)
    {
        System.arraycopy(colours, 0, this.colours, 0, colours.length);
    }

    public void draw(Bitmap bitmap, int xLoc, int yLoc)
    {
        for (int x = 0; x < bitmap.width; x++)
        {
            for (int y = 0; y < bitmap.height; y++)
            {
                int xOff = x + xLoc;
                int yOff = y + yLoc;
                int index = xOff + yOff * width;

                if (xOff < 0 || yOff < 0 || xOff >= width || yOff >= height || index < 0 || index >= colours.length)
                    continue;

                if (bitmap.colours[x + y * bitmap.width] != null && bitmap.colours[x + y * bitmap.width].alpha != 0.0)
                    this.colours[index] = bitmap.colours[x + y * bitmap.width];
            }
        }
    }

    public void render()
    {
        for (int x = 0; x < width; x++)
        {
            for (int y = 0; y < height; y++)
            {
                this.renderPixel(x, y, colours[x + y * width]);
            }
        }
    }

    /*public void render(Bitmap bitmap, int x, int y) {
        render(bitmap, x, y, false);
    }

    public void render(Bitmap bitmap, int x, int y, boolean mirrorY) {
        if (bitmap == null)
            return;

        if (1 == 1) {
            //renderBytes(bitmap, x, y);
            return;
        }

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
                    Colour colour = bitmap.colours[xx];

                    if (colour == null)
                        return;

                    // Set the colour code
                    if (colour.alpha != 0)
                        this.colours[endPoint + xx] = colour;
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

                Colour colour = bitmap.colours[x + y * bitmap.width];

                if (colour == null)
                    return;

                // Set the colour code
                if (colour.alpha > 1) this.colours[xPix + yPix * width] = colour;
            }
        }
    }

    */
    public Bitmap flipVert()
    {
        // Create a new bitmap
        Bitmap bitmap = new Bitmap(width, height);

        int xLoc = 0;

        for (int y = 0; y < bitmap.height; y++)
        {
            for (int x = bitmap.width - 1; x >= 0; x--)
            {
                Colour colour = colours[x + y * bitmap.width];

                if (colour != null)// && colour.alpha > 1)
                {
                    bitmap.colours[xLoc + y * bitmap.width] = colour;
                }

                xLoc++;
            }
            xLoc = 0;
        }

        return bitmap;
    }

    public Bitmap clone()
    {
        // Create a new bitmap
        Bitmap clone = new Bitmap(width, height);

        // Render the current pixels onto the clone and return it
        clone.draw(this, 0, 0);
        return clone;
    }
}