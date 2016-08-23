package me.timefall.timefall.display;

import me.timefall.timefall.Timefall;
import me.timefall.timefall.game.game.MoveListener;
import me.timefall.timefall.graphics.Screen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.net.URL;

public class Display
{
    private String gameName;

    private int width;
    private int height;
    private Dimension dimension;

    public JFrame jFrame;
    private GamePanel gameCanvas;
    private Screen screen;

    public Display(String gameName, int width, int height)
    {
        this.width = width;
        this.height = height;
        this.gameName = gameName;

        dimension = new Dimension(width, height);

        initDisplay();
    }

    /**
     * Initializes all components of main display and combines them
     */
    private void initDisplay()
    {
        // Init frame
        jFrame = new JFrame();

        // TODO: Add layouts
        jFrame.setLayout(new BorderLayout());

        // Set dimensions
        jFrame.setSize(width, height);
        jFrame.setResizable(false);
        jFrame.setLocationRelativeTo(null);
        jFrame.setTitle(gameName);

        try
        {
            URL url = getClass().getResource("/icon.png");
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            Image image = toolkit.createImage(url);

            jFrame.setIconImage(image);
        } catch (NullPointerException npe)
        {
            // Image wasn't found
            System.out.println("Couldn't load icon, using default one");
        }

        // Set close operation
        jFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        jFrame.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                // This way we can save all the data before actually exiting
                onClose();
            }
        });

        // Create components
        screen = new Screen(Timefall.X_RES, Timefall.Y_RES);
        gameCanvas = new GamePanel(width, height, dimension, screen, screen.bufferedImage);

        // Add components
        jFrame.add(gameCanvas);

        // Hide cursor
        jFrame.setCursor(jFrame.getToolkit().createCustomCursor(new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB), new Point(0, 0), "null"));

        // Show frame
        jFrame.pack();
        jFrame.setVisible(true);

        gameCanvas.initBuffer();

        // Add listeners
        jFrame.addKeyListener(Timefall.getKeyHandler());
        jFrame.addFocusListener(Timefall.getKeyHandler());
        jFrame.setFocusable(true);
        Timefall.getKeyHandler().addListener(new MoveListener());
    }

    /**
     * Gets called when the main display is being closed
     * This way everything can be saved before exiting
     */
    private void onClose()
    {
        System.out.println("\nSaving game data ...");

        // Save all necessary data
        Timefall.getFileManager().changeSetting("settings", "xOff", String.valueOf(Timefall.getTileManager().getCurrentWorld().getX()));
        Timefall.getFileManager().changeSetting("settings", "yOff", String.valueOf(Timefall.getTileManager().getCurrentWorld().getY()));
        Timefall.getFileManager().changeSetting("settings", "gender", String.valueOf(Timefall.getTileManager().getEntityManager().getPlayer().getGender()));
        Timefall.getFileManager().changeSetting("lvl", "world", Timefall.getTileManager().getCurrentWorld().getWorldName());
        Timefall.getFileManager().changeSetting("lvl", "xOff", String.valueOf(Timefall.getTileManager().getEntityManager().getPlayer().getxOff()));
        Timefall.getFileManager().changeSetting("lvl", "yOff", String.valueOf(Timefall.getTileManager().getEntityManager().getPlayer().getyOff()));

        System.out.println("Saved game data!");

        // Exit the program
        System.exit(0);
    }

    public GamePanel getGameCanvas()
    {
        return gameCanvas;
    }

    public Screen getScreen()
    {
        return screen;
    }
}
