package me.betasterren.bsgame.game.game;

import me.betasterren.bsgame.Timefall;
import me.betasterren.bsgame.GameState;
import me.betasterren.bsgame.Settings;
import me.betasterren.bsgame.events.Keys;
import me.betasterren.bsgame.graphics.Screen;
import me.betasterren.bsgame.level.Direction;
import me.betasterren.bsgame.level.TileManager;

public class Game extends GameState {
    public Game(Settings settings) {
        super(settings);

        System.out.println("\nInitializing main game");
    }

    @Override
    public void tick(double deltaTime) {
        TileManager tileManager = Timefall.getTileManager();

        // Tick all entities
        tileManager.getEntityManager().tickEntities();

        // Get which keys are pressed
        boolean rightKey = Keys.VK_D.isPressed() || Keys.VK_RIGHT.isPressed();
        boolean leftKey = Keys.VK_A.isPressed() || Keys.VK_LEFT.isPressed();
        boolean upKey = Keys.VK_W.isPressed() || Keys.VK_UP.isPressed();
        boolean downKey = Keys.VK_S.isPressed() || Keys.VK_DOWN.isPressed();

        // 'Make' directions of the above data
        boolean northDirection = upKey && !downKey;
        boolean southDirection = downKey && !upKey;
        boolean westDirection = leftKey && !rightKey;
        boolean eastDirection = rightKey && !leftKey;

        boolean moveCommand = rightKey || leftKey || upKey || downKey;

        // Check if the screen can move any further
        boolean withinXEast = tileManager.getCurrentWorld().getX() + 2 + 16 * 40 <= tileManager.worldX * 16;
        boolean withinXWest = tileManager.getCurrentWorld().getX() - 2 >= 0;
        boolean withinYNorth = tileManager.getCurrentWorld().getY() - 2 >= 0;
        boolean withinYSouth = tileManager.getCurrentWorld().getY() - 6 + 16 * 23 <= tileManager.worldY * 16;

        // Check if the player is X or Y centered
        boolean xCen = tileManager.getEntityManager().getPlayer().isXCentred();
        boolean yCen = tileManager.getEntityManager().getPlayer().isYCentred();

        // Get direction
        Direction direction = (northDirection && eastDirection ? Direction.NORTHEAST : (northDirection && westDirection ? Direction.NORTHWEST : (southDirection && eastDirection ? Direction.SOUTHEAST : (southDirection && westDirection ? Direction.SOUTHWEST : (northDirection ? Direction.NORTH : (southDirection ? Direction.SOUTH : (eastDirection ? Direction.EAST : Direction.WEST)))))));

        // Check all directions if it is a move command and if the player can move
        if (moveCommand /*&&
                tileManager.getEntityManager().getPlayer().canMove(direction)*/) {
            if (northDirection) {
                if (withinYNorth && yCen) {
                    tileManager.getCurrentWorld().setOffset(tileManager.getCurrentWorld().getX(), tileManager.getCurrentWorld().getY() - 2);
                } else {
                    if (tileManager.getEntityManager().getPlayer().getyOff() - 2 >= 0) {
                        tileManager.getEntityManager().getPlayer().yCen = false;
                        tileManager.getEntityManager().getPlayer().yOff -= 2;
                    }
                }
            }

            if (eastDirection) {
                if (withinXEast && xCen) {
                    tileManager.getCurrentWorld().setOffset(tileManager.getCurrentWorld().getX() + 2, tileManager.getCurrentWorld().getY());
                } else {
                    if (tileManager.getEntityManager().getPlayer().getxOff() + 2 <= (tileManager.worldX - 1) * 16) {
                        tileManager.getEntityManager().getPlayer().xCen = false;
                        tileManager.getEntityManager().getPlayer().xOff += 2;
                    }
                }
            }

            if (southDirection) {
                if (withinYSouth && yCen) {
                    tileManager.getCurrentWorld().setOffset(tileManager.getCurrentWorld().getX(), tileManager.getCurrentWorld().getY() + 2);
                } else {
                    if (tileManager.getEntityManager().getPlayer().getyOff() + 2 <= (tileManager.worldY - 1) * 16 - 12) {
                        tileManager.getEntityManager().getPlayer().yCen = false;
                        tileManager.getEntityManager().getPlayer().yOff += 2;
                    }
                }
            }

            if (westDirection) {
                if (withinXWest && xCen) {
                    tileManager.getCurrentWorld().setOffset(tileManager.getCurrentWorld().getX() - 2, tileManager.getCurrentWorld().getY());
                } else {
                    if (tileManager.getEntityManager().getPlayer().getxOff() - 2 >= 0) {
                        tileManager.getEntityManager().getPlayer().xCen = false;
                        tileManager.getEntityManager().getPlayer().xOff -= 2;
                    }
                }
            }

            tileManager.getEntityManager().getPlayer().move(direction);
        } else {
            // Player didn't issue move command this tick so the player entity stops moving
            tileManager.getEntityManager().getPlayer().currentlyMoving = false;
        }

        // TODO: Tick game things
    }

    @Override
    public void render(Screen screen) {
        TileManager tileManager = Timefall.getTileManager();

        // Render the current world and render the entities in that world
        tileManager.getLevel().render(screen);
        tileManager.getEntityManager().renderEntities(screen);
    }
}
