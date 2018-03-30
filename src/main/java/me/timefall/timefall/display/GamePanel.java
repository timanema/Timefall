package me.timefall.timefall.display;

import me.timefall.timefall.Timefall;
import me.timefall.timefall.graphics.components.Screen;
import me.timefall.timefall.time.Time;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class GamePanel extends JComponent {
    private BufferStrategy bufferStrategy;
    private Graphics graphics;
    private BufferedImage bufferedImage;

    private final int width;
    private final int height;
    private Dimension dimension;
    private Screen screen;

    private final boolean menuPanel;

    public GamePanel(int width,
                     int height,
                     Dimension dimension,
                     boolean menuPanel) {
        this.width = width;
        this.height = height;
        this.dimension = dimension;
        this.menuPanel = menuPanel;

        initComponents();
    }

    private void initComponents() {
        // Initialize components and set some settings
        setMinimumSize(dimension);
        setMaximumSize(dimension);
        setPreferredSize(dimension);

        addKeyListener(Timefall.getKeyHandler());
        addFocusListener(Timefall.getKeyHandler());
        addMouseListener(Timefall.getMouseHandler());
        addMouseMotionListener(Timefall.getMouseHandler());
    }

    @Deprecated
    public void initBuffer() {
        if (screen == null) {
            return;
        }

        //createBufferStrategy(2);

        //bufferStrategy = getBufferStrategy();
        graphics = bufferStrategy.getDrawGraphics();
    }

    public void updateScreen(Screen screen) {
        this.screen = screen;
        this.bufferedImage = screen.bufferedImage;

        //this.initBuffer();
    }

    public Screen getScreen() {
        return this.screen;
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        if (screen == null) {
            return;
        }

        if (!menuPanel) {
            Timefall.getSettings().getCurrentState().render(screen);
            Timefall.getButtonHandler().renderButtons(screen);
        } else {
            Timefall.textOverlay.render(screen);
        }

        // Render colours on screen
        screen.render();
        screen.blendLight();
        screen.update();

        // Draw the image on the screen
        //graphics = bufferStrategy.getDrawGraphics();
        graphics.drawImage(bufferedImage, 0, 0, width, height, null);
    }
}