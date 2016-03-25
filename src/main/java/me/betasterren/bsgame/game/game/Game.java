package me.betasterren.bsgame.game.game;

import me.betasterren.bsgame.BSGame;
import me.betasterren.bsgame.GameState;
import me.betasterren.bsgame.Settings;
import me.betasterren.bsgame.graphics.Screen;
import me.betasterren.bsgame.level.Vector;

public class Game extends GameState {
    private static float xOff;
    private static float yOff;

    public Game(Settings settings) {
        super(settings);

        xOff = 0.0F;
        yOff = 0.0F;

        Vector.setWorldVariables(xOff, yOff);
    }

    @Override
    public void tick(double deltaTime) {
        Vector.setWorldVariables(xOff, yOff);

        // TODO: Tick game things
    }

    @Override
    public void render(Screen screen) {
        //TODO: Remove debug sprites
        /*for (int i = 0; i < 8; i++)
            for (int x = 6; x < 8; x++)
                for (int y = 0; y < 3; y++)
                    screen.render(Sprite.sprites[x][y], x * 16 + i * 38, y * 16 + i * 19, false);*/

        BSGame.getTileManager().getLevel().render(screen);
    }
}
