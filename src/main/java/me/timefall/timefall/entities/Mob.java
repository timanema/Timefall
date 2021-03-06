package me.timefall.timefall.entities;

import me.timefall.timefall.level.Direction;

public interface Mob extends Entity
{
    // This class will be used later for health and such
    public void move(Direction direction);

    public void move(Direction direction, float distance);

    public boolean canMove(int xMod, int yMod);

    public void moveAnimationToggle();

}
