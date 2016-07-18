package me.timefall.timefall;

import me.timefall.timefall.graphics.Screen;

public abstract class GameState
{
    Settings settings;

    public GameState(Settings settings)
    {
        this.settings = settings;
    }

    public abstract void tick(double deltaTime);

    public abstract void render(Screen screen);
}
