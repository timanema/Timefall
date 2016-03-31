package me.betasterren.bsgame.entities;

import me.betasterren.bsgame.graphics.Bitmap;
import me.betasterren.bsgame.graphics.Screen;
import me.betasterren.bsgame.level.Direction;
import me.betasterren.bsgame.level.Vector;

public interface Entity {
    public Vector getLocation();

    public String getName();

    public Direction getDirection();

    public Bitmap getCurrentBitmap();

    public boolean isMoving();

    public void tick();

    public void render(Screen screen);

    public void move(Direction direction);
}
