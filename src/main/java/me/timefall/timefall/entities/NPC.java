package me.timefall.timefall.entities;

import me.timefall.timefall.Timefall;
import me.timefall.timefall.entities.behaviors.Routine;
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
    private float speedModifier = 1;
    private Routine routine;

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
        this.routine = new Routine();
    }

    public NPC(int characterIndex,
               Vector location,
               float speedModifier)
    {
        this.characterIndex = characterIndex;
        this.location = location;
        this.xOff = Timefall.getTileManager().getxOff(location);
        this.yOff = Timefall.getTileManager().getxOff(location);
        this.speedModifier = speedModifier;
        this.routine = new Routine();
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
        this.routine = new Routine();
    }

    public void moveTo(Vector location)
    {

    }

    public void setRoutine(Routine routine)
    {
        this.routine = routine;
    }

    public Routine getRoutine()
    {
        return routine;
    }

    @Override
    public void move(Direction direction)
    {
        //todo fix collision bug

        System.out.println("direction: " + direction);

        this.direction = direction;
        this.isMoving = true;

        boolean negativeXMovement = direction.getxChange() < 0;
        boolean negativeYMovement = direction.getyChange() < 0;
        int xMod = (negativeXMovement ? -1 : 1);
        int yMod = (negativeYMovement ? -1 : 1);
        int xMoved = 0;
        int yMoved = 0;

        System.out.println("xchange: " + direction.getxChange());
        System.out.println("calc:" + (direction.getxChange() * this.speedModifier));

        int xAdjusted = Math.round(direction.getxChange() * this.speedModifier);

        int yAdjusted = Math.round(direction.getyChange() * this.speedModifier);

        System.out.println("xadjusted: " + xAdjusted);

        //if (xAdjusted == 0)
        //{
        //    xAdjusted = xMod;
       // }

        //if (yAdjusted == 0)
        //{
         //   yAdjusted = yMod;
        //}

        System.out.println("xadjusted: " + xAdjusted);

        while ((negativeXMovement ? xMoved > xAdjusted : xMoved < xAdjusted) ||
                (negativeYMovement ? yMoved > yAdjusted : yMoved < yAdjusted))
        {
            // Move on x-axis
            if (negativeXMovement ? xMoved > xAdjusted : xMoved < xAdjusted)
            {
                // Check if NPC can move
                if (canMove(xMod, 0))
                {
                    // Check if NPC moves inside borders
                    if (xOff + xMod >= 0 &&
                            xOff + xMod <= Timefall.getTileManager().getCurrentWorld().getWidth() * 16 - getCurrentBitmap().width)
                    {
                        xOff += xMod;
                        this.getLocation().add(xMod * .0625F, 0);
                    }
                }

                xMoved += xMod;
            }

            // Move on y-axis
            if (negativeYMovement ? yMoved > yAdjusted : yMoved < yAdjusted)
            {
                // Check if NPC can move
                if (canMove(0, yMod))
                {
                    // Check if NPC moves inside borders
                    if (yOff + yMod >= 0 &&
                            yOff + yMod <= Timefall.getTileManager().getCurrentWorld().getHeight() * 16 - getCurrentBitmap().height)
                    {
                        yOff += yMod;
                        this.getLocation().add(0, yMod * .0625F);
                    }
                }

                yMoved += yMod;

            }
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
        routine.getBehavior(this).getNextAction().execute(this);
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
