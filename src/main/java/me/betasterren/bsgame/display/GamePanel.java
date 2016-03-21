package me.betasterren.bsgame.display;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class GamePanel extends JPanel {
    private final int width;
    private final int height;
    private Dimension dimension;

    private BufferedImage gameImage;
    private int[] pixels;

    public GamePanel(int width, int height, Dimension dimension) {
        this.width = width;
        this.height = height;
        this.dimension = dimension;

        this.gameImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        this.pixels = ((DataBufferInt) gameImage.getRaster().getDataBuffer()).getData();

        initComponents();
    }

    private void initComponents() {
        setMinimumSize(dimension);
        setMaximumSize(dimension);
        setPreferredSize(dimension);
    }
}
