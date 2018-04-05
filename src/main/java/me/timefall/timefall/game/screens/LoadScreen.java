package me.timefall.timefall.game.screens;

import me.timefall.timefall.GameState;
import me.timefall.timefall.Settings;
import me.timefall.timefall.Timefall;
import me.timefall.timefall.graphics.components.Colour;
import me.timefall.timefall.graphics.components.Screen;
import me.timefall.timefall.graphics.components.Sprite;
import me.timefall.timefall.graphics.components.buttons.ButtonSkin;
import me.timefall.timefall.graphics.components.buttons.NormalButton;
import me.timefall.timefall.graphics.utils.ButtonFactory;

public class LoadScreen extends GameState
{
    public LoadScreen(Settings settings, Screen screen)
    {
        super(settings, screen);

        this.init();
    }

    private void init()
    {
        for (int x = 0; x < getScreen().width; x++) {
            for (int y = 0; y < getScreen().height; y++) {
                getScreen().draw(new Colour(1, 0.745F, 0.737F, 0.659F), x, y);
            }
        }

        //TODO add loading animation

        //the loading will be done elsewhere
    }

    @Override
    public void tick(double deltaTime)
    {

    }

    @Override
    public void render(Screen screen)
    {

    }

    @Override
    public void saveState()
    {

    }
}
