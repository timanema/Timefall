package me.betasterren.bsgame.game.game;

import me.betasterren.bsgame.BSGame;
import me.betasterren.bsgame.GameState;
import me.betasterren.bsgame.Settings;
import me.betasterren.bsgame.events.Keys;
import me.betasterren.bsgame.graphics.Screen;
import me.betasterren.bsgame.level.Direction;

public class Game extends GameState {
    public Game(Settings settings) {
        super(settings);

        System.out.println("\nInitializing main game");
    }

    @Override
    public void tick(double deltaTime) {
        BSGame.getTileManager().getEntityManager().tickEntities();

        //TODO: Clean this up plz
        if ((Keys.VK_D.isPressed() || Keys.VK_RIGHT.isPressed())) {
            if (BSGame.getTileManager().getLevel().xOff + 2 + 16 * 40 <= BSGame.getTileManager().worldX * 16 && BSGame.getTileManager().getEntityManager().getPlayer().isXCentred()) {
                BSGame.getTileManager().getLevel().xOff += 2;
            } else {
                BSGame.getTileManager().getEntityManager().getPlayer().xCen = false;
                BSGame.getTileManager().getEntityManager().getPlayer().xOff += 2;
            }

            BSGame.getTileManager().getEntityManager().getPlayer().move(Direction.EAST);
            BSGame.getTileManager().getLevel().playerMoved = true;
        }

        if ((Keys.VK_A.isPressed() || Keys.VK_LEFT.isPressed())) {
            if (BSGame.getTileManager().getLevel().xOff - 2 >= 0 && BSGame.getTileManager().getEntityManager().getPlayer().isXCentred()) {
                BSGame.getTileManager().getLevel().xOff -= 2;
            } else {
                BSGame.getTileManager().getEntityManager().getPlayer().xCen = false;
                BSGame.getTileManager().getEntityManager().getPlayer().xOff -= 2;
            }

            BSGame.getTileManager().getEntityManager().getPlayer().move(Direction.WEST);
            BSGame.getTileManager().getLevel().playerMoved = true;
        }

        if ((Keys.VK_W.isPressed() || Keys.VK_UP.isPressed())) {
            if (BSGame.getTileManager().getLevel().yOff - 2 >= 0 && BSGame.getTileManager().getEntityManager().getPlayer().isYCentred()) {
                BSGame.getTileManager().getLevel().yOff -= 2;
            } else {
                BSGame.getTileManager().getEntityManager().getPlayer().yCen = false;
                BSGame.getTileManager().getEntityManager().getPlayer().yOff -= 2;
            }

            BSGame.getTileManager().getEntityManager().getPlayer().move(Direction.NORTH);
            BSGame.getTileManager().getLevel().playerMoved = true;
        }

        if ((Keys.VK_S.isPressed() || Keys.VK_DOWN.isPressed())) {
            if (BSGame.getTileManager().getLevel().yOff - 6 + 16 * 23 <= BSGame.getTileManager().worldY * 16 && BSGame.getTileManager().getEntityManager().getPlayer().isYCentred()) {
                BSGame.getTileManager().getLevel().yOff += 2;
            } else {
                BSGame.getTileManager().getEntityManager().getPlayer().yCen = false;
                BSGame.getTileManager().getEntityManager().getPlayer().yOff += 2;
            }

            BSGame.getTileManager().getEntityManager().getPlayer().move(Direction.SOUTH);
            BSGame.getTileManager().getLevel().playerMoved = true;
        }

        // TODO: Tick game things
    }

    @Override
    public void render(Screen screen) {
        BSGame.getTileManager().getLevel().render(screen);
        BSGame.getTileManager().getEntityManager().renderEntities(screen);
    }
}
