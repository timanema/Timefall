package me.timefall.timefall.entities;

import me.timefall.timefall.level.Direction;

public interface Mob extends Entity {
    // This class will be used later for health and such
    public void move(Direction direction);

    public boolean canMove(Direction direction);

}
