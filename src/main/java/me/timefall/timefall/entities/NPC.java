package me.timefall.timefall.entities;

import me.timefall.timefall.Timefall;
import me.timefall.timefall.graphics.components.Bitmap;
import me.timefall.timefall.graphics.components.Colour;
import me.timefall.timefall.graphics.components.Screen;
import me.timefall.timefall.graphics.components.Sprite;
import me.timefall.timefall.level.Direction;
import me.timefall.timefall.level.TileManager;
import me.timefall.timefall.level.Vector;
import me.timefall.timefall.level.world.World;

import java.awt.*;

public class NPC implements Mob {
    private int characterIndex;
    private Vector location;
    private String name;

    private Direction direction;
    private boolean isMoving;
    private int xOff;
    private int yOff;

    public NPC(int characterIndex,
               Vector location)
    {
        this.characterIndex = characterIndex;
        this.location = location;
        this.xOff = Timefall.getTileManager().getxOff(location);
        this.yOff = Timefall.getTileManager().getxOff(location);
    }

    public NPC(int characterIndex,
               Vector location,
               String name)
    {
        this.characterIndex = characterIndex;
        this.location = location;
        this.xOff = Timefall.getTileManager().getxOff(location);
        this.yOff = Timefall.getTileManager().getxOff(location);
        this.name = name;
    }

    public void moveTo(Vector location)
    {

    }

    @Override
    public void move(Direction direction)
    {
        this.direction = direction;
        this.isMoving = true;

        boolean negativeXMovement = direction.getxChange() < 0;
        boolean negativeYMovement = direction.getyChange() < 0;
        int xMod = (negativeXMovement ? -1 : 1);
        int yMod = (negativeYMovement ? -1 : 1);

        for (int x = 0; (negativeXMovement ? x > direction.getxChange() : x < direction.getxChange()); x += xMod)
        {
            // Check if NPC can move
            if (!canMove(xMod, 0))
            {
                break;
            }

            // Check if NPC will go out of bounds
            if (xOff + xMod >= 0 && xOff + xMod <= Timefall.GAME_X_RES - getCurrentBitmap().width)
            {
                xOff += xMod;
            }


            this.getLocation().add(xMod * .0625F, 0);
        }

        for (int y = 0; (negativeYMovement ? y > direction.getyChange() : y < direction.getyChange()); y += yMod)
        {
            // Check if NPC can move
            if (!canMove(0, yMod))
            {
                break;
            }

            // Check if NPC will go out of bounds
            if (yOff + yMod >= 0 && yOff + yMod <= Timefall.GAME_Y_RES - getCurrentBitmap().height)
            {
                yOff += yMod;
            }

            this.getLocation().add(0,yMod * .0625F);
        }
    }

    @Override
    public boolean canMove(int xMod,
                           int yMod)
    {
        // Getting current offsets
        int xOff = Timefall.getTileManager().getxOff(this.location);
        int yOff = Timefall.getTileManager().getyOff(this.location);

        // Simulating move
        int simX = xOff + xMod;
        int simY = yOff + yMod;

        Rectangle entityRectangle = new Rectangle(simX,
                simY,
                getCurrentBitmap().width,
                getCurrentBitmap().height);

        Bitmap t = new Bitmap(getCurrentBitmap().width, getCurrentBitmap().height);

        for (int x = 0; x < t.width; x++)
        {
            for (int y = 0; y < t.height; y++)
            {
                t.draw(Colour.BLUE, x, y);
            }
        }

        Timefall.getMainDisplay().getGameCanvas().getScreen().draw(t, xOff, yOff);

        return !Timefall.getTileManager().intersection(entityRectangle);
    }

    @Override
    public void moveAnimationToggle()
    {

    }

    @Override
    public Vector getLocation()
    {
        return this.location;
    }

    @Override
    public String getName()
    {
        return this.name;
    }

    @Override
    public Direction getDirection()
    {
        return this.direction;
    }

    @Override
    public Bitmap getCurrentBitmap()
    {
        return Sprite.npcs[this.characterIndex][0];
    }

    @Override
    public boolean isMoving()
    {
        return this.isMoving;
    }

    @Override
    public void tick()
    {

    }

    @Override
    public void render(Screen screen)
    {
        TileManager tileManager = Timefall.getTileManager();
        int xOffWorld = tileManager.getCurrentWorld().getX();
        int yOffWorld = tileManager.getCurrentWorld().getY();

        screen.draw(getCurrentBitmap(),
                this.xOff - xOffWorld,
                this.yOff - yOffWorld);
    }

    public void spawn(int x,
                      int y)
    {
        this.xOff = x * 16;
        this.yOff = y * 16;

        Timefall.getTileManager().getEntityManager().addNPC(this);
    }

    public void despawn()
    {
        Timefall.getTileManager().getEntityManager().removeNPC(this);
    }

    @Override
    public boolean isAlive()
    {
        return false;
    }

    @Override
    public void teleport(World world,
                         float x,
                         float y)
    {

    }
}
