package me.timefall.timefall.graphics.font;

import me.timefall.timefall.Timefall;
import me.timefall.timefall.graphics.components.Bitmap;
import me.timefall.timefall.graphics.components.Screen;

public class Font
{
    public static void drawText(FontType fontType, FontSize fontSize, String message, int xPos, int yPos)
    {
        System.out.println("Rendering: " + message);
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

            Timefall.textOverlay.getScreen().draw(font, xPos, yPos);
            xPos += font.width;
        }
    }

    public static void drawText(FontType fontType, String message, int xPos, int yPos)
    {
        //TODO: Change default size to normal
        drawText(fontType, FontSize.LARGE, message, xPos, yPos);
    }

    public static void drawText(FontSize fontSize, String message, int xPos, int yPos)
    {
        drawText(FontType.DEFAULT, fontSize, message, xPos, yPos);
    }

    public static void drawText(String message, int xPos, int yPos)
    {
        //TODO: Change default size to normal
        drawText(FontType.DEFAULT, FontSize.LARGE, message, xPos, yPos);
    }
}
