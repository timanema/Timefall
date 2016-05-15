package me.timefall.timefall.level.tiles.tiles;

import me.timefall.timefall.graphics.Bitmap;
import me.timefall.timefall.graphics.Sprite;
import me.timefall.timefall.level.tiles.base.Block;

public class WaterTile implements Block {
    @Override
    public Bitmap getSprite(int ID) {
        switch (ID) {
            case 1:
                return Sprite.terrain[1][1];
            case 2:
                return Sprite.terrain[1][0];
            case 3:
                return Sprite.terrain[2][0];
            case 4:
                return Sprite.terrain[2][1];
            case 5:
                return Sprite.terrain[2][2];
            case 6:
                return Sprite.terrain[1][2];
            case 7:
                return Sprite.terrain[0][2];
            case 8:
                return Sprite.terrain[0][1];
            case 9:
                return Sprite.terrain[0][0];
            default:
                return null;
        }
    }

    @Override
    public int[] getBlockID() {
        return new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
    }

    @Override
    public int getHeight() {
        return 1;
    }

    @Override
    public int getWidth() {
        return 1;
    }

    @Override
    public String getName() {
        return "Water";
    }

    @Override
    public int[] getHex() {
        return new int[]{0x29ABE3, 0x29ABE4, 0x29ABE5, 0x29ABE6, 0x29ABE7, 0x29ABE8, 0x29ABE9, 0x29ABE2, 0x29ABE1};
    }

    @Override
    public Bitmap[] getAnimations() {
        return null;
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public boolean isAnimated() {
        return false;
    }

    @Override
    public void tick() {
        // TODO: Make this block animated
    }
}
