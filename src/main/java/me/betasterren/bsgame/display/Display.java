package me.betasterren.bsgame.display;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Display {
    private String gameName;

    private int width;
    private int height;
    private Dimension dimension;

    private JFrame jFrame;
    private JPanel gamePanel;

    public Display(String gameName, int width, int height) {
        this.width = width;
        this.height = height;
        this.gameName = gameName;

        dimension = new Dimension(width, height);

        initDisplay();
    }

    /**
     * Initializes all components of main display and combines them
     */
    private void initDisplay() {
        // Init frame
        jFrame = new JFrame();

        // TODO: Add layouts

        // Set dimensions
        jFrame.setSize(width, height);
        jFrame.setResizable(false);
        jFrame.setLocationRelativeTo(null);
        jFrame.setTitle(gameName);


        // Set close operation
        jFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        jFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // This way we can save all the data before actually exiting
                onClose();
            }
        });


        // TODO: Add components
        // Create components
        gamePanel = new GamePanel(dimension);

        // Add components
        jFrame.add(gamePanel);

        // Show frame
        jFrame.pack();
        jFrame.setVisible(true);
    }

    /**
     * Gets called when the main display is being closed
     * This way everything can be saved before exiting
     */
    private void onClose() {
        // TODO: Save stuff

        // Exit the program
        System.exit(0);
    }
}
