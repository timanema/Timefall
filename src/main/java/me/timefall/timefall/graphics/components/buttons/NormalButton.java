package me.timefall.timefall.graphics.components.buttons;

public class NormalButton extends Button
{
    public boolean disappearOnClick;

    public NormalButton(String buttonText, int width, int height, Runnable runnable, ButtonSkin buttonSkin, boolean disappearOnClick)
    {
        super(buttonText, width, height, runnable, buttonSkin);

        this.disappearOnClick = disappearOnClick;
    }

    @Override
    public void buttonClicked()
    {
        super.buttonClicked();

        if (disappearOnClick)
        {
            super.setVisible(false);
            super.setActive(false);
        }
    }
}
