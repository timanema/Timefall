package me.betasterren.bsgame.display;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private Dimension dimension;

    public GamePanel(Dimension dimension) {
        this.dimension = dimension;

        initComponents();
    }

    private void initComponents() {
        setMinimumSize(dimension);
        setMaximumSize(dimension);
        setPreferredSize(dimension);
    }
}
