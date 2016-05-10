package me.betasterren.bsgame.entities;

import me.betasterren.bsgame.level.Direction;

public interface Mob extends Entity {
    // This class will be used later for health and such
    public void move(Direction direction);

    public boolean canMove(Direction direction);

}
