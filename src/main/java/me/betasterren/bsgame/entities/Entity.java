package me.betasterren.bsgame.entities;

import me.betasterren.bsgame.graphics.Bitmap;
import me.betasterren.bsgame.graphics.Screen;
import me.betasterren.bsgame.level.Direction;
import me.betasterren.bsgame.level.Vector;
import me.betasterren.bsgame.level.world.World;

public interface Entity {
    public Vector getLocation();

    public String getName();

    public Direction getDirection();

    public Bitmap getCurrentBitmap();

    public boolean isMoving();

    public void tick();

    public void render(Screen screen);

    public void spawn(int x, int y);

    public void despawn();

    public boolean isAlive();

    public void teleport(World world, float x, float y);
}
