package me.timefall.timefall.graphics.font;

public class IncompatibleSizeException extends Exception
{
    public IncompatibleSizeException(FontType fontType, FontSize fontSize)
    {
        super("Cannot find a bitmap with size '" + fontSize.name() + "' for font type '" + fontType.name() + "'!\nAvailable sizes: " + fontType.fontSizes.keySet());
    }
}
