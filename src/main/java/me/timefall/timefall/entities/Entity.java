package me.timefall.timefall.entities;

import me.timefall.timefall.graphics.components.Bitmap;
import me.timefall.timefall.graphics.components.Screen;
import me.timefall.timefall.level.Direction;
import me.timefall.timefall.level.Vector;
import me.timefall.timefall.level.world.World;

public interface Entity
{
    public Vector getLocation();

    public String getName();

    public Direction getDirection();

    public Bitmap getCurrentBitmap();

    public boolean isMoving();

    public void tick();

    public void render(Screen screen);

    public boolean isAlive();

    public void teleport(World world, float x, float y);
}
