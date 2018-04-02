package me.timefall.timefall.graphics.font;

import me.timefall.timefall.graphics.components.Bitmap;
import me.timefall.timefall.graphics.components.Sprite;

import java.util.HashMap;

public enum FontType
{
    DEFAULT("ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
            "abcdefghijklmnopqrstuvwxyz" +
            "0123456789                " +
            "*$-+%#!?=@;'.,)(~}{`_^][: ",
            new FontSize[]{FontSize.NORMAL},
            new Bitmap[][][]{Sprite.defaultFontNormal});

    private String charString;
    public HashMap<FontSize, Bitmap[][]> fontSizes;

    private FontType(String charString, FontSize[] fontSizes, Bitmap[][][] bitmaps)
    {
        this.charString = charString;
        this.fontSizes = new HashMap<>();

        for (int i = 0; i < fontSizes.length; i++)
        {
            this.fontSizes.put(fontSizes[i], bitmaps[i]);
        }
    }

    public String getCharString()
    {
        return this.charString;
    }

    public Bitmap[][] getChars(FontSize fontSize)
    {
        return this.fontSizes.get(fontSize);
    }
}
