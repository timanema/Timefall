package me.timefall.timefall.game.menu;

import me.timefall.timefall.GameState;
import me.timefall.timefall.Settings;
import me.timefall.timefall.graphics.components.Bitmap;
import me.timefall.timefall.graphics.components.Colour;
import me.timefall.timefall.graphics.components.Screen;

public class TextOverlay extends GameState {
    public TextOverlay(Settings settings, Screen screen) {
        super(settings, screen);
    }

    @Override
    public void tick(double deltaTime) {

    }

    @Override
    public void render(Screen screen) {
        int width = screen.bufferedImage.getWidth();
        int height = screen.bufferedImage.getHeight();

        Bitmap bitmap = new Bitmap(width, height);

        for (int x = 0; x < width; x++)
        {
            for (int y = 0; y < height; y++)
            {
                bitmap.draw(Colour.TRANSPARANT, x, y);
            }
        }

        screen.draw(bitmap, 0, 0);
    }

    @Override
    public void saveState() {

    }

    
}
