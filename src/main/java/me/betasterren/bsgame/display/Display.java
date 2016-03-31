package me.betasterren.bsgame.display;

import me.betasterren.bsgame.BSGame;
import me.betasterren.bsgame.game.game.MoveListener;
import me.betasterren.bsgame.graphics.Screen;

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
    private GamePanel gameCanvas;

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
        jFrame.setLayout(new BorderLayout());

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


        // Create components
        gameCanvas = new GamePanel(width, height, dimension, new Screen(640, 360));

        // Add components
        jFrame.add(gameCanvas);

        // Show frame
        jFrame.pack();
        jFrame.setVisible(true);

        // Add listeners
        jFrame.addKeyListener(BSGame.getKeyHandler());
        jFrame.addFocusListener(BSGame.getKeyHandler());
        jFrame.setFocusable(true);
        BSGame.getKeyHandler().addListener(new MoveListener());
    }

    /**
     * Gets called when the main display is being closed
     * This way everything can be saved before exiting
     */
    private void onClose() {
        System.out.println("\nSaving game data ...");

        BSGame.getFileManager().changeSetting("settings", "xOff", BSGame.getTileManager().getLevel().xOff);
        BSGame.getFileManager().changeSetting("settings", "yOff", BSGame.getTileManager().getLevel().yOff);
        BSGame.getFileManager().changeSetting("lvl", "xOff", BSGame.getTileManager().getEntityManager().getPlayer().getxOff());
        BSGame.getFileManager().changeSetting("lvl", "yOff", BSGame.getTileManager().getEntityManager().getPlayer().getyOff());

        System.out.println("Saved game data!");

        // Exit the program
        System.exit(0);
    }

    public GamePanel getGameCanvas() {
        return gameCanvas;
    }
}
