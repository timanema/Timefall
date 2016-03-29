package me.betasterren.bsgame.game.game;

import me.betasterren.bsgame.BSGame;
import me.betasterren.bsgame.GameState;
import me.betasterren.bsgame.Settings;
import me.betasterren.bsgame.events.Keys;
import me.betasterren.bsgame.graphics.Screen;
import me.betasterren.bsgame.level.Vector;

public class Game extends GameState {
    public static int xOff;
    public static int yOff;

    public Game(Settings settings) {
        super(settings);

        xOff = 0;
        yOff = 0;

        Vector.setWorldVariables(xOff, yOff);
    }

    @Override
    public void tick(double deltaTime) {
        Vector.setWorldVariables(xOff, yOff);

        if ((Keys.VK_D.isPressed() || Keys.VK_RIGHT.isPressed()) && BSGame.getTileManager().getLevel().xOff + 2 + 16 * 40 <= BSGame.getTileManager().worldX * 16) BSGame.getTileManager().getLevel().xOff += 2;
        if ((Keys.VK_A.isPressed() || Keys.VK_LEFT.isPressed()) && BSGame.getTileManager().getLevel().xOff - 2 >= 0) BSGame.getTileManager().getLevel().xOff -= 2;
        if ((Keys.VK_W.isPressed() || Keys.VK_UP.isPressed()) && BSGame.getTileManager().getLevel().yOff - 2 >= 0) BSGame.getTileManager().getLevel().yOff -= 2;
        if ((Keys.VK_S.isPressed() || Keys.VK_DOWN.isPressed()) && BSGame.getTileManager().getLevel().yOff - 6 + 16 * 23 <= BSGame.getTileManager().worldY * 16) BSGame.getTileManager().getLevel().yOff += 2;

        // TODO: Tick game things
    }

    @Override
    public void render(Screen screen) {
        BSGame.getTileManager().getLevel().render(screen);
    }
}
