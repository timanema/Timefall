package me.timefall.timefall.display;

import me.timefall.timefall.Timefall;
import me.timefall.timefall.graphics.Screen;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class GamePanel extends Canvas
{
    private BufferStrategy bufferStrategy;
    private Graphics graphics;
    private BufferedImage bufferedImage;

    private final int width;
    private final int height;
    private Dimension dimension;
    private Screen screen;

    public GamePanel(int width, int height, Dimension dimension, Screen screen, BufferedImage bufferedImage)
    {
        this.width = width;
        this.height = height;
        this.dimension = dimension;
        this.screen = screen;
        this.bufferedImage = bufferedImage;

        initComponents();
    }

    private void initComponents()
    {
        // Initialize components and set some settings
        setMinimumSize(dimension);
        setMaximumSize(dimension);
        setPreferredSize(dimension);

        addKeyListener(Timefall.getKeyHandler());
        addFocusListener(Timefall.getKeyHandler());
    }

    public void initBuffer()
    {
        createBufferStrategy(2);

        bufferStrategy = getBufferStrategy();
        graphics = bufferStrategy.getDrawGraphics();
    }

    public void render()
    {
        Timefall.getSettings().getCurrentState().render(screen);

        // Draw the image on the screen
        //graphics = bufferStrategy.getDrawGraphics();
        graphics.drawImage(bufferedImage, 0, 0, width, height, null);

        // Dispose of the current graphics and issue the new buffer
        //graphics.dispose();
        bufferStrategy.show();
    }
}
