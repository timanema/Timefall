package me.timefall.timefall.graphics.handlers;

import me.timefall.timefall.Timefall;
import me.timefall.timefall.graphics.components.buttons.ButtonSkin;
import me.timefall.timefall.graphics.components.buttons.NormalButton;

public class ButtonFactory
{
    public static NormalButton createNormalButton(String text, int width, int height, Runnable runnable, ButtonSkin buttonSkin, boolean disappearOnClick)
    {
        NormalButton normalButton = new NormalButton(text, width, height, runnable, buttonSkin, disappearOnClick);

        Timefall.getButtonHandler().addButton(normalButton);
        return normalButton;
    }
}
