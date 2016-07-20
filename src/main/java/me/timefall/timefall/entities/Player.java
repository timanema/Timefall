package me.timefall.timefall.entities;

import me.timefall.timefall.Timefall;
import me.timefall.timefall.graphics.Bitmap;
import me.timefall.timefall.graphics.Screen;
import me.timefall.timefall.graphics.Sprite;
import me.timefall.timefall.level.Direction;
import me.timefall.timefall.level.TileManager;
import me.timefall.timefall.level.Vector;
import me.timefall.timefall.level.tiles.base.Block;
import me.timefall.timefall.level.tiles.base.MapObject;
import me.timefall.timefall.level.world.World;

public class Player implements Mob
{
    private final int xAxis = Timefall.X_RES / 2;
    private final int yAxis = Timefall.Y_RES / 2;

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

    public Player(String name, Vector location, Direction direction, int worldX, int worldY, int gender)
    {
        this.playerName = name;
        this.playerLocation = location;
        this.playerDirection = direction;
        this.gender = gender;

        this.currentlyMoving = false;

        // Calculate max and current values and use these for center calculations
        int xMax = 16 * worldX - (xAxis % 16);
        int yMax = 16 * worldY - (yAxis % 16);
        int xPlayer = (int) Vector.playerxPos;
        int yPlayer = (int) Vector.playeryPos;

        this.checkCentred(xMax, yMax, xPlayer, yPlayer);

        Vector.setPlayerVariables(xOff * .0625F, yOff * .0625F);

        System.out.println("   Player created!");
    }

    @Override
    public Vector getLocation()
    {
        return playerLocation;
    }

    @Override
    public String getName()
    {
        return playerName;
    }

    @Override
    public Direction getDirection()
    {
        return playerDirection;
    }

    @Override
    public Bitmap getCurrentBitmap()
    {
        int gender = Math.abs(this.gender - 1);
        // TODO: Add weapons, colouring and more
        // Returns appropriate bitmap
        switch (getDirection())
        {
            case NORTH:
                return (animationStatus == 0 ? Sprite.characters[gender][3] : (animationStatus == 1 ? Sprite.characters[gender][4] : Sprite.characters[gender][5]));
            case NORTHEAST:
                return (animationStatus == 0 ? Sprite.characters[gender][3] : (animationStatus == 1 ? Sprite.characters[gender][4] : Sprite.characters[gender][5]));
            case NORTHWEST:
                return (animationStatus == 0 ? Sprite.characters[gender][3] : (animationStatus == 1 ? Sprite.characters[gender][4] : Sprite.characters[gender][5]));
            case EAST:
                return (animationStatus == 0 ? Sprite.characters[gender][6].flipVert() : (animationStatus == 1 ? Sprite.characters[gender][7].flipVert() : Sprite.characters[gender][8].flipVert()));
            case SOUTH:
                return (animationStatus == 0 ? Sprite.characters[gender][0] : (animationStatus == 1 ? Sprite.characters[gender][1] : Sprite.characters[gender][2]));
            case SOUTHEAST:
                return (animationStatus == 0 ? Sprite.characters[gender][0] : (animationStatus == 1 ? Sprite.characters[gender][1] : Sprite.characters[gender][2]));
            case SOUTHWEST:
                return (animationStatus == 0 ? Sprite.characters[gender][0] : (animationStatus == 1 ? Sprite.characters[gender][1] : Sprite.characters[gender][2]));
            case WEST:
                return (animationStatus == 0 ? Sprite.characters[gender][6] : (animationStatus == 1 ? Sprite.characters[gender][7] : Sprite.characters[gender][8]));
            default:
                return Sprite.terrain[0][0];
        }
    }

    @Override
    public boolean isMoving()
    {
        return currentlyMoving;
    }

