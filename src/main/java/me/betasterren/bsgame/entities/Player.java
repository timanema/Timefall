package me.betasterren.bsgame.entities;

import me.betasterren.bsgame.Timefall;
import me.betasterren.bsgame.graphics.Bitmap;
import me.betasterren.bsgame.graphics.Screen;
import me.betasterren.bsgame.graphics.Sprite;
import me.betasterren.bsgame.level.Direction;
import me.betasterren.bsgame.level.TileManager;
import me.betasterren.bsgame.level.Vector;
import me.betasterren.bsgame.level.tiles.base.Block;
import me.betasterren.bsgame.level.tiles.base.MapObject;
import me.betasterren.bsgame.level.world.World;

public class Player implements Mob {
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
    private int gender;

    public Player(String name, Vector location, Direction direction, int worldX, int worldY, int gender) {
        this.playerName = name;
        this.playerLocation = location;
        this.playerDirection = direction;
        this.gender = gender;

        this.currentlyMoving = false;

        // Calculate max and current values and use these for center calculations
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
        // Returns appropriate bitmap
        switch (getDirection()) {
            case NORTH:
                return (animationStatus == 0 ? Sprite.characters[1][gender] : (animationStatus == 1 ? Sprite.characters[4][gender] : Sprite.characters[8][gender]));
            case NORTHEAST:
                return (animationStatus == 0 ? Sprite.characters[1][gender] : (animationStatus == 1 ? Sprite.characters[4][gender] : Sprite.characters[8][gender]));
            case NORTHWEST:
                return (animationStatus == 0 ? Sprite.characters[1][gender] : (animationStatus == 1 ? Sprite.characters[4][gender] : Sprite.characters[8][gender]));
            case EAST:
                return (animationStatus == 0 ? Sprite.characters[2][gender].flip() : (animationStatus == 1 ? Sprite.characters[5][gender].flip() : Sprite.characters[6][gender].flip()));
            case SOUTH:
                return (animationStatus == 0 ? Sprite.characters[0][gender] : (animationStatus == 1 ? Sprite.characters[3][gender] : Sprite.characters[7][gender]));
            case SOUTHEAST:
                return (animationStatus == 0 ? Sprite.characters[0][gender] : (animationStatus == 1 ? Sprite.characters[3][gender] : Sprite.characters[7][gender]));
            case SOUTHWEST:
                return (animationStatus == 0 ? Sprite.characters[0][gender] : (animationStatus == 1 ? Sprite.characters[3][gender] : Sprite.characters[7][gender]));
            case WEST:
                return (animationStatus == 0 ? Sprite.characters[2][gender] : (animationStatus == 1 ? Sprite.characters[5][gender] : Sprite.characters[6][gender]));
            default:
                return Sprite.terrain[0][0];
        }
    }

    @Override
    public boolean isMoving() {
        return currentlyMoving;
    }

