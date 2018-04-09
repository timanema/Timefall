package me.timefall.timefall.entities.mobs;

import me.timefall.timefall.entities.EntityManager;
import me.timefall.timefall.entities.NPC;
import me.timefall.timefall.graphics.components.Bitmap;
import me.timefall.timefall.graphics.components.Screen;
import me.timefall.timefall.level.Direction;
import me.timefall.timefall.level.Vector;
import me.timefall.timefall.level.world.World;

public class Sheep extends NPC {
    int t = 0;
    public Sheep(Vector location)
    {
        super(EntityManager.CHARACTER_SHEEP, location);
    }

    public Sheep(Vector location,
                 String name)
    {
        super(EntityManager.CHARACTER_SHEEP, location, name);
    }

    public Sheep(Vector location,
                 float speedModifier)
    {
        super(EntityManager.CHARACTER_SHEEP, location, speedModifier);
    }

    @Override
    public void move(Direction direction)
    {
        super.move(direction);
    }

    @Override
    public boolean canMove(int xMod, int yMod)
    {
        return super.canMove(xMod, yMod);
    }

    @Override
    public void moveAnimationToggle()
    {
        super.moveAnimationToggle();
    }

    @Override
    public Vector getLocation()
    {
        return super.getLocation();
    }

    @Override
    public String getName()
    {
        return super.getName();
    }

    @Override
    public Direction getDirection()
    {
        return super.getDirection();
    }

    @Override
    public Bitmap getCurrentBitmap()
    {
        return super.getCurrentBitmap();
    }

    @Override
    public boolean isMoving()
    {
        return super.isMoving();
    }

    @Override
    public void tick()
    {
        move(Direction.SOUTHEAST);
    }

    @Override
    public void render(Screen screen)
    {
        super.render(screen);
    }

    @Override
    public void spawn(int x, int y)
    {
        super.spawn(x, y);
    }

    @Override
    public void despawn()
    {
        super.despawn();
    }

    @Override
    public boolean isAlive()
    {
        return super.isAlive();
    }

    @Override
    public void teleport(World world,
                         float x,
                         float y)
    {
        super.teleport(world, x, y);
    }
}
