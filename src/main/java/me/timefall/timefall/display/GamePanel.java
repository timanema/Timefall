package me.timefall.timefall.display;

import me.timefall.timefall.Timefall;
import me.timefall.timefall.graphics.components.Screen;

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

    public GamePanel(int width, int height, Dimension dimension)
    {
        this.width = width;
        this.height = height;
        this.dimension = dimension;

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
        addMouseListener(Timefall.getMouseHandler());
        addMouseMotionListener(Timefall.getMouseHandler());
    }

    public void initBuffer()
    {
        if (screen == null)
        {
            return;
        }

        createBufferStrategy(2);

        bufferStrategy = getBufferStrategy();
        graphics = bufferStrategy.getDrawGraphics();
    }

    public void updateScreen(Screen screen)
    {
        this.screen = screen;
        this.bufferedImage = screen.bufferedImage;
        this.initBuffer();
    }

    public Screen getScreen()
    {
        return this.screen;
    }

    public void render()
    {
        if (screen == null)
        {
            return;
        }

        Timefall.getSettings().getCurrentState().render(screen);
        Timefall.getButtonHandler().renderButtons(screen);

        // Render colours on screen
        screen.render();
        screen.blendLight();
        screen.update();

        // Draw the image on the screen
        //graphics = bufferStrategy.getDrawGraphics();
        graphics.drawImage(bufferedImage, 0, 0, width, height, null);

        // Dispose of the current graphics and issue the new buffer
        //graphics.dispose();

        bufferStrategy.show();
    }
}
