package me.timefall.timefall.game.game;

import me.timefall.timefall.GameState;
import me.timefall.timefall.Settings;
import me.timefall.timefall.Timefall;
import me.timefall.timefall.entities.Entity;
import me.timefall.timefall.entities.NPC;
import me.timefall.timefall.entities.PathTile;
import me.timefall.timefall.entities.Pathfinding;
import me.timefall.timefall.entities.behaviors.Behavior;
import me.timefall.timefall.entities.behaviors.BehaviorAction;
import me.timefall.timefall.entities.mobs.Sheep;
import me.timefall.timefall.events.keys.Keys;
import me.timefall.timefall.files.Save;
import me.timefall.timefall.graphics.components.Bitmap;
import me.timefall.timefall.graphics.components.Screen;
import me.timefall.timefall.graphics.font.Font;
import me.timefall.timefall.graphics.font.FontSize;
import me.timefall.timefall.graphics.font.FontType;
import me.timefall.timefall.level.Direction;
import me.timefall.timefall.level.TileManager;
import me.timefall.timefall.level.Vector;
import me.timefall.timefall.time.TimedTask;

import java.util.ArrayList;

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
        TileManager tileManager = Timefall.getTileManager();

        // Tick all entities
        tileManager.getEntityManager().tickEntities();

        // Check for key commands
        boolean debugToggle = Keys.VK_F3.isClicked();

        if (debugToggle)
        {
            this.debugInfo = !this.debugInfo;
        }

        if (Keys.VK_P.isClicked())
        {
            //tileManager.getCurrentWorld().reloadCollisions();

            Sheep sheep = (Sheep) Timefall.getTileManager().getEntityManager().getEntities().get(0);
            Behavior behavior = new Behavior("moveSouth");
            BehaviorAction behaviorAction = new BehaviorAction("move", Direction.NORTH);
            behavior.addAction(behaviorAction);
            sheep.getRoutine().giveCommand(behavior);
        }
        if (Keys.VK_Q.isClicked())
        {
            //tileManager.getCurrentWorld().reloadCollisions();

            Sheep sheep = (Sheep) Timefall.getTileManager().getEntityManager().getEntities().get(0);
            Behavior behavior = new Behavior("moveSouth");
            BehaviorAction behaviorAction = new BehaviorAction("move", Direction.NORTHEAST);
            behavior.addAction(behaviorAction);
            sheep.getRoutine().giveCommand(behavior);
        }
        if (Keys.VK_R.isClicked())
        {
            //tileManager.getCurrentWorld().reloadCollisions();

            Sheep sheep = (Sheep) Timefall.getTileManager().getEntityManager().getEntities().get(0);
            Behavior behavior = new Behavior("moveSouth");
            BehaviorAction behaviorAction = new BehaviorAction("move", Direction.NORTHWEST);
            behavior.addAction(behaviorAction);
            sheep.getRoutine().giveCommand(behavior);
        }
        if (Keys.VK_T.isClicked())
        {
            //tileManager.getCurrentWorld().reloadCollisions();

            Sheep sheep = (Sheep) Timefall.getTileManager().getEntityManager().getEntities().get(0);
            Behavior behavior = new Behavior("moveSouth");
            BehaviorAction behaviorAction = new BehaviorAction("move", Direction.EAST);
            behavior.addAction(behaviorAction);
            sheep.getRoutine().giveCommand(behavior);
        }
        if (Keys.VK_Y.isClicked())
        {
            //tileManager.getCurrentWorld().reloadCollisions();

            Sheep sheep = (Sheep) Timefall.getTileManager().getEntityManager().getEntities().get(0);
            Behavior behavior = new Behavior("moveSouth");
            BehaviorAction behaviorAction = new BehaviorAction("move", Direction.SOUTH);
            behavior.addAction(behaviorAction);
            sheep.getRoutine().giveCommand(behavior);
        }
        if (Keys.VK_K.isClicked())
        {
            //tileManager.getCurrentWorld().reloadCollisions();

            Sheep sheep = (Sheep) Timefall.getTileManager().getEntityManager().getEntities().get(0);
            Behavior behavior = new Behavior("moveSouth");
            BehaviorAction behaviorAction = new BehaviorAction("moveDistance", Direction.SOUTHEAST, 8);
            behavior.addAction(behaviorAction);
            sheep.getRoutine().giveCommand(behavior);
        }
        if (Keys.VK_J.isClicked())
        {
            //tileManager.getCurrentWorld().reloadCollisions();

            //Once again - do this only if this is a statelss object!
            Runnable runnable = () -> {
                Sheep sheep = (Sheep) Timefall.getTileManager().getEntityManager().getEntities().get(0);
                Behavior behavior = new Behavior("moveSouth");
                BehaviorAction behaviorAction = new BehaviorAction("move", Direction.SOUTH);
                behavior.addAction(behaviorAction);
                sheep.getRoutine().giveCommand(behavior);
            };
            Timefall.getTime().scheduleTask(25, 2, 5, runnable);

        }
        if (Keys.VK_L.isClicked())
        {
            Sheep sheep = (Sheep) Timefall.getTileManager().getEntityManager().getEntities().get(0);
            Vector s = sheep.getLocation();
            System.out.println("S: " + s.getX() + " " + s.getY());
            Vector p = Timefall.getTileManager().getEntityManager().getPlayer().getLocation();

            Pathfinding pathfinding = new Pathfinding(p, s);
            ArrayList<PathTile> finalPath = pathfinding.getFinalPath();

            for (PathTile pathTile : finalPath)
            {
                System.out.println(pathTile.x + " " + pathTile.y);
            }

            Behavior behavior = pathfinding.getBehaviorFromPath(finalPath, sheep);
            sheep.getRoutine().giveCommand(behavior);
        }

        if (Keys.VK_ESC.isClicked())
        {
            Timefall.openMenu();
        }

        if (Keys.VK_O.isClicked())
        {
            Timefall.getFileManager().getSaveMap();
            Sheep sheep = new Sheep(new Vector("world", 0, 0), 2F);

            sheep.spawn(41, 15);
            //saveState();
        }

        //Pathfinding pathfinding = new Pathfinding(new Vector(30, 9), new Vector(41, 15));
        //Pathfinding pathfinding1 = new Pathfinding(new Vector(30, 9), new Vector(41, 15));

        // Get which keys are pressed and pair them with direction
        boolean rightKey = Keys.VK_D.isPressed() || Keys.VK_RIGHT.isPressed(), leftKey = Keys.VK_A.isPressed() || Keys.VK_LEFT.isPressed(), upKey = Keys.VK_W.isPressed() || Keys.VK_UP.isPressed(), downKey = Keys.VK_S.isPressed() || Keys.VK_DOWN.isPressed();
        boolean northDirection = upKey && !downKey, southDirection = downKey && !upKey, westDirection = leftKey && !rightKey, eastDirection = rightKey && !leftKey;
        boolean moveCommand = rightKey || leftKey || upKey || downKey;

        // Get direction
        Direction direction = (northDirection && eastDirection ? Direction.NORTHEAST : (northDirection && westDirection ? Direction.NORTHWEST : (southDirection && eastDirection ? Direction.SOUTHEAST : (southDirection && westDirection ? Direction.SOUTHWEST : (northDirection ? Direction.NORTH : (southDirection ? Direction.SOUTH : (eastDirection ? Direction.EAST : Direction.WEST)))))));

        // Check all directions if it is a move command and if the player can move
        if (moveCommand)
        {
            tileManager.getEntityManager().getPlayer().move(direction);
        } else
        {
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
            Font.requestText(FontType.DEFAULT, FontSize.NORMAL, "Player yWOff: " + playerYWOFF, 0, 100);
            Font.requestText(FontType.DEFAULT, FontSize.NORMAL, "Player xOff: " + playerXOFF, 0, 120);
            Font.requestText(FontType.DEFAULT, FontSize.NORMAL, "Player yOff: " + playerYOFF, 0, 140);
            Font.requestText(FontType.DEFAULT, FontSize.NORMAL, "Player xCen: " + xCen, 0, 160);
            Font.requestText(FontType.DEFAULT, FontSize.NORMAL, "Player yCen: " + yCen, 0, 180);
            Font.requestText(FontType.DEFAULT, FontSize.NORMAL, "World: " + worldName, 0, 200);
        }
    }

    @Override
    public void saveState()
    {
        System.out.println("\nSaving game data ...");

        String worldName = Timefall.getTileManager().getCurrentWorld().getWorldName();
        int cameraXOff = Timefall.getTileManager().getCurrentWorld().getX();
        int cameraYOff = Timefall.getTileManager().getCurrentWorld().getY();
        int playerXOff = Timefall.getTileManager().getEntityManager().getPlayer().getxOff();
        int playerYOff = Timefall.getTileManager().getEntityManager().getPlayer().getyOff();
        int gender = Timefall.getTileManager().getEntityManager().getPlayer().getGender();

        Save save = new Save(worldName, cameraXOff, cameraYOff, playerXOff, playerYOff, gender);

        Timefall.getFileManager().writeSave(save);

        System.out.println("Saved game data!");
    }
}
