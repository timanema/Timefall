package me.timefall.timefall.display;

import me.timefall.timefall.Timefall;
import me.timefall.timefall.game.game.KeyListener;
import me.timefall.timefall.graphics.components.Screen;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class Display
{
    private String gameName;

    private int width;
    private int height;
    private Dimension dimension;

    public JFrame jFrame;
    private GamePanel gameCanvas;
    private GamePanel menuCanvas;

    public Display(String gameName, int width, int height, Object lockObject)
    {
        this.width = width;
        this.height = height;
        this.gameName = gameName;

        dimension = new Dimension(width, height);

        initDisplay(lockObject);
    }

    /**
     * Initializes all components of main display and combines them
     */
    private void initDisplay(Object lockObject)
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
        JLayeredPane jLayeredPane = new JLayeredPane();
        gameCanvas = new GamePanel(width, height, dimension, false);
        menuCanvas = new GamePanel(width, height, dimension, true);

        // Add components
        jLayeredPane.setPreferredSize(new Dimension(width, height));
        gameCanvas.setBounds(0 ,0, width, height);
        menuCanvas.setBounds(0, 0, width, height);
        jLayeredPane.add(gameCanvas, JLayeredPane.DEFAULT_LAYER);
        jLayeredPane.add(menuCanvas, JLayeredPane.PALETTE_LAYER);

        jFrame.getContentPane().add(jLayeredPane);

        // Set custom cursor
        BufferedImage mouseImage = null;
        try
        {
            mouseImage = ImageIO.read(Timefall.class.getResourceAsStream("/spritesheets/ImprovedCursorV2.png"));
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        //TODO: Fatsoenlijke muis maken
        //jFrame.setCursor(jFrame.getToolkit().createCustomCursor(mouseImage, new Point(0, 0), "customMouse"));

        // Show frame
        jFrame.pack();
        jFrame.setVisible(true);

         // Add listeners
        jFrame.addKeyListener(Timefall.getKeyHandler());
        jFrame.addFocusListener(Timefall.getKeyHandler());
        jFrame.addMouseListener(Timefall.getMouseHandler());
        jFrame.addMouseMotionListener(Timefall.getMouseHandler());
        jFrame.setFocusable(true);
        Timefall.getKeyHandler().addListener(new KeyListener());

        this.unlock(lockObject);
    }

    private void unlock(Object lockObject)
    {
        synchronized (lockObject)
        {
            lockObject.notify();
        }
    }

    /**
     * Gets called when the main display is being closed
     * This way everything can be saved before exiting
     */
    public void onClose()
    {
        Timefall.getSettings().getCurrentState().saveState();

        // Exit the program
        System.exit(0);
    }

    public GamePanel getGameCanvas()
    {
        return this.gameCanvas;
    }

    public GamePanel getMenuCanvas() {
        return this.menuCanvas;
    }

    public Screen getScreen()
    {
        return this.gameCanvas.getScreen();
    }
}