    private void checkCentred(int xMax, int yMax, int xPlayer, int yPlayer)
    {
        // Check if the player is in one of the four corners
        if (xPlayer < xAxis && yPlayer < yAxis)
        {
            // Player is in upper left corner
            xOff = xPlayer;
            yOff = yPlayer;

            xCen = false;
            yCen = false;
        } else if (xPlayer > xMax - xAxis && yPlayer > yMax - yAxis)
        {
            // Player is in lower right corner
            xOff = xAxis + (xPlayer - (xMax - xAxis));
            yOff = yAxis + (yPlayer - (yMax - yAxis));

            xCen = false;
            yCen = false;
        } else if (xPlayer < xAxis && yPlayer > yMax - yAxis)
        {
            // Player is in lower left corner
            xOff = xPlayer;
            yOff = yAxis + (yPlayer - (yMax - yAxis));

            xCen = false;
            yCen = false;
        } else if (xPlayer > xMax - xAxis && yPlayer < yAxis)
        {
            // Player is in upper right corner
            xOff = xAxis + (xPlayer - (xMax - xAxis));
            yOff = yPlayer;

            xCen = false;
            yCen = false;
        } else
        {
            if (yPlayer < yAxis || yPlayer > yMax - yAxis)
            {
                // Player is X centered, not Y centered
                xOff = xAxis;
                yOff = (yPlayer < yAxis ? yPlayer : yAxis + (yPlayer - (yMax - yAxis)));

                xCen = true;
                yCen = false;

            } else if (xPlayer < xAxis || xPlayer > xMax - xAxis)
            {
                // Player is Y centered, not X centered
                xOff = (xPlayer < xAxis ? xPlayer : xAxis + (xPlayer - (xMax - xAxis)));
                yOff = yAxis;

                xCen = false;
                yCen = true;
            } else
            {
                // Both X and Y are centered
                xOff = xAxis;
                yOff = yAxis;

                xCen = true;
                yCen = true;
            }
        }
    }

    public boolean isXCentred()
    {
        return xCen;
    }

    public boolean isYCentred()
    {
        return yCen;
    }

    @Override
    public void tick()
    {
        // Calculate max and current values and use these for center calculations
        int xMax = 16 * Timefall.getTileManager().worldX - (xAxis % 16);
        int yMax = 16 * Timefall.getTileManager().worldY - (yAxis % 16);
        int xPlayer = getxOff();
        int yPlayer = getyOff();

        this.checkCentred(xMax, yMax, xPlayer, yPlayer);

        // Update the current animationCount to set the correct animationStatus
        if (isMoving())
        {
            animationCount++;

            if (animationCount == 3)
            {
                animationCount = 0;
                animationStatus++;

                if (animationStatus == 3)
                    animationStatus = 0;
            }
        } else
        {
            animationCount = 0;
            animationStatus = 0;
        }
    }

    @Override
    public void render(Screen screen)
    {
        screen.draw(getCurrentBitmap(), xOff, yOff);
    }

    @Override
    public void move(Direction direction)
    {
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
        for (Block block : tileManager.getLevel().getSurroundingTiles(getLocation(), 2))
        {
            if (block == null)
                continue;

            if (block.getBlockID()[0] == 1)
            {
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
    public boolean canMove(Direction direction)
    {
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
    public void spawn(int x, int y)
    {
    }

    @Override
    public void despawn()
    {
    }

    @Override
    public boolean isAlive()
    {
        return true;
    }

    @Override
    public void teleport(World world, float x, float y)
    {
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
        boolean xCen = xOff >= xAxis && xOff <= xMax - xAxis;
        boolean yCen = yOff >= yAxis && yOff <= yMax - yAxis;

        if (xCen && yCen)
        {
            // Both X and Y are centered
            xOffWorld = xOff - xAxis;
            yOffWorld = yOff - yAxis;
        } else if (xCen && !yCen)
        {
            // Only X is centered
            xOffWorld = xOff - xAxis;
            yOffWorld = (yOff < yAxis ? 0 : yMax - yAxis * 2);
        } else if (!xCen && yCen)
        {
            // Only Y is centered
            xOffWorld = (xOff < xAxis ? 0 : xMax - xAxis * 2);
            yOffWorld = yOff - yAxis;
        } else
        {
            // Both X and Y are not centered
            xOffWorld = (xOff < xAxis ? 0 : xMax - xAxis * 2);
            yOffWorld = (yOff < yAxis ? 0 : yMax - yAxis * 2);
        }

        // Update world
        Timefall.getTileManager().changeWorld(world, xOffWorld, yOffWorld);
    }

    public int getxOff()
    {
        return getxOff(getLocation());
    }

    public int getyOff()
    {
        return getyOff(getLocation());
    }

    private int getxOff(Vector vector)
    {
        int remainderX = (int) Math.round((vector.getX() * Math.pow(10, 3)) % (Math.pow(10, 3)));
        int xPos = (int) vector.getX();

        return xPos * 16 + (int) (remainderX / 62.5);
    }

    private int getyOff(Vector vector)
    {
        int remainderY = (int) Math.round((vector.getY() * Math.pow(10, 3)) % (Math.pow(10, 3)));
        int yPos = (int) vector.getY();

        return yPos * 16 + (int) (remainderY / 62.5);
    }

    public int getGender()
    {
        return gender;
    }
}
