package me.betasterren.bsgame.display;

import me.betasterren.bsgame.Timefall;
import me.betasterren.bsgame.graphics.Screen;

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
        // Initialize components and set some settings
        setMinimumSize(dimension);
        setMaximumSize(dimension);
        setPreferredSize(dimension);

        addKeyListener(Timefall.getKeyHandler());
        addFocusListener(Timefall.getKeyHandler());
    }

    public void render() {
        // Change BufferStrategy so we use double buffering
        BufferStrategy bufferStrategy = getBufferStrategy();

        if (bufferStrategy == null) {
            createBufferStrategy(2);
            return;
        }

        Graphics graphics = bufferStrategy.getDrawGraphics();

        // Render sprites
        Timefall.getSettings().getCurrentState().render(screen);

        // Draw the image on the screen
        graphics.drawImage(screen.bufferedImage, 0, 0, width, height, null);

        // Dispose of the current graphics and issue the new buffer
        graphics.dispose();
        bufferStrategy.show();
    }
}
