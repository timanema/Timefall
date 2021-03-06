package me.timefall.timefall.entities;

import me.timefall.timefall.Timefall;
import me.timefall.timefall.graphics.components.Bitmap;
import me.timefall.timefall.graphics.utils.PixelUtils;
import me.timefall.timefall.graphics.components.Screen;
import me.timefall.timefall.graphics.components.Sprite;
import me.timefall.timefall.level.Direction;
import me.timefall.timefall.level.TileManager;
import me.timefall.timefall.level.Vector;
import me.timefall.timefall.level.tiles.base.Block;
import me.timefall.timefall.level.world.World;

import java.awt.*;

public class Player implements Mob
{
    private final int xAxis = Timefall.GAME_X_RES / 2;
    private final int yAxis = Timefall.GAME_Y_RES / 2;

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

        this.updateBitmap();
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

    public void updateBitmap()
    {
        Sprite.completeCharacters = new Bitmap[Sprite.characters.length][Sprite.characters[0].length];
        int x = 0;
        int gender = 0;

        for (Bitmap[] genderBitmaps : Sprite.characters)
        {
            int y = 0;

            for (Bitmap bitmap : genderBitmaps)
            {
                // TODO: Add weapons, colouring and more
                // DEFAULTS:
                //  Male: AC8105 (main hair), E2C13A (light hair), 4F4F15 (edge hair), 004562 (eye), 70591D (eyebrow), F9AD81 (skin-mid), F9BA98 (skin-light), D89671 (skin-dark)
                //  Female: 007975 (eye), F9AD81 (skin-mid), 111111 (eyebrow), 16161A (main hair), 4A4A5F (light hair), 000000 (edge hair),
                for (int i = 0; i < bitmap.colours.length; i++)
                {
                    int colour = PixelUtils.getColour(bitmap.colours[i]);

                    // Main hair
                    if (colour == 0xffff0000)
                    {
                        bitmap.colours[i] = PixelUtils.getColour(gender == 0 ? 0xffac8105 : 0xff16161a);
                    }

                    // Light hair
                    if (colour == 0xffff0c00)
                    {
                        bitmap.colours[i] = PixelUtils.getColour(gender == 0 ? 0xffe2c13a : 0xff4a4a5f);
                    }

                    // Edge hair
                    if (colour == 0xffff0c0c)
                    {
                        bitmap.colours[i] = PixelUtils.getColour(gender == 0 ? 0xff4f4f15 : 0xff000000);
                    }

                    // Eyes
                    if (colour == 0xff00ff00)
                    {
                        bitmap.colours[i] = PixelUtils.getColour(gender == 0 ? 0xff004562 : 0xff007975);
                    }

                    // Eyebrow
                    if (colour == 0xffff000c)
                    {
                        bitmap.colours[i] = PixelUtils.getColour(gender == 0 ? 0xff70591d : 0xff111111);
                    }

                    // Skin medium
                    if (colour == 0xffffff00)
                    {
                        bitmap.colours[i] = PixelUtils.getColour(0xfff9ad81);
                    }

                    // Skin light
                    if (colour == 0xffffff01)
                    {
                        bitmap.colours[i] = PixelUtils.getColour(gender == 0 ? 0xfff9ba98 : 0);
                    }

                    // Skin dark
                    if (colour == 0xffffff02)
                    {
                        bitmap.colours[i] = PixelUtils.getColour(gender == 0 ? 0xffd89671 : 0);
                    }
                }

                Sprite.completeCharacters[x][y] = bitmap.clone();
                y++;
            }
            gender++;
            x++;
        }
    }

