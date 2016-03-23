package me.betasterren.bsgame.display;

import me.betasterren.bsgame.graphics.Screen;
import me.betasterren.bsgame.graphics.Sprite;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class GamePanel extends Canvas {
    private final int width;
    private final int height;
    private Dimension dimension;

    private Screen screen;

    public GamePanel(int width, int height, Dimension dimension, Screen screen) {
        this.width = width;
        this.height = height;
        this.dimension = dimension;
        this.screen = screen;

        initComponents();
    }

    private void initComponents() {
        setMinimumSize(dimension);
        setMaximumSize(dimension);
        setPreferredSize(dimension);
    }

    public void render() {
        BufferStrategy bufferStrategy = getBufferStrategy();

        if (bufferStrategy == null) {
            createBufferStrategy(2);
            return;
        }

        Graphics graphics = bufferStrategy.getDrawGraphics();

        // Render sprites
        //TODO: Remove debug sprites
        for (int i = 0; i < 26; i++) {
            for (int x = 0; x < 3; x++)
                for (int y = 0; y < 3; y++)
                    screen.render(Sprite.sprites[x][y], i * 48 + (x * 16), y * 16);
        }

        for (int x = 6; x < 8; x++)
            for (int y = 0; y < 3; y++)
                screen.render(Sprite.sprites[x][y], x * 16, y * 16 + 48);
        //TODO: Remove debug sprites

        graphics.drawImage(screen.bufferedImage, 0, 0, width, height, null);

        graphics.dispose();
        bufferStrategy.show();
    }
}
