package me.betasterren.bsgame.entities;

import me.betasterren.bsgame.BSGame;
import me.betasterren.bsgame.graphics.Bitmap;
import me.betasterren.bsgame.graphics.Screen;
import me.betasterren.bsgame.graphics.Sprite;
import me.betasterren.bsgame.level.Direction;
import me.betasterren.bsgame.level.Vector;

public class Player implements Entity {
    public int xOff = 0;
    public int yOff = 0;

    private int worldX;
    private int worldY;

    public boolean xCen;
    public boolean yCen;

    private String playerName;
    private Vector playerLocation;
    private Direction playerDirection;

    private boolean currentlyMoving;

    public Player(String name, Vector location, Direction direction, int worldX, int worldY) {
        this.playerName = name;
        this.playerLocation = location;
        this.playerDirection = direction;

        this.currentlyMoving = false;

        int xMax = 16 * worldX;
        int yMax = 16 * worldY - 8;
        int xPlayer = (int) Vector.playerxPos;
        int yPlayer = (int) Vector.playeryPos;

        this.checkCentred(xMax, yMax, xPlayer, yPlayer);

        Vector.setPlayerVariables(xOff * .0625F, yOff * .0625F);

        System.out.println("   Player created!");
    }

    @Override
    public Vector getLocation() {
        return playerLocation;
    }

    @Override
    public String getName() {
        return playerName;
    }

    @Override
    public Direction getDirection() {
        return playerDirection;
    }

    @Override
    public Bitmap getCurrentBitmap() {
        return Sprite.sprites[9][1];
    }

    @Override
    public boolean isMoving() {
        return currentlyMoving;
    }

    private void checkCentred(int xMax, int yMax, int xPlayer, int yPlayer) {
        if ((xPlayer < 320 && yPlayer < 180) ||
                (xPlayer > xMax - 320 && yPlayer < 180) ||
                (xPlayer > xMax - 320 && yPlayer > yMax - 180) ||
                (xPlayer < 320 && yPlayer > yMax - 180)) {
            xOff = xPlayer;
            yOff = yPlayer;

            xCen = false;
            yCen = false;
        } else {
            if (yPlayer < 180 || yPlayer > yMax - 180) {
                // Player is X centered, not Y centered
                xOff = 320;
                yOff = yPlayer;

                xCen = true;
                yCen = false;
            } else if (xPlayer < 320 || xPlayer > xMax - 320) {
                // Player is Y centered, not X centered
                xOff = xPlayer;
                yOff = 180;

                xCen = false;
                yCen = true;
            } else {
                // Both X and Y are centered
                xOff = 320;
                yOff = 180;

                xCen = true;
                yCen = true;
            }
        }
    }

    public boolean isXCentred() {
        return xCen;
    }

    public boolean isYCentred() {
        return yCen;
    }

    @Override
    public void tick() {
        int xMax = 16 * BSGame.getTileManager().worldX;
        int yMax = 16 * BSGame.getTileManager().worldY - 8;
        int xPlayer = getxOff();
        int yPlayer = getyOff();

        this.checkCentred(xMax, yMax, xPlayer, yPlayer);
    }

    @Override
    public void render(Screen screen) {
        screen.render(getCurrentBitmap(), xOff, yOff);
    }

    @Override
    public void move(Direction direction) {
        this.playerDirection = direction;
        // TODO: Fix this later
        this.currentlyMoving = false;

        float xDif = direction.getxChange() * .125F;
        float yDif = direction.getyChange() * .125F;

        BSGame.getTileManager().getEntityManager().getPlayer().getLocation().add(xDif, yDif);
    }

    public int getxOff() {
        int remainderX = (int) Math.round((playerLocation.getX() * Math.pow(10, 3)) % (Math.pow(10, 3)));
        int xPos = (int) playerLocation.getX();

        return xPos * 16 + (int) (remainderX / 62.5);
    }

    public int getyOff() {
        int remainderY = (int) Math.round((playerLocation.getY() * Math.pow(10, 3)) % (Math.pow(10, 3)));
        int yPos = (int) playerLocation.getY();

        return yPos * 16 + (int) (remainderY / 62.5);
    }
}
