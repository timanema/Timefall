package me.timefall.timefall.game.game;

import me.timefall.timefall.GameState;
import me.timefall.timefall.Settings;
import me.timefall.timefall.Timefall;
import me.timefall.timefall.events.keys.Keys;
import me.timefall.timefall.graphics.components.Screen;
import me.timefall.timefall.graphics.font.Font;
import me.timefall.timefall.graphics.font.FontSize;
import me.timefall.timefall.graphics.font.FontType;
import me.timefall.timefall.level.Direction;
import me.timefall.timefall.level.TileManager;

public class Game extends GameState
{
    public TileManager tileManager;
    private boolean debugInfo = false;

    public Game(Settings settings, Screen screen)
    {
        super(settings, screen);

        System.out.println("\nInitializing main game");
        tileManager = new TileManager();
    }

    @Override
    public void tick(double deltaTime)
    {
        //TODO: Remove old movement code
        TileManager tileManager = Timefall.getTileManager();

        // Tick all entities
        tileManager.getEntityManager().tickEntities();

        // Check for key commands
        boolean debugToggle = Keys.VK_F3.isClicked();

        if (debugToggle)
        {
            this.debugInfo = !this.debugInfo;
        }

        // Get which keys are pressed and pair them with direction
        boolean rightKey = Keys.VK_D.isPressed() || Keys.VK_RIGHT.isPressed(), leftKey = Keys.VK_A.isPressed() || Keys.VK_LEFT.isPressed(), upKey = Keys.VK_W.isPressed() || Keys.VK_UP.isPressed(), downKey = Keys.VK_S.isPressed() || Keys.VK_DOWN.isPressed();
        boolean northDirection = upKey && !downKey, southDirection = downKey && !upKey, westDirection = leftKey && !rightKey, eastDirection = rightKey && !leftKey;
        boolean moveCommand = rightKey || leftKey || upKey || downKey;

        // Get direction
        Direction direction = (northDirection && eastDirection ? Direction.NORTHEAST : (northDirection && westDirection ? Direction.NORTHWEST : (southDirection && eastDirection ? Direction.SOUTHEAST : (southDirection && westDirection ? Direction.SOUTHWEST : (northDirection ? Direction.NORTH : (southDirection ? Direction.SOUTH : (eastDirection ? Direction.EAST : Direction.WEST)))))));

        /*// Check if the screen can move any further
        boolean withinXEast = tileManager.getCurrentWorld().getX() + 2 + 16 * 40 <= tileManager.worldX * 16;
        boolean withinXWest = tileManager.getCurrentWorld().getX() - 2 >= 0;
        boolean withinYNorth = tileManager.getCurrentWorld().getY() - 2 >= 0;
        boolean withinYSouth = tileManager.getCurrentWorld().getY() - 6 + 16 * 23 <= tileManager.worldY * 16;

        // Check if the player is X or Y centered
        boolean xCen = tileManager.getEntityManager().getPlayer().isXCentred();
        boolean yCen = tileManager.getEntityManager().getPlayer().isYCentred();*/


        // Check all directions if it is a move command and if the player can move
        if (moveCommand)
        {
            /*if (northDirection)
            {
                if (withinYNorth && yCen)
                {
                    tileManager.getCurrentWorld().setOffset(tileManager.getCurrentWorld().getX(), tileManager.getCurrentWorld().getY() - 2);
                } else
                {
                    if (tileManager.getEntityManager().getPlayer().getyOff() - 2 >= 0)
                    {
                        tileManager.getEntityManager().getPlayer().yCen = false;
                        tileManager.getEntityManager().getPlayer().yOff -= 2;
                    }
                }
            }

            if (eastDirection)
            {
                if (withinXEast && xCen)
                {
                    tileManager.getCurrentWorld().setOffset(tileManager.getCurrentWorld().getX() + 2, tileManager.getCurrentWorld().getY());
                } else
                {
                    if (tileManager.getEntityManager().getPlayer().getxOff() + 2 <= (tileManager.worldX - 1) * 16)
                    {
                        tileManager.getEntityManager().getPlayer().xCen = false;
                        tileManager.getEntityManager().getPlayer().xOff += 2;
                    }
                }
            }

            if (southDirection)
            {
                if (withinYSouth && yCen)
                {
                    tileManager.getCurrentWorld().setOffset(tileManager.getCurrentWorld().getX(), tileManager.getCurrentWorld().getY() + 2);
                } else
                {
                    if (tileManager.getEntityManager().getPlayer().getyOff() + 2 <= (tileManager.worldY - 1) * 16 - 12)
                    {
                        tileManager.getEntityManager().getPlayer().yCen = false;
                        tileManager.getEntityManager().getPlayer().yOff += 2;
                    }
                }
            }

            if (westDirection)
            {
                if (withinXWest && xCen)
                {
                    tileManager.getCurrentWorld().setOffset(tileManager.getCurrentWorld().getX() - 2, tileManager.getCurrentWorld().getY());
                } else
                {
                    if (tileManager.getEntityManager().getPlayer().getxOff() - 2 >= 0)
                    {
                        tileManager.getEntityManager().getPlayer().xCen = false;
                        tileManager.getEntityManager().getPlayer().xOff -= 2;
                    }
                }
            }*/
            tileManager.getEntityManager().getPlayer().move(direction);
        } else
        {
            // TODO: Use keyReleased in keyhandler
            tileManager.getEntityManager().getPlayer().currentlyMoving = false;
        }

        // TODO: Tick game things
    }