    private void checkCentred(int xMax, int yMax, int xPlayer, int yPlayer) {
        // Check if the player is in one of the four corners
        if (xPlayer < 320 && yPlayer < 180) {
            // Player is in upper left corner
            xOff = xPlayer;
            yOff = yPlayer;

            xCen = false;
            yCen = false;
        } else if (xPlayer > xMax - 320 && yPlayer > yMax - 180) {
            // Player is in lower right corner
            xOff = 320 + (xPlayer - (xMax - 320));
            yOff = 180 + (yPlayer - (yMax - 180));

            xCen = false;
            yCen = false;
        } else if (xPlayer < 320 && yPlayer > yMax - 180) {
            // Player is in lower left corner
            xOff = xPlayer;
            yOff = 180 + (yPlayer - (yMax - 180));

            xCen = false;
            yCen = false;
        } else if (xPlayer > xMax - 320 && yPlayer < 180) {
            // Player is in upper right corner
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
        // Calculate max and current values and use these for center calculations
        int xMax = 16 * Timefall.getTileManager().worldX;
        int yMax = 16 * Timefall.getTileManager().worldY - 8;
        int xPlayer = getxOff();
        int yPlayer = getyOff();

        this.checkCentred(xMax, yMax, xPlayer, yPlayer);

        // Update the current animationCount to set the correct animationStatus
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
    }

    @Override
    public void render(Screen screen) {
        screen.render(getCurrentBitmap(), xOff, yOff);
    }

    @Override
    public void move(Direction direction) {
        TileManager tileManager = Timefall.getTileManager();

        // Move the player in a direction
        this.playerDirection = direction;
        this.currentlyMoving = true;

        // Calculating needed change in X-axis and Y-axis to move correctly
        float xDif = direction.getxChange() * .125F;
        float yDif = direction.getyChange() * .125F;

        getLocation().add((getxOff() + xDif < 0 || getxOff() + xDif > (tileManager.getCurrentWorld().getWidth() - 1) * 16 ? 0 : xDif),
                (getyOff() + yDif < 0 || getyOff() + yDif > (tileManager.getCurrentWorld().getHeight() - 1) * 16 - 12 ? 0 : yDif));

        //TODO: Remove this debug code
        for (Block block : tileManager.getLevel().getSurroundingTiles(getLocation(), 2)) {
            if (block == null)
                continue;

            if (block.getBlockID()[0] == 1) {
                // TP player
                teleport((tileManager.getWorld(getLocation().getWorldName().equals("world") ? "test" : "world")), 30, 18);

                // Change gender
                Timefall.getSettings().setGender(this.gender == 0 ? 1 : 0);
                this.gender = this.gender == 0 ? 1 : 0;
                break;
            }
        }
    }

    @Override
    public boolean canMove(Direction direction) {
        TileManager tileManager = Timefall.getTileManager();

        // Simulating move
        float xDif = direction.getxChange() * .125F;
        float yDif = direction.getyChange() * .125F;
        Vector locationCopy = getLocation().clone();

        locationCopy.add((getxOff() + xDif < 0 || getxOff() + xDif > (tileManager.getCurrentWorld().getWidth() - 1) * 16 ? 0 : xDif),
                (getyOff() + yDif < 0 || getyOff() + yDif > (tileManager.getCurrentWorld().getHeight() - 1) * 16 - 12 ? 0 : yDif));

        int xOff = getxOff(locationCopy);
        int yOff = getyOff(locationCopy);


        for (int i = 0; i < 2; i++)
            for (MapObject mapObject : tileManager.getMapObjectsByLoc(xOff / 16 + (direction.name().contains("EAST") ? 1 : 0) + i, yOff / 16 + (direction.name().contains("NORTH") ? 0 : 1) + i))
                if (mapObject != null)
                    if (mapObject.isSolid())
                        return false;

        return true;
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
        // Get current location
        float currentX = playerLocation.getX();
        float currentY = playerLocation.getY();

        // Make sure x and y are multiplications of .125
        x = x - (((int) Math.round((x * Math.pow(10, 3)) % (Math.pow(10, 3)))) % 125 / 1000);
        y = y - (((int) Math.round((y * Math.pow(10, 3)) % (Math.pow(10, 3)))) % 125 / 1000);

        int xOff = getxOff(new Vector(x, 0));
        int yOff = getyOff(new Vector(0, y));

        // Update player location
        this.xOff = xOff;
        this.yOff = yOff;

        getLocation().setLocation(world.getWorldName(), x, y);

        // Get current offsets
        int xOffWorld;
        int yOffWorld;

        // Calculate max offsets
        int xMax = 16 * world.getWidth();
        int yMax = 16 * world.getHeight() - 8;

        // Calculate what the next offsets should be
        boolean xCen = xOff >= 320 && xOff <= xMax - 320;
        boolean yCen = yOff >= 180 && yOff <= yMax - 180;

        if (xCen && yCen) {
            // Both X and Y are centered
            xOffWorld = xOff - 320;
            yOffWorld = yOff - 180;
        } else if (xCen && !yCen) {
            // Only X is centered
            xOffWorld = xOff - 320;
            yOffWorld = (yOff < 180 ? 0 : yMax - 360);
        } else if (!xCen && yCen) {
            // Only Y is centered
            xOffWorld = (xOff < 320 ? 0 : xMax - 640);
            yOffWorld = yOff - 180;
        } else {
            // Both X and Y are not centered
            xOffWorld = (xOff < 320 ? 0 : xMax - 640);
            yOffWorld = (yOff < 180 ? 0 : yMax - 360);
        }

        // Update world
        Timefall.getTileManager().changeWorld(world, xOffWorld, yOffWorld);
    }

    public int getxOff() {
        return getxOff(getLocation());
    }

    public int getyOff() {
        return getyOff(getLocation());
    }

    private int getxOff(Vector vector) {
        int remainderX = (int) Math.round((vector.getX() * Math.pow(10, 3)) % (Math.pow(10, 3)));
        int xPos = (int) vector.getX();

        return xPos * 16 + (int) (remainderX / 62.5);
    }

    private int getyOff(Vector vector) {
        int remainderY = (int) Math.round((vector.getY() * Math.pow(10, 3)) % (Math.pow(10, 3)));
        int yPos = (int) vector.getY();

        return yPos * 16 + (int) (remainderY / 62.5);
    }

    public int getGender() {
        return gender;
    }
}
