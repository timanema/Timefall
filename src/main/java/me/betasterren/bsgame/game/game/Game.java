package me.betasterren.bsgame.game.game;

import me.betasterren.bsgame.GameState;
import me.betasterren.bsgame.Settings;
import me.betasterren.bsgame.graphics.Screen;
import me.betasterren.bsgame.graphics.Sprite;

public class Game extends GameState {
    public Game(Settings settings) {
        super(settings);
    }

    @Override
    public void tick() {
        // TODO: Tick game things
    }

    @Override
    public void render(Screen screen) {
        //TODO: Remove debug sprites
        for (int x = 0; x < 40; x++) {
            for (int y = 0; y < 23; y++)
                screen.render(Sprite.sprites[4][1], x * 16, y * 16);
        }


        for (int i = 0; i < 8; i++)
            for (int x = 6; x < 8; x++)
                for (int y = 0; y < 3; y++)
                    screen.render(Sprite.sprites[x][y], x * 16 + i * 38, y * 16 + i * 19, false);
    }
}
