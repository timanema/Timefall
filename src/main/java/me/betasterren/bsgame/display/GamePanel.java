package me.betasterren.bsgame.display;

import me.betasterren.bsgame.BSGame;
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
        BSGame.getSettings().getCurrentState().render(screen);

        graphics.drawImage(screen.bufferedImage, 0, 0, width, height, null);

        graphics.dispose();
        bufferStrategy.show();
    }
}