    @Override
    public void render(Screen screen)
    {
        TileManager tileManager = Timefall.getTileManager();

        // Render the current world and render the entities in that world
        tileManager.getLevel().render(screen);
        tileManager.getEntityManager().renderEntities(screen);

        // Render debug information

        if (this.debugInfo) {
            int screenXOFF = tileManager.getCurrentWorld().getX();
            int screenYOFF = tileManager.getCurrentWorld().getY();
            float xLoc = tileManager.getEntityManager().getPlayer().getLocation().getX();
            float yLoc = tileManager.getEntityManager().getPlayer().getLocation().getY();
            int playerXWOFF = tileManager.getEntityManager().getPlayer().getxOff();
            int playerYWOFF = tileManager.getEntityManager().getPlayer().getyOff();
            int playerXOFF = tileManager.getEntityManager().getPlayer().xOff;
            int playerYOFF = tileManager.getEntityManager().getPlayer().yOff;
            boolean xCen = tileManager.getEntityManager().getPlayer().xCen;
            boolean yCen = tileManager.getEntityManager().getPlayer().yCen;
            String worldName = tileManager.getCurrentWorld().getWorldName();

            Font.requestText(FontType.DEFAULT, FontSize.NORMAL, "Screen xOff: " + screenXOFF, 0, 0);
            Font.requestText(FontType.DEFAULT, FontSize.NORMAL, "Screen yOff: " + screenYOFF, 0, 20);
            Font.requestText(FontType.DEFAULT, FontSize.NORMAL, "xLoc: " + xLoc, 0, 40);
            Font.requestText(FontType.DEFAULT, FontSize.NORMAL, "yLoc: " + yLoc, 0, 60);
            Font.requestText(FontType.DEFAULT, FontSize.NORMAL, "Player xWOff: " + playerXWOFF, 0, 80);
            Font.requestText(FontType.DEFAULT, FontSize.NORMAL, "Player xWOff: " + playerYWOFF, 0, 100);
            Font.requestText(FontType.DEFAULT, FontSize.NORMAL, "Player xOff: " + playerXOFF, 0, 120);
            Font.requestText(FontType.DEFAULT, FontSize.NORMAL, "Player xOff: " + playerYOFF, 0, 140);
            Font.requestText(FontType.DEFAULT, FontSize.NORMAL, "Player xCen: " + xCen, 0, 160);
            Font.requestText(FontType.DEFAULT, FontSize.NORMAL, "Player yCen: " + yCen, 0, 180);
            Font.requestText(FontType.DEFAULT, FontSize.NORMAL, "World: " + worldName, 0, 200);
        }
    }

    @Override
    public void saveState()
    {
        System.out.println("\nSaving game data ...");

        // Save all necessary data
        Timefall.getFileManager().changeSetting("settings", "xOff", String.valueOf(Timefall.getTileManager().getCurrentWorld().getX()));
        Timefall.getFileManager().changeSetting("settings", "yOff", String.valueOf(Timefall.getTileManager().getCurrentWorld().getY()));
        Timefall.getFileManager().changeSetting("settings", "gender", String.valueOf(Timefall.getTileManager().getEntityManager().getPlayer().getGender()));
        Timefall.getFileManager().changeSetting("lvl", "world", Timefall.getTileManager().getCurrentWorld().getWorldName());
        Timefall.getFileManager().changeSetting("lvl", "xOff", String.valueOf(Timefall.getTileManager().getEntityManager().getPlayer().getxOff()));
        Timefall.getFileManager().changeSetting("lvl", "yOff", String.valueOf(Timefall.getTileManager().getEntityManager().getPlayer().getyOff()));

        System.out.println("Saved game data!");
    }
}
