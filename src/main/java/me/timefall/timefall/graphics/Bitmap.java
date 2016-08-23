package me.timefall.timefall.graphics;

import me.timefall.timefall.Timefall;
import me.timefall.timefall.graphics.lighting.Light;
import me.timefall.timefall.graphics.lighting.ShadowType;

public class Bitmap
{
    public int width, height;
    public byte[] pixels;
    private int[] lightMap;
    private int[] shadowMap;
    public Colour[] colours;

    public Bitmap(int width, int height)
    {
        this.width = width;
        this.height = height;

        this.pixels = new byte[width * height * 4];
        this.colours = new Colour[width * height];
        this.lightMap = new int[width * height];
        this.shadowMap = new int[width * height];
    }

    public void setDimension(int width, int height)
    {
        this.width = width;
        this.height = height;

        this.pixels = new byte[width * height * 4];
        this.colours = new Colour[width * height];
        this.lightMap = new int[width * height];
        this.shadowMap = new int[width * height];
    }

    public void renderLightMap(int x, int y, int colour)
    {
        if ((x < 0 || y < 0 || x >= width || y >= height) || colour == 0xffff00ff)
            return;

        this.lightMap[x + y * width] = PixelUtils.getMax(colour, this.lightMap[x + y * width]);
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

    public int getShadow(int x, int y)
    {
        if (x < 0 || y < 0 || x >= width || y >= height)
        {
            return 1;
        }

        return shadowMap[x + y * width];
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

    public void draw(Bitmap bitmap, int xLoc, int yLoc, int[] shadows)
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
                {
                    this.colours[index] = bitmap.colours[x + y * bitmap.width];

                    if (shadows[0] != -1) this.shadowMap[index] = shadows[x + y * bitmap.width];
                }
            }
        }
    }

    public void draw(Bitmap bitmap, int xLoc, int yLoc)
    {
        this.draw(bitmap, xLoc, yLoc, new int[]{-1});
    }

    public void clearLights()
    {
        this.lightMap = new int[width * height];
        this.shadowMap = new int[width * height];
    }

    public void blendLight()
    {
        if (!Timefall.getSettings().isLightEnabled())
        {
            return;
        }

        for (int x = 0; x < width; x++)
        {
            for (int y = 0; y < height; y++)
            {
                Colour colour = colours[x + y * width];

                if (colour == null)
                {
                    continue;
                }

                this.renderPixel(x, y, PixelUtils.getColour(PixelUtils.blendColours(PixelUtils.getColour(colour.alpha, colour.red, colour.green, colour.blue), lightMap[x + y * width])));
            }
        }
    }

    public void drawLight(Light light, int xOff, int yOff)
    {
        if (!Timefall.getSettings().isLightEnabled())
        {
            return;
        }

        if (!Timefall.getSettings().isDynamicLightEnabled())
        {
            for (int x = 0; x < light.getDiameter(); x++)
            {
                for (int y = 0; y < light.getDiameter(); y++)
                {
                    this.renderLightMap(x + xOff - light.getRadius(), y + yOff - light.getRadius(), light.getLightMap()[x + y * light.getDiameter()]);
                }
            }
        } else
        {
            for (int i = 0; i < light.getDiameter(); i++)
            {
                this.drawBresenhamLine(light.getRadius(), light.getRadius(), i, 0, light, xOff, yOff);
                this.drawBresenhamLine(light.getRadius(), light.getRadius(), 0, i, light, xOff, yOff);
                this.drawBresenhamLine(light.getRadius(), light.getRadius(), i, light.getDiameter(), light, xOff, yOff);
                this.drawBresenhamLine(light.getRadius(), light.getRadius(), light.getDiameter(), i, light, xOff, yOff);
            }
        }
    }

    private void drawBresenhamLine(int startX, int startY, int endX, int endY, Light light, int xOff, int yOff)
    {
        int deltaX = Math.abs(startX - endX);
        int deltaY = Math.abs(startY - endY);

        int sX = startX < endX ? 1 : -1;
        int sY = startY < endY ? 1 : -1;

        int err = deltaX - deltaY;
        int e2;
        float lightPower = 1.0F;

        while (true)
        {
            if (lightPower == 1.0F)
            {
                this.renderLightMap(startX - light.getRadius() + xOff, startY - light.getRadius() + yOff, light.getColour(startX, startY));
            } else
            {
                this.renderLightMap(startX - light.getRadius() + xOff, startY - light.getRadius() + yOff, PixelUtils.getColourPower(light.getColour(startX, startY), lightPower));
            }

            if (startX == endX && startY == endY)
            {
                break;
            }

            if (this.getShadow(startX - light.getRadius() + xOff, startY - light.getRadius() + yOff) == ShadowType.FULL.type) break;
            if (this.getShadow(startX - light.getRadius() + xOff, startY - light.getRadius() + yOff) == ShadowType.FADE.type) lightPower -= 0.1F;
            if (this.getShadow(startX - light.getRadius() + xOff, startY - light.getRadius() + yOff) == ShadowType.TREE_FADE.type) lightPower -= 0.03F;
            if (lightPower <= 0) break;
            e2 = 2 * err;

            if (e2 > -1 * deltaY)
            {
                err -= deltaY;
                startX += sX;
            }

            if (e2 < deltaX)
            {
                err += deltaX;
                startY += sY;
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