    @Override
    public Bitmap getCurrentBitmap()
    {
        if (Sprite.completeCharacters == null)
            return null;

        // Returns appropriate bitmap
        switch (getDirection())
        {
            case NORTH:
                return (animationStatus == 0 ? Sprite.completeCharacters[gender][3] :
                        (animationStatus == 1 ? Sprite.completeCharacters[gender][4] :
                                Sprite.completeCharacters[gender][5]));
            case NORTHEAST:
                return (animationStatus == 0 ? Sprite.completeCharacters[gender][3] :
                        (animationStatus == 1 ? Sprite.completeCharacters[gender][4] :
                                Sprite.completeCharacters[gender][5]));
            case NORTHWEST:
                return (animationStatus == 0 ? Sprite.completeCharacters[gender][3] :
                        (animationStatus == 1 ? Sprite.completeCharacters[gender][4] :
                                Sprite.completeCharacters[gender][5]));
            case EAST:
                return (animationStatus == 0 ? Sprite.completeCharacters[gender][6].flipVert() :
                        (animationStatus == 1 ? Sprite.completeCharacters[gender][7].flipVert() :
                                Sprite.completeCharacters[gender][8].flipVert()));
            case SOUTH:
                return (animationStatus == 0 ? Sprite.completeCharacters[gender][0] :
                        (animationStatus == 1 ? Sprite.completeCharacters[gender][1] :
                                Sprite.completeCharacters[gender][2]));
            case SOUTHEAST:
                return (animationStatus == 0 ? Sprite.completeCharacters[gender][0] :
                        (animationStatus == 1 ? Sprite.completeCharacters[gender][1] :
                                Sprite.completeCharacters[gender][2]));
            case SOUTHWEST:
                return (animationStatus == 0 ? Sprite.completeCharacters[gender][0] :
                        (animationStatus == 1 ? Sprite.completeCharacters[gender][1] :
                                Sprite.completeCharacters[gender][2]));
            case WEST:
                return (animationStatus == 0 ? Sprite.completeCharacters[gender][6] :
                        (animationStatus == 1 ? Sprite.completeCharacters[gender][7] :
                                Sprite.completeCharacters[gender][8]));
            default:
                // Shouldn't happen
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
        Bitmap currentBitmap = getCurrentBitmap();
        int width = currentBitmap == null ? 0 : currentBitmap.width / 2;
        int height = currentBitmap == null ? 0 : currentBitmap.width / 2;

        // Check if the player is in one of the four corners
        if (xPlayer + width < xAxis && yPlayer + height < yAxis)
        {
            // Player is in upper left corner
            xOff = xPlayer;
            yOff = yPlayer;

            xCen = false;
            yCen = false;
        } else if (xPlayer + width > xMax - xAxis && yPlayer + height > yMax - yAxis)
        {
            // Player is in lower right corner
            xOff = xAxis + (xPlayer - (xMax - xAxis));
            yOff = yAxis + (yPlayer - (yMax - yAxis));

            xCen = false;
            yCen = false;
        } else if (xPlayer + width < xAxis && yPlayer + height > yMax - yAxis)
        {
            // Player is in lower left corner
            xOff = xPlayer;
            yOff = yAxis + (yPlayer - (yMax - yAxis));

            xCen = false;
            yCen = false;
        } else if (xPlayer + width> xMax - xAxis && yPlayer + height < yAxis)
        {
            // Player is in upper right corner
            xOff = xAxis + (xPlayer - (xMax - xAxis));
            yOff = yPlayer;

            xCen = false;
            yCen = false;
        } else
        {
            if (yPlayer + height < yAxis || yPlayer + height > yMax - yAxis)
            {
                // Player is X centered, not Y centered
                xOff = xAxis - width;

                if (yPlayer < yAxis)
                {
                    yOff = yPlayer;
                } else
                {
                    yOff = yAxis + (yPlayer - (yMax - yAxis));
                }

                xCen = true;
                yCen = false;

            } else if (xPlayer + width < xAxis || xPlayer + width > xMax - xAxis)
            {
                // Player is Y centered, not X centered
                xOff = (xPlayer < xAxis ? xPlayer : xAxis + (xPlayer - (xMax - xAxis)));
                yOff = yAxis - height;

                xCen = false;
                yCen = true;
            } else
            {
                // Both X and Y are centered
                xOff = xAxis - width;
                yOff = yAxis - height;

                xCen = true;
                yCen = true;
            }
        }
    }

    @Override
    public void tick()
    {
        TileManager tileManager = Timefall.getTileManager();

        // Just in case the player somehow gets an offset outside the borders
        if (xOff < 0) xOff = 0;
        if (yOff < 0) yOff = 0;
        if (xOff > tileManager.getCurrentWorld().getWidth() * 16)
            xOff = tileManager.getCurrentWorld().getWidth() * 16 - getCurrentBitmap().width;
        if (yOff > tileManager.getCurrentWorld().getHeight() * 16)
            yOff = tileManager.getCurrentWorld().getHeight() * 16 - getCurrentBitmap().height;

        // Update the current animationCount to set the correct animationStatus
        if (isMoving())
        {
            if (animationCount == -1)
            {
                animationStatus = 1;
                animationCount += 1;
            }

            animationCount += 1;

            if (animationCount == 3)
            {
                animationCount = 0;
                animationStatus += 1;

                if (animationStatus == 3)
                    animationStatus = 0;
            }
        } else
        {
            animationCount = -1;
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

        boolean negativeXMovement = direction.getxChange() < 0;
        boolean negativeYMovement = direction.getyChange() < 0;
        int xMod = (negativeXMovement ? -1 : 1);
        int yMod = (negativeYMovement ? -1 : 1);
        int xMoved = 0;
        int yMoved = 0;

        while ((negativeXMovement ? xMoved > direction.getxChange() : xMoved < direction.getxChange()) ||
                (negativeYMovement ? yMoved > direction.getyChange() : yMoved < direction.getyChange()))
        {
            // Calculate max and current values and use these for center calculations
            int xMax = 16 * tileManager.worldX;// - (xAxis % 16);
            int yMax = 16 * tileManager.worldY;// - (yAxis % 16);
            int xPlayer = getxOff();
            int yPlayer = getyOff();

            this.checkCentred(xMax, yMax, xPlayer, yPlayer);

            // Move on x-axis
            if (negativeXMovement ? xMoved > direction.getxChange() : xMoved < direction.getxChange())
            {
                if (canMove(xMod, 0))
                {
                    int currentOff = tileManager.getCurrentWorld().getX();

                    if (currentOff + xMod >= 0 &&
                            currentOff + xMod + Timefall.GAME_X_RES <= tileManager.getCurrentWorld().getWidth() * 16
                            && xCen)
                    {
                        // Screen can move
                        tileManager.getCurrentWorld().setOffset(tileManager.getCurrentWorld().getX() + xMod, tileManager.getCurrentWorld().getY());

                        this.getLocation().add(xMod * .0625F, 0);
                    } else
                    {
                        // Screen cannot move
                        if (xOff + xMod >= 0 && xOff + xMod <= Timefall.GAME_X_RES - getCurrentBitmap().width)
                        {
                            xCen = false;
                            xOff += xMod;

                            this.getLocation().add(xMod * .0625F, 0);
                        }
                    }

                }

                xMoved += xMod;
            }

            // Move on y-axis
            if (negativeYMovement ? yMoved > direction.getyChange() : yMoved < direction.getyChange())
            {
                if (canMove(0, yMod))
                {
                    int currentOff = tileManager.getCurrentWorld().getY();

                    if (currentOff + yMod >= 0 &&
                            currentOff + yMod + Timefall.GAME_Y_RES <= tileManager.getCurrentWorld().getHeight() * 16
                            && yCen)
                    {
                        // Screen can move
                        tileManager.getCurrentWorld().setOffset(tileManager.getCurrentWorld().getX(), tileManager.getCurrentWorld().getY() + yMod);
                        this.getLocation().add(0, yMod * .0625F);
                    } else
                    {
                        // Screen cannot move
                        if (yOff + yMod >= 0 && yOff + yMod <= Timefall.GAME_Y_RES - getCurrentBitmap().height)
                        {
                            yCen = false;
                            yOff += yMod;
                            this.getLocation().add(0, yMod * .0625F);
                        }
                    }
                }

                yMoved += yMod;
            }
        }

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
    public boolean canMove(int xMod, int yMod)
    {
        // Simulating move
        int simX = getxOff() + xMod;
        int simY = getyOff() + yMod;

        Rectangle playerRectangle = new Rectangle(simX,
                simY + 12,
                16,
                24 - 12);

        return !Timefall.getTileManager().intersection(playerRectangle);
    }

    @Override
    public void moveAnimationToggle()
    {
        this.currentlyMoving = !this.currentlyMoving;
    }

    @Override
    public boolean isAlive()
    {
        return true;
    }

    @Override
    public void teleport(World world, float x, float y)
    {
        // Make sure x and y are multiplications of .125
        x = x - (((int) Math.round((x * Math.pow(10, 3)) % (Math.pow(10, 3)))) % 125 / 1000);
        y = y - (((int) Math.round((y * Math.pow(10, 3)) % (Math.pow(10, 3)))) % 125 / 1000);

        int xOff = Timefall.getTileManager().getxOff(new Vector(x, 0));
        int yOff = Timefall.getTileManager().getyOff(new Vector(0, y));

        getLocation().setLocation(world.getWorldName(), x, y);

        // Get current offsets
        int xOffWorld;
        int yOffWorld;

        // Calculate max offsets
        int xMax = 16 * world.getWidth() - (xAxis % 16);
        int yMax = 16 * world.getHeight() - (yAxis % 16);

        // Calculate what the next offsets should be
        boolean xCen = xOff >= xAxis && xOff <= xMax - xAxis;
        boolean yCen = yOff >= yAxis && yOff <= yMax - yAxis;
        Bitmap currentBitmap = getCurrentBitmap();
        int width = currentBitmap == null ? 0 : currentBitmap.width / 2;
        int height = currentBitmap == null ? 0 : currentBitmap.width / 2;

        this.checkCentred(xMax, yMax, getxOff(), getyOff());

        if (xCen && yCen)
        {
            // Both X and Y are centered
            xOffWorld = xOff - xAxis + width;
            yOffWorld = yOff - yAxis + height;
        } else if (xCen)
        {
            // Only X is centered
            xOffWorld = xOff - xAxis + width;
            yOffWorld = (yOff < yAxis ? 0 : yMax - yAxis * 2);
        } else if (yCen)
        {
            // Only Y is centered
            xOffWorld = (xOff < xAxis ? 0 : xMax - xAxis * 2);
            yOffWorld = yOff - yAxis + height;
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
        return Timefall.getTileManager().getxOff(getLocation());
    }

    public int getyOff()
    {
        return Timefall.getTileManager().getyOff(getLocation());
    }

    public int getGender()
    {
        return gender;
    }
}
