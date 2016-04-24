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

        boolean rightKey = Keys.VK_D.isPressed() || Keys.VK_RIGHT.isPressed();
        boolean leftKey = Keys.VK_A.isPressed() || Keys.VK_LEFT.isPressed();
        boolean upKey = Keys.VK_W.isPressed() || Keys.VK_UP.isPressed();
        boolean downKey = Keys.VK_S.isPressed() || Keys.VK_DOWN.isPressed();

        boolean northDirection = upKey && !downKey;
        boolean southDirection = downKey && !upKey;
        boolean westDirection = leftKey && !rightKey;
        boolean eastDirection = rightKey && !leftKey;

        boolean moveCommand = rightKey || leftKey || upKey || downKey;

        boolean withinXEast = BSGame.getTileManager().getCurrentWorld().getX() + 2 + 16 * 40 <= BSGame.getTileManager().worldX * 16;
        boolean withinXWest = BSGame.getTileManager().getCurrentWorld().getX() - 2 >= 0;
        boolean withinYNorth = BSGame.getTileManager().getCurrentWorld().getY() - 2 >= 0;
        boolean withinYSouth = BSGame.getTileManager().getCurrentWorld().getY() - 6 + 16 * 23 <= BSGame.getTileManager().worldY * 16;

        boolean xCen = BSGame.getTileManager().getEntityManager().getPlayer().isXCentred();
        boolean yCen = BSGame.getTileManager().getEntityManager().getPlayer().isYCentred();

        // Check all directions if it is a move command
        if (moveCommand) {
            if (northDirection) {
                if (withinYNorth && yCen) {
                    BSGame.getTileManager().getCurrentWorld().setOffset(BSGame.getTileManager().getCurrentWorld().getX(), BSGame.getTileManager().getCurrentWorld().getY() - 2);
                } else {
                    BSGame.getTileManager().getEntityManager().getPlayer().yCen = false;
                    BSGame.getTileManager().getEntityManager().getPlayer().yOff -= 2;
                }
            }

            if (eastDirection) {
                if (withinXEast && xCen) {
                    BSGame.getTileManager().getCurrentWorld().setOffset(BSGame.getTileManager().getCurrentWorld().getX() + 2, BSGame.getTileManager().getCurrentWorld().getY());
                } else {
                    BSGame.getTileManager().getEntityManager().getPlayer().xCen = false;
                    BSGame.getTileManager().getEntityManager().getPlayer().xOff += 2;
                }
            }

            if (southDirection) {
                if (withinYSouth && yCen) {
                    BSGame.getTileManager().getCurrentWorld().setOffset(BSGame.getTileManager().getCurrentWorld().getX(), BSGame.getTileManager().getCurrentWorld().getY() + 2);
                } else {
                    BSGame.getTileManager().getEntityManager().getPlayer().yCen = false;
                    BSGame.getTileManager().getEntityManager().getPlayer().yOff += 2;
                }
            }

            if (westDirection) {
                if (withinXWest && xCen) {
                    BSGame.getTileManager().getCurrentWorld().setOffset(BSGame.getTileManager().getCurrentWorld().getX() - 2, BSGame.getTileManager().getCurrentWorld().getY());
                } else {
                    BSGame.getTileManager().getEntityManager().getPlayer().xCen = false;
                    BSGame.getTileManager().getEntityManager().getPlayer().xOff -= 2;
                }
            }

            BSGame.getTileManager().getEntityManager().getPlayer().move(
                    (northDirection && eastDirection ? Direction.NORTHEAST : (northDirection && westDirection ? Direction.NORTHWEST : (southDirection && eastDirection ? Direction.SOUTHEAST : (southDirection && westDirection ? Direction.SOUTHWEST : (northDirection ? Direction.NORTH : (southDirection ? Direction.SOUTH : (eastDirection ? Direction.EAST : Direction.WEST)))))))
            );
        } else {
            // Player didn't issue move command this tick so the player entity stops moving
            BSGame.getTileManager().getEntityManager().getPlayer().currentlyMoving = false;
        }

        // TODO: Tick game things
    }

    @Override
    public void render(Screen screen) {
        BSGame.getTileManager().getLevel().render(screen);
        BSGame.getTileManager().getEntityManager().renderEntities(screen);
    }
}
