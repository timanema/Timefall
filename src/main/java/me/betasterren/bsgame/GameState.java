package me.betasterren.bsgame;

import me.betasterren.bsgame.graphics.Screen;

public abstract class GameState {
    Settings settings;

    public GameState(Settings settings) {
        this.settings = settings;
    }

    public abstract void tick();

    public abstract void render(Screen screen);
}
