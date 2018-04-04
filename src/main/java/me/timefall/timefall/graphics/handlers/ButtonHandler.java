package me.timefall.timefall.graphics.handlers;

import me.timefall.timefall.Timefall;
import me.timefall.timefall.graphics.components.Screen;
import me.timefall.timefall.graphics.components.buttons.Button;
import me.timefall.timefall.time.Time;

import java.util.ArrayList;

public class ButtonHandler
{
    private ArrayList<Button> buttons;

    public ButtonHandler()
    {
        this.buttons = new ArrayList<>();
    }

    public void clickAction(int xOff, int yOff)
    {
        Button clickedButton = this.findButton(xOff, yOff);

        if (clickedButton != null)
        {
            clickedButton.buttonClicked();
        }
    }

    public Button findButton(int xOff, int yOff)
    {
        for (Button button : this.buttons)
        {
            if (button.isVisible() && button.isActive())
            {
                if (xOff >= button.xOff && xOff <= button.xOff + button.getWidth() &&
                        yOff >= button.yOff && yOff <= button.yOff + button.getHeight())
                {
                    return button;
                }
            }
        }

        return null;
    }

    public void addButton(Button button)
    {
        if (!this.buttons.contains(button))
        {
            this.buttons.add(button);
        }
    }

    public void tickButtons()
    {
        //TODO: Add tick things
    }

    public void renderButtons(Screen screen)
    {
        this.buttons.stream().filter(Button::isVisible).forEach(button ->
                screen.draw(button.getBitmap(), button.xOff, button.yOff));
    }
}
