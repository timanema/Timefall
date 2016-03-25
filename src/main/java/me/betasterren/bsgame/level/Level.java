package me.betasterren.bsgame.level;

import me.betasterren.bsgame.graphics.Bitmap;
import me.betasterren.bsgame.graphics.Screen;

public class Level {
    private TileManager tileManager;
    private Bitmap[][] groundTiles;

    private int screenX, screenY;
    private boolean playerMoved = false;

    public Level(TileManager tileManager, int screenX, int screenY) {
        this.tileManager = tileManager;
        this.screenX = screenX;
        this.screenY = screenY;

        this.groundTiles = new Bitmap[screenX][screenY];

        updateBitmap();
    }

    private void updateBitmap() {
        int x = 0;

        for (int[] row : tileManager.getTiles()) {
            int y  = 0;
            for (int column : row) {
                groundTiles[x][y] = tileManager.getTileByID(column).getSprite();

                y++;
            }

            x++;
        }
    }

    public void render(Screen screen) {
        updateBitmap();

        int x = 0;

        for (int[] row : tileManager.getTiles()) {
            int y  = 0;
            for (int column : row) {
                screen.render(groundTiles[x][y], x * 16, y * 16);

                y++;
            }

            x++;
        }
    }

}
