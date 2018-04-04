package me.timefall.timefall.graphics.components.buttons;

import me.timefall.timefall.graphics.components.Colour;

public enum ButtonSkin
{
    DEFAULT(Colour.BLACK, Colour.GREY),
    TRANSPARENT(Colour.TRANSPARENT, Colour.TRANSPARENT);

    private Colour outsideColour;
    private Colour insideColour;

    private ButtonSkin(Colour outsideColour, Colour insideColour)
    {
        this.outsideColour = outsideColour;
        this.insideColour = insideColour;
    }

    public Colour getOutsideColour()
    {
        return this.outsideColour;
    }

    public Colour getInsideColour()
    {
        return this.insideColour;
    }
}
