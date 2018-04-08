package me.timefall.timefall.graphics.font;

import me.timefall.timefall.Timefall;
import me.timefall.timefall.graphics.components.Bitmap;
import me.timefall.timefall.graphics.components.Screen;

public class Font
{
    public static void requestText(FontType fontType,
                                   FontSize fontSize,
                                   String message,
                                   int xPos,
                                   int yPos)
    {
        Timefall.getTextOverlay().requestText(fontType, fontSize, message, xPos, yPos);
    }

    //TODO: Add more text options

    /**
     * Draws text directly onto given bitmap
     */
    public static void drawText(Bitmap bitmap,
                                FontType fontType,
                                FontSize fontSize,
                                String message,
                                int xPos,
                                int yPos)
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

            if (index < 0)
            {
                System.out.println("[TimeFall] Could not find character: " + message.charAt(i));
                continue;
            }

            Bitmap font = fontType.getChars(fontSize)[index % 26][index / 26];

            bitmap.draw(font, xPos, yPos);
            xPos += font.width;
        }
    }
}
