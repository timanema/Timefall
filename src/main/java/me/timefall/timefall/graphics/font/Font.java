package me.timefall.timefall.graphics.font;

import me.timefall.timefall.graphics.Bitmap;
import me.timefall.timefall.graphics.Screen;

public class Font
{
    public static void drawText(FontType fontType, FontSize fontSize, String message, Screen screen, int xPos, int yPos)
    {
        if (!fontType.fontSizes.keySet().contains(fontSize))
        {
            try
            {
                throw new IncompatibleSizeException(fontType, fontSize);
            } catch (IncompatibleSizeException e)
            {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < message.length(); i++)
        {
            int index = fontType.getCharString().indexOf(message.charAt(i));
            Bitmap font = fontType.getChars(fontSize)[index % 26][index / 26];

            screen.draw(font, xPos, yPos);
            xPos += font.width;
            font = null;
        }
    }

    public static void drawText(FontType fontType, String message, Screen screen, int xPos, int yPos)
    {
        //TODO: Change default size to normal
        drawText(fontType, FontSize.LARGE, message, screen, xPos, yPos);
    }

    public static void drawText(FontSize fontSize, String message, Screen screen, int xPos, int yPos)
    {
        drawText(FontType.DEFAULT, fontSize, message, screen, xPos, yPos);
    }

    public static void drawText(String message, Screen screen, int xPos, int yPos)
    {
        //TODO: Change default size to normal
        drawText(FontType.DEFAULT, FontSize.LARGE, message, screen, xPos, yPos);
    }
}
