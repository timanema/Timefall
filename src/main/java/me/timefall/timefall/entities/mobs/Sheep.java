package me.timefall.timefall.entities.mobs;

import me.timefall.timefall.entities.Mob;
import me.timefall.timefall.graphics.components.Bitmap;
import me.timefall.timefall.graphics.components.Screen;
import me.timefall.timefall.level.Direction;
import me.timefall.timefall.level.Vector;
import me.timefall.timefall.level.world.World;

public class Sheep implements Mob {
    @Override
    public void move(Direction direction) {

    }

    @Override
    public boolean canMove(int xMod, int yMod) {
        return false;
    }

    @Override
    public void moveAnimationToggle() {

    }

    @Override
    public Vector getLocation() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public Direction getDirection() {
        return null;
    }

    @Override
    public Bitmap getCurrentBitmap() {
        return null;
    }

    @Override
    public boolean isMoving() {
        return false;
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Screen screen) {

    }

    @Override
    public void spawn(int x, int y) {

    }

    @Override
    public void despawn() {

    }

    @Override
    public boolean isAlive() {
        return false;
    }

    @Override
    public void teleport(World world, float x, float y) {

    }
}
