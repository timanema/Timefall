package me.timefall.timefall.graphics;

public class Font
{
    public static final String letters_default = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "abcdefghijklmnopqrstuvwxyz" + "0123456789                " + "*$-+%#!?=@;'.,)(~}{`_^][  ";

    public static void drawText(Screen screen, String message, int xPos, int yPos)
    {
        //TODO: Add support for more fonts
        for (int i = 0; i < message.length(); i++)
        {
            int index = letters_default.indexOf(message.charAt(i));

            screen.draw(Sprite.font[index % 26][index / 26], xPos, yPos);
            xPos += 48;
        }
    }
}
