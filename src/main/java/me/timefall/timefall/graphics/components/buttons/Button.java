package me.timefall.timefall.graphics.components.buttons;

import me.timefall.timefall.graphics.components.Bitmap;

public abstract class Button
{
    private String buttonText;
    private int width, height;
    public int xOff, yOff;
    private boolean isActive, isVisible;
    private Runnable runnable;
    private Bitmap bitmap;

    public Button(String buttonText, int width, int height, Runnable runnable, ButtonSkin buttonSkin)
    {
        this.buttonText = buttonText;
        this.width = width;
        this.height = height;
        this.runnable = runnable;
        this.xOff = 0;
        this.yOff = 0;
        this.generateBitmap(buttonSkin);
    }

    private void generateBitmap(ButtonSkin buttonSkin)
    {
        bitmap = new Bitmap(width, height);

        for (int x = 0; x < width; x++)
        {
            for (int y = 0; y < height; y++)
            {
                if (y <= 1 || y >= height - 2 || x <= 1 || x >= width - 2)
                {
                    bitmap.draw(buttonSkin.getOutsideColour(), x, y);
                } else
                {
                    bitmap.draw(buttonSkin.getInsideColour(), x, y);
                }
            }
        }
    }

    public String getText()
    {
        return this.buttonText;
    }

    public void setText(String text)
    {
        this.buttonText = text;
    }

    public int getWidth()
    {
        return this.width;
    }

    public int getHeight()
    {
        return this.height;
    }

    public Bitmap getBitmap()
    {
        return this.bitmap;
    }

    public boolean isActive()
    {
        return this.isActive;
    }

    public boolean isVisible()
    {
        return this.isVisible;
    }

    public void buttonClicked()
    {
        this.runnable.run();
    }

    public void setLocation(int xOff, int yOff)
    {
        this.xOff = xOff;
        this.yOff = yOff;
    }

    public void setActive(boolean isActive)
    {
        this.isActive = isActive;
    }

    public void setVisible(boolean isVisible)
    {
        this.isVisible = isVisible;
    }
}
