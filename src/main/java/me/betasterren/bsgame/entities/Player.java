package me.betasterren.bsgame.entities;

import me.betasterren.bsgame.BSGame;
import me.betasterren.bsgame.graphics.Bitmap;
import me.betasterren.bsgame.graphics.Screen;
import me.betasterren.bsgame.graphics.Sprite;
import me.betasterren.bsgame.level.Direction;
import me.betasterren.bsgame.level.Vector;
import me.betasterren.bsgame.level.world.World;

public class Player implements Entity {
    public int xOff = 0;
    public int yOff = 0;

    public boolean xCen;
    public boolean yCen;

    private String playerName;
    private Vector playerLocation;
    private Direction playerDirection;

    public boolean currentlyMoving;
    private int animationCount = 0;
    private int animationStatus = 0;

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
        switch (getDirection()) {
            case NORTH:
                return (animationStatus == 0 ? Sprite.playerBud[1][0] : (animationStatus == 1 ? Sprite.playerBud[4][0] : Sprite.playerBud[8][0]));
            case NORTHEAST:
                return (animationStatus == 0 ? Sprite.playerBud[1][0] : (animationStatus == 1 ? Sprite.playerBud[4][0] : Sprite.playerBud[8][0]));
            case NORTHWEST:
                return (animationStatus == 0 ? Sprite.playerBud[1][0] : (animationStatus == 1 ? Sprite.playerBud[4][0] : Sprite.playerBud[8][0]));
            case EAST:
                return (animationStatus == 0 ? Sprite.playerBud[2][0].flip() : (animationStatus == 1 ? Sprite.playerBud[5][0].flip() : Sprite.playerBud[6][0].flip()));
            case SOUTH:
                return (animationStatus == 0 ? Sprite.playerBud[0][0] : (animationStatus == 1 ? Sprite.playerBud[3][0] : Sprite.playerBud[7][0]));
            case SOUTHEAST:
                return (animationStatus == 0 ? Sprite.playerBud[0][0] : (animationStatus == 1 ? Sprite.playerBud[3][0] : Sprite.playerBud[7][0]));
            case SOUTHWEST:
                return (animationStatus == 0 ? Sprite.playerBud[0][0] : (animationStatus == 1 ? Sprite.playerBud[3][0] : Sprite.playerBud[7][0]));
            case WEST:
                return (animationStatus == 0 ? Sprite.playerBud[2][0] : (animationStatus == 1 ? Sprite.playerBud[5][0] : Sprite.playerBud[6][0]));
            default:
                return Sprite.sprites[9][1];
        }
    }

    @Override
    public boolean isMoving() {
        return currentlyMoving;
    }

    private void checkCentred(int xMax, int yMax, int xPlayer, int yPlayer) {
        if (xPlayer < 320 && yPlayer < 180) {
            xOff = xPlayer;
            yOff = yPlayer;

            xCen = false;
            yCen = false;
        } else if (xPlayer > xMax - 320 && yPlayer > yMax - 180) {
            xOff = 320 + (xPlayer - (xMax - 320));
            yOff = 180 + (yPlayer - (yMax - 180));

            xCen = false;
            yCen = false;
        } else if (xPlayer < 320 && yPlayer > yMax - 180) {
            xOff = xPlayer;
            yOff = 180 + (yPlayer - (yMax - 180));

            xCen = false;
            yCen = false;
        } else if (xPlayer > xMax - 320 && yPlayer < 180) {
            xOff = 320 + (xPlayer - (xMax - 320));
            yOff = yPlayer;

            xCen = false;
            yCen = false;
        } else {
            if (yPlayer < 180 || yPlayer > yMax - 180) {
                // Player is X centered, not Y centered
                xOff = 320;
                yOff = (yPlayer < 180 ? yPlayer : 180 + (yPlayer - (yMax - 180)));

                xCen = true;
                yCen = false;
            } else if (xPlayer < 320 || xPlayer > xMax - 320) {
                // Player is Y centered, not X centered
                xOff = (xPlayer < 320 ? xPlayer : 320 + (xPlayer - (xMax - 320)));
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

        if (isMoving()) {
            animationCount++;

            if (animationCount == 3) {
                animationCount = 0;
                animationStatus++;

                if (animationStatus == 3)
                    animationStatus = 0;
            }
        } else {
            animationCount = 0;
            animationStatus = 0;
        }

        //if (animationCount == 2) System.out.println("Anim status: " + animationStatus);
    }

    @Override
    public void render(Screen screen) {
        screen.render(getCurrentBitmap(), xOff, yOff);
    }

    @Override
    public void move(Direction direction) {
        this.playerDirection = direction;
        this.currentlyMoving = true;

        float xDif = direction.getxChange() * .125F;
        float yDif = direction.getyChange() * .125F;

        getLocation().add(xDif, yDif);
    }

    @Override
    public void spawn(int x, int y) {
    }

    @Override
    public void despawn() {
    }

    @Override
    public boolean isAlive() {
        return true;
    }

    @Override
    public void teleport(World world, float x, float y) {
        // TODO: Change world in worldmanager on teleport
        // World gaan we later iets mee doen
        float currentX = playerLocation.getX();
        float currentY = playerLocation.getY();

        // Make sure x and y are multiplications of .125
        x = x - (((int) Math.round((x * Math.pow(10, 3)) % (Math.pow(10, 3)))) % 125 / 1000);
        y = y - (((int) Math.round((y * Math.pow(10, 3)) % (Math.pow(10, 3)))) % 125 / 1000);

        float xDif = -currentX + x;
        float yDif = -currentY + y;

        // Update player location
        getLocation().add(xDif, yDif);

        int playerxOff = getxOff();
        int playeryOff = getyOff();
        int xOffWorld = 0;
        int yOffWorld = 0;

        int xMax = 16 * BSGame.getTileManager().worldX;
        int yMax = 16 * BSGame.getTileManager().worldY - 8;

        // Update checkCentered();
        tick();

        if (xCen && yCen) {
            // Both X and Y are centered
            xOffWorld = playerxOff - 320;
            yOffWorld = playeryOff - 180;
        } else if (xCen && !yCen) {
            // Only X is centered
            xOffWorld = playerxOff - 320;
            yOffWorld = (playeryOff < 180 ? 0 : yMax - 360);
        } else if (!xCen && yCen) {
            // Only Y is centered
            xOffWorld = (playerxOff < 320 ? 0 : xMax - 640);
            yOffWorld = playeryOff - 180;
        } else {
            // Both X and Y are not centered
            xOffWorld = (playerxOff < 320 ? 0 : xMax - 640);
            yOffWorld = (playeryOff < 180 ? 0 : yMax - 360);
        }

        // Update world offsets
        world.setOffset(xOffWorld, yOffWorld);
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
