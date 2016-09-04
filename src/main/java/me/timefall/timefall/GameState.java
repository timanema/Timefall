package me.timefall.timefall;

import me.timefall.timefall.graphics.components.Screen;

public abstract class GameState
{
    private Settings settings;
    private Screen screen;

    public GameState(Settings settings, Screen screen)
    {
        this.settings = settings;
        this.screen = screen;
    }

    public abstract void tick(double deltaTime);

    public abstract void render(Screen screen);

    public Screen getScreen()
    {
        return this.screen;
    }
}
